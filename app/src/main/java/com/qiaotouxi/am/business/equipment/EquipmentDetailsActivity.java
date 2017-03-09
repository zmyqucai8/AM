package com.qiaotouxi.am.business.equipment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.customer.CustomerSelectActivity;
import com.qiaotouxi.am.business.dao.CustomerDao;
import com.qiaotouxi.am.business.dao.DaoUtils;
import com.qiaotouxi.am.business.dao.EquipmentDao;
import com.qiaotouxi.am.business.dao.EquipmentDaoDao;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.view.PhotoPop;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/7 0007.
 * 设备详情页面 跳转必传参数
 * 1. Constant.EQUIPMENT_ID  value =  ENGINE_ID
 * 跳转传递过来的设备id 即发动机编号，不传对象是因为序列化后数据库无法获取对象实例
 * 2. 跳转类型 START_TYPE
 * 方便刷新具体页面， 不用做多余查询数据
 */
public class EquipmentDetailsActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_add_photo)
    TextView tv_add_photo;
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.myrecycler_layout)
    RecyclerView mRecyclerView;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_pp)
    EditText tvPp;
    @BindView(R.id.et_cj)
    EditText etCj;
    @BindView(R.id.et_ccbh)
    EditText etCcbh;
    @BindView(R.id.et_fdjbh)
    EditText etFdjbh;
    @BindView(R.id.et_bzxx)
    EditText etBzxx;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.btn_sell)
    Button btnSell;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    @BindView(R.id.ll_gjzxx)
    LinearLayout ll_gjzxx;
    @BindView(R.id.tv_kh_name)
    TextView tv_kh_name;
    @BindView(R.id.tv_kh_puhone)
    TextView tv_kh_phone;
    @BindView(R.id.tv_kh_cardid)
    TextView tv_kh_cardid;
    @BindView(R.id.tv_kh_location)
    TextView tv_kh_location;
    @BindView(R.id.tv_kh_bzxx)
    TextView tv_kh_bzxx;
    @BindView(R.id.tv_kh_sex)
    TextView tv_kh_sex;
    @BindView(R.id.ll_ddxx)
    LinearLayout ll_ddxx;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rb_pay_yes)
    RadioButton rb_pay_yes;
    @BindView(R.id.rb_pay_no)
    RadioButton rb_pay_no;
    @BindView(R.id.img_tx)
    ImageView img_tx;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    private boolean isShowAlert = true;

    boolean isScrollViewToButtom;//是否自动滚动到底部过
    AddEquipmentPhotoAdapter mAdapter;
    public List<String> mImgPathList = new ArrayList<String>();//照片数量集合
    private String imgPath;//当前拍照返回的一张图片
    EquipmentDao mEquipmentDao;
    private int mStartType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_details_activity);
        ButterKnife.bind(this);
        mEquipmentDao = DaoUtils.getEquipmentByID(EquipmentDetailsActivity.this, getIntent().getStringExtra(Constant.EQUIPMENT_ID));
        mStartType = getIntent().getIntExtra(Constant.START_TYPE, Constant.EQUIPMENT_ALL);
        AmUtlis.showLog("设备详情=" + mEquipmentDao.toString());
        initViewData();
    }

    /**
     * 初始化view点击事件数据等
     */
    private void initViewData() {

        mImgPathList = AmUtlis.splitStringByChar(",", mEquipmentDao.getPhoto_list());
        mAdapter = new AddEquipmentPhotoAdapter(EquipmentDetailsActivity.this, mImgPathList, 1);
        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.setEmptyView(AmUtlis.getEmptyView(this, "请点击上方添加按钮添加照片"));
        mRecyclerView.setAdapter(mAdapter);
        tv_add_photo.setTypeface(AmUtlis.getTTF());

        //设置出售状态
        if (mEquipmentDao.getSell()) {
            tvStatus.setBackgroundResource(R.drawable.shape_oval_red_bg);
            tvStatus.setText(mEquipmentDao.getPayment() ? "已付款" : "未付款");
            rb_pay_yes.setChecked(mEquipmentDao.getPayment());
            rb_pay_no.setChecked(!mEquipmentDao.getPayment());
            btnSell.setVisibility(View.GONE);
        } else {
            tvStatus.setBackgroundResource(R.drawable.shape_oval_gray_bg);
            tvStatus.setText("未出售");
            btnSell.setVisibility(View.VISIBLE);
        }
        //设置名称 品牌 厂家 出厂编号 发动机编号 备注信息
        etName.setText(mEquipmentDao.getName());
        tvPp.setText(mEquipmentDao.getBrand());
        etCj.setText(mEquipmentDao.getManufacturer());
        etCcbh.setText(mEquipmentDao.getFactory_id());
        etFdjbh.setText(mEquipmentDao.getEngine_id());
        etBzxx.setText(mEquipmentDao.getRemark());


        tv_add_photo.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSell.setOnClickListener(this);

        if (mEquipmentDao.getSell()) {
            setSoldYesViewData();
        }

    }

    CustomerDao customer;//客户数据

    /**
     * 设置已出售状态下的ui
     */
    private void setSoldYesViewData() {
        String phone = mEquipmentDao.getPhone();
        //设置客户信息
        customer = DaoUtils.getCustomerByPhone(EquipmentDetailsActivity.this, phone);
        setSoldData(customer, true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_photo:
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.btn_save:
                save(false, false, "", "");
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_sell:
                sell();
                break;

        }
    }

    /**
     * 出售设备
     */
    private void sell() {
        Intent intent = new Intent(EquipmentDetailsActivity.this, CustomerSelectActivity.class);
        startActivityForResult(intent, Constant.CUSTMER_SELECT_REQUEST_CODE);
    }


    /**
     * 删除设备
     */
    private void delete() {

        if (mEquipmentDao.getSell()) {
            AmUtlis.showToast("该设备已出售，无法删除");
            return;
        }

        boolean b = DaoUtils.deleteEquipment(EquipmentDetailsActivity.this, mEquipmentDao);
        if (b) {
            AmUtlis.showToast("删除成功");
            AmUtlis.refreshEquipmentManageData(mStartType);
            finish();
        } else {
            AmUtlis.showToast("删除失败");
        }
    }

    /**
     * 保存设备
     */
    private void save(boolean sell, boolean payment, String phone, String date) {

        if (mImgPathList.size() < 3) {
            AmUtlis.showToast("请至少添加3张设备照片");
            return;
        }
        String photo_list;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < mImgPathList.size(); i++) {

            if (i != mImgPathList.size() - 1) {
                builder.append(mImgPathList.get(i) + ",");
            } else {
                builder.append(mImgPathList.get(i));
            }
        }
        photo_list = builder.toString();
        AmUtlis.showLog("photo_list=" + photo_list);

        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写名称");
            return;
        }
        String pinpai = tvPp.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写品牌");
            return;
        }
        String changjia = etCj.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写厂家");
            return;
        }
        String ccbh = etCcbh.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写出厂编号");
            return;
        }
        String fdjbh = etFdjbh.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写发动机编号");
            return;
        }
        String bzxx = etBzxx.getText().toString();
