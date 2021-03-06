package com.qiaotouxi.am.business.customer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.CustomerDao;
import com.qiaotouxi.am.business.dao.CustomerDaoDao;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.utils.FileUtils;
import com.qiaotouxi.am.framework.view.PhotoPop;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/1 0001.
 * 添加客户页面
 */
public class AddCustomerActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_tx)
    ImageView imgTx;
    @BindView(R.id.tv_khzl)
    TextView tv_khzl;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_cardID)
    EditText etCardID;
    @BindView(R.id.et_location)
    EditText etLocation;
    @BindView(R.id.et_bzxx)
    EditText etBzxx;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_buy_list)
    LinearLayout llBuyList;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    /**
     * 拍照返回图片路径
     */
    private String imgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view 点击事件等
     */
    private void initView() {
        tv_khzl.requestFocus();
        imgTx.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_tx:
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.btn_save:
                saveData();
                break;
        }
    }

    /**
     * 保存客户数据
     */
    private void saveData() {
        if (b == null) {
            AmUtlis.showToast("请上传照片");
            return;
        }
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写姓名");
            return;
        }
        String phone = etPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            AmUtlis.showToast("请填写电话");
            return;
        }
        String cardId = etCardID.getText().toString().trim();
//        if(TextUtils.isEmpty(cardId)){
//            AmUtlis.showToast("请填写身份证号码");
//            return;
//        }
        String location = etLocation.getText().toString().trim();
        String bzxx = etBzxx.getText().toString().trim();
        int sex = -1;//1=男， 0=女
        boolean checkedFemale = rbFemale.isChecked();
        boolean checkedMale = rbMale.isChecked();
        if (!checkedFemale && !checkedMale) {
            AmUtlis.showToast("请选择性别");
            return;
        } else {
            if (checkedFemale) {
                sex = 0;
            } else {
                sex = 1;
            }
        }

        imgPath = BitmapUtils.saveImg(b, name + phone, BitmapUtils.IMG_TYPE_KHTX);
        CustomerDao dao = new CustomerDao();
        dao.setName(name);
        dao.setPhone(phone);
        dao.setCardId(cardId);
        dao.setLocation(location);
        dao.setEngine_id_list("");
        dao.setPhoto_path(imgPath);
        dao.setDirPath(name + phone);
        dao.setRemark(bzxx);
        dao.setSex(sex);
        dao.setBuy(false);
        dao.setPinyin(AmUtlis.getPinYin(name));
        dao.setDate(new Date());
        CustomerDaoDao customerDaoDao = App.getDaoSession(this).getCustomerDaoDao();
        CustomerDao unique = customerDaoDao.queryRawCreate("where PHONE=? order by PHONE", phone).unique();
        if (unique != null && unique.getCardId().equals(phone)) {
            //保存前根据填写的电话号码，查询数据库， 如果存在此会员，提示电话重复
            AmUtlis.showToast("该电话已被注册，请修改");
            return;
        }
        try {
            customerDaoDao.insert(dao);
            AmUtlis.showToast("添加成功");
            //写入资料到txt文件
            String text = "姓名：" + name + "\r\n性别：" + (sex == 1 ? "男" : "女") + "\r\n电话：" + phone + "\r\n身份证：" + cardId + "\r\n联系地址：" + location + "\r\n备注信息：" + bzxx;
            FileUtils.writeTxt(BitmapUtils.getFilePath() + name + phone + "/" + "客户资料.txt", text);
            AmUtlis.refreshCustomerManageData();
            finish();
        } catch (Exception e) {
            AmUtlis.showToast("添加失败");
        }
    }

    private Bitmap b;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CAPTURE && resultCode == RESULT_OK) {
            //拍照返回
            b = BitmapUtils.getDiskBitmap(AmUtlis.getPhotoFile().getAbsolutePath());
            imgTx.setImageBitmap(b);
        } else if (data != null && requestCode == Constant.ALBUM && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (null == cursor) {
                imgPath = data.getData().getPath();
            } else {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgPath = cursor.getString(columnIndex);
                cursor.close();
            }

            b = BitmapUtils.getDiskBitmap(imgPath);

            if (b == null) {
                AmUtlis.showToast("图片已被删除，或不存在");
                return;
            }
            imgTx.setImageBitmap(b);
        }

    }


    /**
     * 请求权限后的回调。
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Constant.PERMISSION_PHONE:
                if (grantResults == null || grantResults.length < 1) {
                    //用户取消授权
                    AmUtlis.showToast(" 授权失败");
                    return;
                }
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户授权 再次检测
                    PhotoPop.getInstance().checkCameraOrSDCradPermission();
                } else {
                    //用户取消授权
                    AmUtlis.showToast("你必须授权，才能进行下一步");
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onBackPressed() {
        AmUtlis.showCloseAlert(AddCustomerActivity.this);
    }
}
