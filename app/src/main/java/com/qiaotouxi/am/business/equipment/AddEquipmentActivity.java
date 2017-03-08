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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.alertview.OnItemClickListener;
import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
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
 * @Date 2017/3/1 0001.
 * 添加设备页面
 */

public class AddEquipmentActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_title)
    TextView tvTitle;
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
    @BindView(R.id.tv_add_photo)
    TextView tv_add_photo;
    @BindView(R.id.btn_save)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_equipment);
        ButterKnife.bind(this);
        initView();
    }

    AddEquipmentPhotoAdapter mAdapter;

    /**
     * 初始化view
     */
    private void initView() {


        mAdapter = new AddEquipmentPhotoAdapter(this, mImgPathList, 0);
        // 设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.setEmptyView(AmUtlis.getEmptyView(this, "请点击上方添加按钮添加照片"));
        mRecyclerView.setAdapter(mAdapter);
        tv_add_photo.setTypeface(AmUtlis.getTTF());
        tv_add_photo.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_photo:
                PhotoPop.getInstance().showPop(this);
                break;
            case R.id.btn_save:
                save(false, false, "");
                break;

        }

    }

    /**
     * 保存设备
     */
    private void save(boolean sell, boolean payment, String card_id) {

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
//        if (TextUtils.isEmpty(name)) {
//            AmUtlis.showToast("请填写发动机编号");
//            return;
//        }


        EquipmentDao dao = new EquipmentDao(photo_list, name, pinpai, fdjbh, changjia, ccbh, bzxx, sell, payment, "", card_id);

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

    private String imgPath;//当前拍照返回的一张图片

    public List<String> mImgPathList = new ArrayList<String>();//照片数量集合

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

        //TODO:
        if (true) {
            AmUtlis.showAlertView(AddEquipmentActivity.this, new OnItemClickListener() {
                @Override
                public void onItemClick(Object o, int position) {
                    AmUtlis.showLog(position + " = position");
                    if (position != -1) {
                        finish();
                    }

                }
            });

        } else {
            super.onBackPressed();
        }


    }
}