//        EquipmentDao dao = new EquipmentDao(photo_list, name, pinpai, fdjbh, changjia, ccbh, bzxx, false, false, new Date());
        EquipmentDaoDao equipmentDaoDao = App.getDaoSession(this).getEquipmentDaoDao();
//        try {
        EquipmentDao unique = equipmentDaoDao.queryRawCreate("where ENGINE_ID=? order by ENGINE_ID", fdjbh).unique();
        if (unique != null && unique.getEngine_id().equals(fdjbh)) {
            //更新数据
            unique.setPhoto_list(photo_list);
            unique.setName(name);
            unique.setBrand(pinpai);
            unique.setEngine_id(fdjbh);
            unique.setManufacturer(changjia);
            unique.setFactory_id(ccbh);
            unique.setRemark(bzxx);
            unique.setSell(sell);
            unique.setPhone(phone);
            unique.setPayment(payment);
            unique.setDate(date);
            equipmentDaoDao.update(unique);

            //更新客户信息
            if (null != customer && sell) {
                customer.setBuy(true);
                String engine_id_list = customer.getEngine_id_list();

                StringBuilder engineIDs = new StringBuilder();
                if (!TextUtils.isEmpty(engine_id_list)) {
                    engineIDs.append(engine_id_list);
                    engineIDs.append("," + fdjbh);
                } else {
                    engineIDs.append(fdjbh);
                }
                customer.setEngine_id_list(engineIDs.toString());
                DaoUtils.updateCustomerDao(EquipmentDetailsActivity.this, customer);
                AmUtlis.showToast("出售成功");
            } else {
                AmUtlis.showToast("保存成功");
            }
            if (sell) {

                AmUtlis.refreshEquipmentManageData(Constant.EQUIPMENT_SOLD_YES);
            } else {
                AmUtlis.refreshEquipmentManageData(mStartType);
            }

            finish();
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
//            imgTx.setImageBitmap(b);
            imgPath = BitmapUtils.save(b);
            mImgPathList.add(imgPath);
            mAdapter.setNewData(mImgPathList);
//            mAdapter.notifyDataSetChanged();
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
//            imgTx.setImageBitmap(bitmap);
            imgPath = BitmapUtils.save(bitmap);
            AmUtlis.showLog("imgPath=" + imgPath);
            mImgPathList.add(imgPath);
            mAdapter.setNewData(mImgPathList);
        } else if (data != null && requestCode == Constant.CUSTMER_SELECT_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //选择客户返回数据 客户id
            String phone = data.getStringExtra(Constant.CUSTMER_PHONE);
            customer = DaoUtils.getCustomerByPhone(EquipmentDetailsActivity.this, phone);
            AmUtlis.showLog("选择客户返回数据=" + customer.toString());

            setSoldData(customer, false);

        }

    }

    /**
     * 在选择客户返回后，数据处理，显示选择的客户信息
     *
     * @param customerDao
     * @param isSold      是否已出售状态下设置购机者及订单信息
     */
    private void setSoldData(final CustomerDao customerDao, boolean isSold) {
        //TODO：当选择了客户时候， 应该要设置个临时状态， 在未保存的情况下点击返回出现弹框提示
        ll_gjzxx.setVisibility(View.VISIBLE);
        ll_ddxx.setVisibility(View.VISIBLE);
        if (!isSold) {
            btnSell.setText("重新选择客户");
            tvTitle.setText("出售设备");
            btnSave.setText("确定出售");
        } else {
            btnSell.setVisibility(View.GONE);
        }

        btnDelete.setVisibility(View.GONE);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean no = rb_pay_no.isChecked();
                final boolean yes = rb_pay_yes.isChecked();
                if (!no && !yes) {
                    AmUtlis.showToast("请选择是否付款");
                    return;
                }
                if (!isShowAlert) {
                    return;
                }
                isShowAlert = false;
                new AlertView.Builder().setContext(EquipmentDetailsActivity.this)
                        .setStyle(AlertView.Style.Alert)
                        .setTitle("温馨提示")
                        .setMessage("请检查设备照片是否包含如下\n1.发动机编号照片\n2.出厂编号照片\n3.人机合影")
                        .setCancelText("查看照片")
                        .setDestructive("确定出售")
                        .setOthers(null)
                        .setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                isShowAlert = true;
                                if (position != -1) {
                                    save(true, yes, customerDao.getPhone(), TextUtils.isEmpty(mEquipmentDao.getDate()) ? AmUtlis.getYMD() : mEquipmentDao.getDate());
                                } else {
                                    AmUtlis.scrollViewToButtomTop(scrollView, true);
                                }
                            }
                        })
                        .build()
                        .show();


            }
        });
        tv_kh_name.setText("姓名　　　　　　" + customerDao.getName());
        tv_kh_phone.setText("电话　　　　　　" + customerDao.getPhone());
        tv_kh_cardid.setText("身份证　　　　　" + customerDao.getCardId());
        tv_kh_bzxx.setText("备注信息　　　　" + customerDao.getRemark());
        tv_kh_location.setText("联系地址　　　　" + customerDao.getLocation());
        tv_kh_sex.setText(customerDao.getSex() == 0 ? "性别　　　　　　女" : "性别　　　　　　男");
        img_tx.setImageBitmap(BitmapUtils.getDiskBitmap(customerDao.getPhoto_path()));

//      if(TextUtils.isEmpty(mEquipmentDao.getDate().toString()))

        tv_date.setText("购机日期　　　　" + (TextUtils.isEmpty(mEquipmentDao.getDate()) ? AmUtlis.getYMD() : mEquipmentDao.getDate()));

        AmUtlis.showLog("scrollView.getHeight()=" + scrollView.getHeight());

        if (!isScrollViewToButtom && !mEquipmentDao.getSell()) {//自动滚动到底部的处理
            AmUtlis.scrollViewToButtomTop(scrollView, false);
            isScrollViewToButtom = !isScrollViewToButtom;
        }
    }

    @Override
    public void onBackPressed() {
        AmUtlis.showCloseAlert(EquipmentDetailsActivity.this);

    }
}
