package com.qiaotouxi.am.business.customer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.qiaotouxi.am.business.dao.DaoUtils;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.view.PhotoPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/7 0007.
 * 客户资料详情页面
 */

public class CustomerDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.img_tx)
    ImageView imgTx;
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
    @BindView(R.id.rb_male)
    RadioButton rbMale;
    @BindView(R.id.rb_female)
    RadioButton rbFemale;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.ll_buy_list)
    LinearLayout llBuyList;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    CustomerDao mCustomerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        ButterKnife.bind(this);
        mCustomerDao = DaoUtils.getCustomerByID(CustomerDetailsActivity.this, getIntent().getStringExtra(Constant.CUSTMER_ID));
        initViewData();
    }

    /**
     * 初始化view 及 data
     */
    private void initViewData() {
        //设置照片 及 基本信息
        String path = mCustomerDao.getPhoto_path();
        if (!TextUtils.isEmpty(path)) {
            imgPath = path;
            Bitmap bitmap = BitmapUtils.getDiskBitmap(path);
            imgTx.setImageBitmap(bitmap);
        }
        etName.setText(mCustomerDao.getName());
        etPhone.setText(mCustomerDao.getPhone());
        etCardID.setText(mCustomerDao.getCardId());
        etLocation.setText(mCustomerDao.getLocation());
        etBzxx.setText(mCustomerDao.getRemark());
        if (mCustomerDao.getSex() == 0) {
            rbFemale.setChecked(true);
            rbMale.setChecked(false);
        } else {
            rbFemale.setChecked(false);
            rbMale.setChecked(true);
        }

        btnDelete.setVisibility(View.VISIBLE);
        imgTx.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
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
            case R.id.btn_delete:
                delete();
                break;

        }
    }

    /**
     * 删除客户
     */
    private void delete() {

        if (mCustomerDao.getBuy()) {
            AmUtlis.showToast("该用户已购买设备，无法删除");
            return;
        }

        boolean b = DaoUtils.deleteCustomer(CustomerDetailsActivity.this, mCustomerDao);
        if (b) {
            AmUtlis.showToast("删除成功");
            AmUtlis.refreshCustomerManageData();
            finish();
        } else {
            AmUtlis.showToast("删除失败");
        }
    }

    /**
     * 拍照返回图片路径
     */
    private String imgPath;

    /**
     * 保存客户数据
     */
    private void saveData() {

        if (TextUtils.isEmpty(imgPath)) {
            AmUtlis.showToast("请上传照片");
            return;
        }
        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写姓名");
            return;
        }
        String phone = etPhone.getText().toString();
        if (TextUtils.isEmpty(phone)) {
            AmUtlis.showToast("请填写电话");
            return;
        }
        String cardId = etCardID.getText().toString();
        if (TextUtils.isEmpty(cardId)) {
            AmUtlis.showToast("请填写身份证号码");
            return;
        }
        String location = etLocation.getText().toString();
//        if(TextUtils.isEmpty(cardId)){
//            AmUtlis.showToast("请填写联系地址");
//            return;
//        }
        String bzxx = etBzxx.getText().toString();
//        if(TextUtils.isEmpty(cardId)){
//            AmUtlis.showToast("请填写备注信息");
//            return;
//        }
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
        CustomerDaoDao dao = App.getDaoSession(this).getCustomerDaoDao();

        CustomerDao unique = dao.queryRawCreate("where CARD_ID=? order by CARD_ID", cardId).unique();
        if (unique != null && unique.getCardId().equals(cardId)) {
            //更新数据
            unique.setPhoto_path(imgPath);
            unique.setName(name);
            unique.setPhone(phone);
            unique.setRemark(bzxx);
            unique.setSex(sex);
            unique.setLocation(location);
            dao.update(unique);
            AmUtlis.showToast("保存成功");
            AmUtlis.refreshCustomerManageData();
        } else {
            AmUtlis.showToast("保存失败");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == Constant.CAPTURE && resultCode == RESULT_OK) {
            //拍照返回
            Bundle extras = data.getExtras();
            Bitmap b = (Bitmap) extras.get("data");
            imgTx.setImageBitmap(b);
            imgPath = BitmapUtils.save(b);
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
            Bitmap bitmap = BitmapFactory.decodeFile(imgPath);
            imgTx.setImageBitmap(bitmap);
            imgPath = BitmapUtils.save(bitmap);
            AmUtlis.showLog("imgPath=" + imgPath);
        }

    }
}
