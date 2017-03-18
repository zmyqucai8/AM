package com.qiaotouxi.am.business.equipment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.utils.FileUtils;
import com.qiaotouxi.am.framework.view.PhotoPop;

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
    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.img_ccbh)
    ImageView img_ccbh;
    @BindView(R.id.img_fdjbh)
    ImageView img_fdjbh;
    @BindView(R.id.img_rjhy)
    ImageView img_rjhy;
    @BindView(R.id.line)
    View line;
    @BindView(R.id.et_name)
    EditText etName;
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
    @BindView(R.id.ll_rjhy)
    LinearLayout ll_rjhy;
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
    private boolean isShowAlert = true;//提示框显示约束
    private boolean isScrollViewToButtom;//是否自动滚动到底部过
    private EquipmentDao mEquipmentDao; //当前设备dao
    private int mStartType;//跳转类型
    private CustomerDao customer;//客户数据 ：如果当前设备是已出售的话，去查询赋值， 或未出售状态选择客户时赋值
    private int photoType = -1;    //当前选择的照片类型
    private String imgPathFdjbh = "";//发动机编号返回的photo
    private String imgPathCcbh = "";//出厂编号返回的photo
    private String imgPathRjhy = "";//人机合影返回的photo

    String engine_id;//未修改的发动机编号,如果修改了发动机编号,并且设备已出售状态,需要更新客户数据
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipment_details_activity);
        ButterKnife.bind(this);
        mEquipmentDao = DaoUtils.getEquipmentByID(EquipmentDetailsActivity.this, getIntent().getStringExtra(Constant.EQUIPMENT_ID));
        mStartType = getIntent().getIntExtra(Constant.START_TYPE, Constant.EQUIPMENT_ALL);
        AmUtlis.showLog("设备详情=" + mEquipmentDao.toString());
        engine_id = mEquipmentDao.getEngine_id();
        initViewData();
    }

    /**
     * 初始化view点击事件数据等
     */
    private void initViewData() {
        //设置出售状态
        if (mEquipmentDao.getSell()) {
            tvStatus.setBackgroundResource(R.drawable.shape_oval_red_bg);
            tvStatus.setText(mEquipmentDao.getPayment() ? "已付款" : "未付款");
            rb_pay_yes.setChecked(mEquipmentDao.getPayment());
            rb_pay_no.setChecked(!mEquipmentDao.getPayment());
            if (!TextUtils.isEmpty(mEquipmentDao.getPhoto_rjhy())) {
                imgPathRjhy = mEquipmentDao.getPhoto_rjhy();
                Bitmap bitmap = BitmapUtils.getDiskBitmap(imgPathRjhy);
                if (null != bitmap)
                    img_rjhy.setImageBitmap(bitmap);
                else {
                    img_rjhy.setImageResource(R.drawable.img_splash1);
                }
            }
            btnSell.setVisibility(View.GONE);

        } else {
            tvStatus.setBackgroundResource(R.drawable.shape_oval_gray_bg);
            tvStatus.setText("未出售");
            btnSell.setVisibility(View.VISIBLE);
        }
        //设置照片信息
        if (!TextUtils.isEmpty(mEquipmentDao.getPhoto_ccbh())) {
            imgPathCcbh = mEquipmentDao.getPhoto_ccbh();
            Bitmap bitmap = BitmapUtils.getDiskBitmap(imgPathCcbh);
            if (null != bitmap)
                img_ccbh.setImageBitmap(bitmap);
            else {
                img_ccbh.setImageResource(R.drawable.img_splash1);
            }
        }
        if (!TextUtils.isEmpty(mEquipmentDao.getPhoto_fdjbh())) {
            imgPathFdjbh = mEquipmentDao.getPhoto_fdjbh();
            Bitmap bitmap = BitmapUtils.getDiskBitmap(imgPathFdjbh);
            if (null != bitmap)
                img_fdjbh.setImageBitmap(bitmap);
            else {

                img_fdjbh.setImageResource(R.drawable.img_splash1);
            }
        }

        //设置名称 品牌 厂家 出厂编号 发动机编号 备注信息
        etName.setText(mEquipmentDao.getName());
        etCcbh.setText(mEquipmentDao.getFactory_id());
        etFdjbh.setText(mEquipmentDao.getEngine_id());
        etBzxx.setText(mEquipmentDao.getRemark());
        img_rjhy.setOnClickListener(this);
        img_ccbh.setOnClickListener(this);
        img_fdjbh.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnSell.setOnClickListener(this);

        //设置已出售设备数据
        if (mEquipmentDao.getSell()) {
            setSoldYesViewData();
        }
    }


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
            case R.id.img_rjhy:
                photoType = Constant.PHOTO_RJHY;
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.img_fdjbh:
                photoType = Constant.PHOTO_FDJBH;
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.img_ccbh:
                photoType = Constant.PHOTO_CCBH;
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

        if (!isShowAlert) {
            return;
        }
        isShowAlert = false;
        new AlertView.Builder().setContext(EquipmentDetailsActivity.this)
                .setStyle(AlertView.Style.Alert)
                .setTitle("温馨提示")
                .setMessage("你确定要删除这个设备吗")
                .setCancelText("取消")
                .setDestructive("确定")
                .setOthers(null)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        isShowAlert = true;
                        if (position != -1) {
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
                    }
                })
                .build()
                .show();


    }


    /**
     * 保存设备
     */
    private void save(boolean sell, boolean payment, String phone, String date) {

        if (sell) {
            if (!mEquipmentDao.getSell() && rjhyBitmap == null) {
                AmUtlis.showToast("请上传人机合影照片");
                AmUtlis.scrollViewToButtomTop(scrollView, true);
                return;
            }
        }
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写品牌型号");
            return;
        }

        String ccbh = etCcbh.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写出厂编号");
            return;
        }
        String fdjbh = etFdjbh.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写发动机编号");
            return;
        }
        String bzxx = etBzxx.getText().toString().trim();
        saveImg();
        //更新数据
        mEquipmentDao.setPhoto_ccbh(imgPathCcbh);
        mEquipmentDao.setPhoto_fdjbh(imgPathFdjbh);
        mEquipmentDao.setPhoto_rjhy(imgPathRjhy);
        mEquipmentDao.setName(name);
        mEquipmentDao.setEngine_id(fdjbh);
        mEquipmentDao.setFactory_id(ccbh);
        mEquipmentDao.setRemark(bzxx);
        mEquipmentDao.setSell(sell);
        mEquipmentDao.setPhone(phone);
        mEquipmentDao.setPayment(payment);
        mEquipmentDao.setDate(date);
        App.getDaoSession(EquipmentDetailsActivity.this).getEquipmentDaoDao().update(mEquipmentDao);
        //更新客户信息
        if (null != customer && sell) {
            customer.setBuy(true);
            String engine_id_list = customer.getEngine_id_list();
            if (!engine_id_list.contains(fdjbh)) {//防止已出售设备修改数据时重复添加
                StringBuilder engineIDs = new StringBuilder();
                if (!TextUtils.isEmpty(engine_id_list)) {
                    if (engine_id_list.contains(engine_id)) {//如果是已出售则修改否则添加
                        engineIDs.append(engine_id_list.replace(engine_id, fdjbh));
                    } else {
                        engineIDs.append(engine_id_list);
                        engineIDs.append("," + fdjbh);
                    }
                } else {
                    engineIDs.append(fdjbh);
                }

                customer.setEngine_id_list(engineIDs.toString());
            }
            DaoUtils.updateCustomerDao(EquipmentDetailsActivity.this, customer);
            if ("保存修改".equals(btnSave.getText().toString())) {
                AmUtlis.showToast("修改成功");
            } else {
                //出售成功， 把设备照片移动到当前客户文件夹下,注意数据库的路径也要修改
                String equipmentDirPath = mEquipmentDao.getDirPath();
                String customerDirPath = customer.getDirPath();
                FileUtils.moveFolder(BitmapUtils.getFilePath() + equipmentDirPath, BitmapUtils.getFilePath() + customerDirPath);
                mEquipmentDao.setPhoto_rjhy(mEquipmentDao.getPhoto_rjhy().replace(equipmentDirPath, customerDirPath));
                mEquipmentDao.setPhoto_fdjbh(mEquipmentDao.getPhoto_fdjbh().replace(equipmentDirPath, customerDirPath));
                mEquipmentDao.setPhoto_ccbh(mEquipmentDao.getPhoto_ccbh().replace(equipmentDirPath, customerDirPath));
                mEquipmentDao.setDirPath(customerDirPath);
                App.getDaoSession(EquipmentDetailsActivity.this).getEquipmentDaoDao().update(mEquipmentDao);
                AmUtlis.showToast("出售成功");
                //
            }
        } else {
            AmUtlis.showToast("保存成功");
        }
        //更新设备txt文档
        String text = "品牌型号：" + name + "\r\n出厂编号：" + ccbh + "\r\n发动机编号：" + fdjbh + "\r\n备注信息：" + bzxx;
        if (sell) {
            FileUtils.writeTxt(BitmapUtils.getFilePath() + customer.getDirPath() + "/" + mEquipmentDao.getTxtName() + ".txt", text);
        } else {
            FileUtils.writeTxt(BitmapUtils.getFilePath() + mEquipmentDao.getDirPath() + "/" + mEquipmentDao.getTxtName() + ".txt", text);
        }
        if (sell) {
            AmUtlis.refreshEquipmentManageData(Constant.EQUIPMENT_SOLD_YES);
        }
        AmUtlis.refreshEquipmentManageData(Constant.EQUIPMENT_SOLD_NO);
        finish();
    }

    /**
     * 保存img
     */
    private void saveImg() {
        if (ccbhBitmap != null) {
            imgPathCcbh = BitmapUtils.saveImg(ccbhBitmap, mEquipmentDao.getDirPath(), BitmapUtils.IMG_TYPE_CCBH, mEquipmentDao.getPhoto_ccbh());
        } else {
            imgPathCcbh = mEquipmentDao.getPhoto_ccbh();
        }
        if (fdjbhBitmap != null) {
            imgPathFdjbh = BitmapUtils.saveImg(fdjbhBitmap, mEquipmentDao.getDirPath(), BitmapUtils.IMG_TYPE_FDJBH, mEquipmentDao.getPhoto_fdjbh());
        } else {
            imgPathFdjbh = mEquipmentDao.getPhoto_fdjbh();
        }
        if (rjhyBitmap != null) {
            imgPathRjhy = BitmapUtils.saveImg(rjhyBitmap, mEquipmentDao.getDirPath(), BitmapUtils.IMG_TYPE_RJHY, mEquipmentDao.getPhoto_rjhy());
        } else {
            imgPathRjhy = mEquipmentDao.getPhoto_rjhy();
        }
    }

    /**
     * 删除上一张图片
     */
    private void deletePhoto() {
        if (photoType == Constant.PHOTO_FDJBH) {//发动机编号照片
            if (!TextUtils.isEmpty(imgPathFdjbh))
                AmUtlis.deleteFile(imgPathFdjbh);
        } else if (photoType == Constant.PHOTO_CCBH) {//出厂编号照片
            if (!TextUtils.isEmpty(imgPathCcbh))
                AmUtlis.deleteFile(imgPathCcbh);
        } else if (photoType == Constant.PHOTO_RJHY) {//人机合影照片
            if (!TextUtils.isEmpty(imgPathRjhy))
                AmUtlis.deleteFile(imgPathRjhy);
        }
    }


    private Bitmap fdjbhBitmap;
    private Bitmap ccbhBitmap;
    private Bitmap rjhyBitmap;

    /**
     * 设置img显示， 根据选择类型
     */
    private void setImgShow(Bitmap b) {
        if (photoType == Constant.PHOTO_FDJBH) {//发动机编号照片
            fdjbhBitmap = b;
//            imgPathFdjbh = BitmapUtils.saveImg(b, mEquipmentDao.getDirPath(), BitmapUtils.IMG_TYPE_FDJBH, mEquipmentDao.getPhoto_fdjbh());
            img_fdjbh.setImageBitmap(b);

        } else if (photoType == Constant.PHOTO_CCBH) {//出厂编号照片
            ccbhBitmap = b;
//            imgPathCcbh = BitmapUtils.saveImg(b, mEquipmentDao.getDirPath(), BitmapUtils.IMG_TYPE_CCBH, mEquipmentDao.getPhoto_ccbh());
            img_ccbh.setImageBitmap(b);
        } else if (photoType == Constant.PHOTO_RJHY) {//人机合影照片
            rjhyBitmap = b;
//            imgPathRjhy = BitmapUtils.saveImg(b, mEquipmentDao.getDirPath(), BitmapUtils.IMG_TYPE_RJHY, mEquipmentDao.getPhoto_rjhy());
            img_rjhy.setImageBitmap(b);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.CAPTURE && resultCode == RESULT_OK) {
            //拍照返回
            setImgShow(BitmapUtils.getDiskBitmap(AmUtlis.getPhotoFile().getAbsolutePath()));
        } else if (data != null && requestCode == Constant.ALBUM && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            if (null == cursor) {
                if (photoType == Constant.PHOTO_FDJBH) {
                    imgPathFdjbh = data.getData().getPath();
                } else if (photoType == Constant.PHOTO_CCBH) {
                    imgPathCcbh = data.getData().getPath();
                } else if (photoType == Constant.PHOTO_RJHY) {
                    imgPathRjhy = data.getData().getPath();
                }
            } else {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if (photoType == Constant.PHOTO_FDJBH) {
                    imgPathFdjbh = cursor.getString(columnIndex);
                } else if (photoType == Constant.PHOTO_CCBH) {
                    imgPathCcbh = cursor.getString(columnIndex);
                } else if (photoType == Constant.PHOTO_RJHY) {
                    imgPathRjhy = cursor.getString(columnIndex);
                }

                cursor.close();
            }
            Bitmap bitmap = null;
            if (photoType == Constant.PHOTO_FDJBH) {
                bitmap = BitmapUtils.getDiskBitmap(imgPathFdjbh);
            } else if (photoType == Constant.PHOTO_CCBH) {
                bitmap = BitmapUtils.getDiskBitmap(imgPathCcbh);
            } else if (photoType == Constant.PHOTO_RJHY) {
                bitmap = BitmapUtils.getDiskBitmap(imgPathRjhy);
            }

            if (bitmap == null) {
                AmUtlis.showToast("图片被删除或不存在");
                return;
            }
            setImgShow(bitmap);

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
        ll_gjzxx.setVisibility(View.VISIBLE);
        ll_ddxx.setVisibility(View.VISIBLE);
        ll_rjhy.setVisibility(View.VISIBLE);
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
                if (TextUtils.isEmpty(mEquipmentDao.getPhone())) {
                    //未出售 保存信息 需要显示提示框
                    if (!isShowAlert) {
                        return;
                    }
                    isShowAlert = false;
                    new AlertView.Builder().setContext(EquipmentDetailsActivity.this)
                            .setStyle(AlertView.Style.Alert)
                            .setTitle("温馨提示")
                            .setMessage("请检查设备照片是否包含如下\n1.出厂编号照片\n2.发动机编号照片\n3.人机合影")
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
                } else {
                    //已出售，修改信息 不需要提示框
                    save(true, yes, customerDao.getPhone(), TextUtils.isEmpty(mEquipmentDao.getDate()) ? AmUtlis.getYMD() : mEquipmentDao.getDate());
                }

            }
        });
        tv_kh_name.setText("姓名　　　　　　" + customerDao.getName());
        tv_kh_phone.setText("电话　　　　　　" + customerDao.getPhone());
        tv_kh_cardid.setText("身份证　　　　　" + customerDao.getCardId());
        tv_kh_bzxx.setText("备注信息　　　　" + customerDao.getRemark());
        tv_kh_location.setText("联系地址　　　　" + customerDao.getLocation());
        tv_kh_sex.setText(customerDao.getSex() == 0 ? "性别　　　　　　女" : "性别　　　　　　男");
        if (!TextUtils.isEmpty(customerDao.getPhoto_path())) {
            img_tx.setImageBitmap(BitmapUtils.getDiskBitmap(customerDao.getPhoto_path()));
        }
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
