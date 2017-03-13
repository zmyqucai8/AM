package com.qiaotouxi.am.business.equipment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.EquipmentDao;
import com.qiaotouxi.am.business.dao.EquipmentDaoDao;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.BitmapUtils;
import com.qiaotouxi.am.framework.view.PhotoPop;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/1 0001.
 * 添加设备页面w
 */

public class AddEquipmentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.img_ccbh)
    ImageView img_ccbh;
    @BindView(R.id.img_fdjbh)
    ImageView img_fdjbh;
    private String imgPathFdjbh;//发动机编号返回的photo
    private String imgPathCcbh;//出厂编号返回的photo
    //当前选择的照片类型
    private int photoType = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_equipment);
        ButterKnife.bind(this);
        initView();
    }


    /**
     * 初始化view
     */
    private void initView() {
        img_ccbh.setOnClickListener(this);
        img_fdjbh.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fdjbh:
                photoType = Constant.PHOTO_FDJBH;
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.img_ccbh:
                photoType = Constant.PHOTO_CCBH;
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.btn_save:
                save();
                break;

        }

    }

    /**
     * 保存设备
     */
    private void save() {

        if (TextUtils.isEmpty(imgPathCcbh)) {
            AmUtlis.showToast("请上传出厂编号照片");
            return;
        }
        if (TextUtils.isEmpty(imgPathFdjbh)) {
            AmUtlis.showToast("请上传发动机编号照片");
            return;
        }
        AmUtlis.showLog("出厂编号path=" + imgPathCcbh);
        AmUtlis.showLog("发动机编号path=" + imgPathFdjbh);

        String name = etName.getText().toString();
        if (TextUtils.isEmpty(name)) {
            AmUtlis.showToast("请填写名称");
            return;
        }

        String pinpai = tvPp.getText().toString();
        if (TextUtils.isEmpty(pinpai)) {
            AmUtlis.showToast("请填写品牌");
            return;
        }
        String changjia = etCj.getText().toString();
        if (TextUtils.isEmpty(changjia)) {
            AmUtlis.showToast("请填写厂家");
            return;
        }
        String ccbh = etCcbh.getText().toString();
        if (TextUtils.isEmpty(ccbh)) {
            AmUtlis.showToast("请填写出厂编号");
            return;
        }
        String fdjbh = etFdjbh.getText().toString();
        if (TextUtils.isEmpty(fdjbh)) {
            AmUtlis.showToast("请填写发动机编号");
            return;
        }
        String bzxx = etBzxx.getText().toString();

        EquipmentDao dao = new EquipmentDao(imgPathFdjbh, imgPathCcbh, "", name, pinpai, fdjbh, changjia, ccbh, bzxx, false, false, "", "");

        EquipmentDaoDao equipmentDaoDao = App.getDaoSession(this).getEquipmentDaoDao();
        try {
            EquipmentDao unique = equipmentDaoDao.queryRawCreate("where ENGINE_ID=? order by ENGINE_ID", fdjbh).unique();

            if (unique != null && unique.getEngine_id().equals(fdjbh)) {
                //保存前根据填写的发动机编号 查询数据库， 如果存在此设备，提示设备重复
                AmUtlis.showToast("该发动机编号已被添加，请修改");
                return;
            }
            equipmentDaoDao.insert(dao);
            AmUtlis.showToast("添加成功");
            AmUtlis.refreshEquipmentManageData(Constant.EQUIPMENT_SOLD_NO);
            finish();
        } catch (Exception e) {
            AmUtlis.showToast("添加失败");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == Constant.CAPTURE && resultCode == RESULT_OK) {
            //拍照返回
            Bundle extras = data.getExtras();
            Bitmap b = (Bitmap) extras.get("data");
            setImgShow(b);
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
                }
            } else {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                if (photoType == Constant.PHOTO_FDJBH) {
                    imgPathFdjbh = cursor.getString(columnIndex);
                } else if (photoType == Constant.PHOTO_CCBH) {
                    imgPathCcbh = cursor.getString(columnIndex);
                }
                cursor.close();
            }
            Bitmap bitmap = null;
            if (photoType == Constant.PHOTO_FDJBH) {
                bitmap = BitmapFactory.decodeFile(imgPathFdjbh);
            } else if (photoType == Constant.PHOTO_CCBH) {
                bitmap = BitmapFactory.decodeFile(imgPathCcbh);
            }
            setImgShow(bitmap);
        }

    }

    /**
     * 设置img显示， 根据选择类型
     */
    private void setImgShow(Bitmap b) {
        if (photoType == Constant.PHOTO_FDJBH) {
            AmUtlis.deleteFile(imgPathFdjbh);
            imgPathFdjbh = BitmapUtils.save(b, BitmapUtils.IMG_TYPE_FDJBH);
            img_fdjbh.setImageBitmap(b);

        } else if (photoType == Constant.PHOTO_CCBH) {
            AmUtlis.deleteFile(imgPathCcbh);
            imgPathCcbh = BitmapUtils.save(b, BitmapUtils.IMG_TYPE_CCBH);
            img_ccbh.setImageBitmap(b);
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
        AmUtlis.showCloseAlert(AddEquipmentActivity.this);
    }
}
