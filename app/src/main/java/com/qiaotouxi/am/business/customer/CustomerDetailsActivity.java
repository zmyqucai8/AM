package com.qiaotouxi.am.business.customer;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.CustomerDao;
import com.qiaotouxi.am.business.dao.DaoUtils;
import com.qiaotouxi.am.business.dao.EquipmentDao;
import com.qiaotouxi.am.business.equipment.EquipmentSoldAdapter;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.utils.FileUtils;
import com.qiaotouxi.am.framework.view.PhotoPop;

import java.util.List;

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
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_buy_list)
    LinearLayout llBuyList;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    private CustomerDao mCustomerDao;//当前客户dao
    private EquipmentSoldAdapter mAdapter;//购机历史adapter
    private String imgPath;// 拍照返回图片路径
    private boolean isShowAlert = true;//提示框显示约束

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_customer);
        ButterKnife.bind(this);
        mCustomerDao = DaoUtils.getCustomerByPhone(CustomerDetailsActivity.this, getIntent().getStringExtra(Constant.CUSTMER_PHONE));
        initViewData();
    }


    /**
     * 初始化view 及 data
     */
    private void initViewData() {
        tvTitle.setText("客户资料");
        //设置照片 及 基本信息
        String path = mCustomerDao.getPhoto_path();
        if (!TextUtils.isEmpty(path)) {
            imgPath = path;
            Bitmap bitmap = BitmapUtils.getDiskBitmap(path);
            if (null != bitmap)
                imgTx.setImageBitmap(bitmap);
            else {
                imgTx.setImageResource(R.drawable.img_splash1);
            }
        }
        etName.setText(mCustomerDao.getName());
        etPhone.setText(mCustomerDao.getPhone());
        etCardID.setText(mCustomerDao.getCardId());
        etLocation.setText(mCustomerDao.getLocation());
        etBzxx.setText(mCustomerDao.getRemark());
        //设置性别
        if (mCustomerDao.getSex() == 0) {
            rbFemale.setChecked(true);
            rbMale.setChecked(false);
        } else {
            rbFemale.setChecked(false);
            rbMale.setChecked(true);
        }
        btnDelete.setVisibility(View.VISIBLE);
        //设置购机历史记录。 如果有的话
        String engine_id_list = mCustomerDao.getEngine_id_list();
        if (!TextUtils.isEmpty(engine_id_list)) {
            llBuyList.setVisibility(View.VISIBLE);
            AmUtlis.showLog("engine_id_list=" + engine_id_list);
            List<EquipmentDao> equipmentByIDs = DaoUtils.getEquipmentByIDs(CustomerDetailsActivity.this, engine_id_list);
            mAdapter = new EquipmentSoldAdapter(CustomerDetailsActivity.this, equipmentByIDs);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerDetailsActivity.this));
            mAdapter.isFirstOnly(true);
            mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
            mRecyclerView.setAdapter(mAdapter);
            if (equipmentByIDs.size() > 1) {
                ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
                layoutParams.height = AmUtlis.dp2px(300);
                mRecyclerView.setLayoutParams(layoutParams);
            }

        }
        //点击事件
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

        if (!isShowAlert) {
            return;
        }
        isShowAlert = false;
        new AlertView.Builder().setContext(CustomerDetailsActivity.this)
                .setStyle(AlertView.Style.Alert)
                .setTitle("温馨提示")
                .setMessage("你确定要删除这个客户吗")
                .setCancelText("取消")
                .setDestructive("确定")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        isShowAlert = true;
                        if (position != -1) {
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
                    }
                })
                .build()
                .show();

    }


    /**
     * 保存客户数据
     */
    private void saveData() {

//        if () {
//            AmUtlis.showToast("请上传照片");
//            return;
//        }
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
        if (txBitmap != null) {
            imgPath = BitmapUtils.saveImg(txBitmap, mCustomerDao.getDirPath(), BitmapUtils.IMG_TYPE_KHTX, mCustomerDao.getPhoto_path());
        } else {
            imgPath = mCustomerDao.getPhoto_path();
        }
        //更新数据
        mCustomerDao.setPhoto_path(imgPath);
        mCustomerDao.setName(name);
        mCustomerDao.setPhone(phone);
        mCustomerDao.setCardId(cardId);
        mCustomerDao.setRemark(bzxx);
        mCustomerDao.setSex(sex);
        mCustomerDao.setLocation(location);
        DaoUtils.updateCustomerDao(this, mCustomerDao);
        AmUtlis.showToast("修改成功");
        String text = "姓名：" + name + "\r\n性别：" + (sex == 1 ? "男" : "女") + "\r\n电话：" + phone + "\r\n身份证：" + cardId + "\r\n联系地址：" + location + "\r\n备注信息：" + bzxx;
        FileUtils.writeTxt(BitmapUtils.getFilePath() + mCustomerDao.getDirPath() + "/" + "客户资料.txt", text);
        AmUtlis.refreshCustomerManageData();
    }

    private Bitmap txBitmap;//拍照或相册选择的头像bitmap

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CAPTURE && resultCode == RESULT_OK) {
            //拍照返回
            txBitmap = BitmapUtils.getDiskBitmap(AmUtlis.getPhotoFile().getAbsolutePath());
            imgTx.setImageBitmap(txBitmap);
        } else if (data != null && requestCode == Constant.ALBUM && resultCode == RESULT_OK) {
            if (!TextUtils.isEmpty(imgPath)) {
                AmUtlis.deleteFile(imgPath);
            }
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
            txBitmap = BitmapUtils.getDiskBitmap(imgPath);
            if (txBitmap == null) {
                AmUtlis.showToast("图片被删除或找不到");
                return;
            }
            imgTx.setImageBitmap(txBitmap);

        }

    }

    @Override
    public void onBackPressed() {
        AmUtlis.showCloseAlert(CustomerDetailsActivity.this);
    }
}
