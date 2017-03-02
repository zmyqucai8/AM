package com.qiaotouxi.am.framework.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;

/**
 * @Created by zmy.
 * @Date 2017/3/2 0002.
 * @Describe
 */

public class PhotoPop implements View.OnClickListener {

    public static PhotoPop mPhotoPop;
    private PopupWindow mPop;
    private Activity mAct;
    //只能显示一个的约束 默认可以显示
    private boolean isShow = true;

    /**
     * 获取实例
     *
     * @return
     */
    public static PhotoPop getInstance() {
        if (mPhotoPop == null) {
            return mPhotoPop = new PhotoPop();
        } else {
            return mPhotoPop;
        }
    }


    /**
     * @param activity
     */
    public void showPop(Activity activity) {
        mAct = activity;
        if (isShow) {
            if (mPop == null) {
                initPop();
            }
            AmUtlis.showDarkScreen(activity);
            mPop.showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            isShow = false;
        }
    }

    /**
     * 初始化pop
     */
    private void initPop() {
        View popView = View.inflate(mAct, R.layout.photo_pop, null);
        popView.findViewById(R.id.tv_pictures).setOnClickListener(this);
        popView.findViewById(R.id.tv_album).setOnClickListener(this);
        popView.findViewById(R.id.tv_cancel).setOnClickListener(this);
        mPop = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPop.setContentView(popView);
        mPop.setBackgroundDrawable(new BitmapDrawable());
        mPop.setTouchable(true);
        mPop.setFocusable(true);
        mPop.setAnimationStyle(R.style.PopupAnimation);
        mPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                AmUtlis.hideDarkScreen(mAct);
                isShow = true;
            }
        });


    }

    public static int startTag;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pictures:
                //拍照
                startTag = 0;
                checkCameraOrSDCradPermission();
                break;
            case R.id.tv_album:
                //打开相册
                startTag = 1;
                checkCameraOrSDCradPermission();
                break;
            case R.id.tv_cancel:
                //取消关闭pop
                break;
        }
        mPop.dismiss();
    }


    /**
     * 检测拍照 及 sd卡读写 权限
     */
    public void checkCameraOrSDCradPermission() {

        if (Build.VERSION.SDK_INT >= 23) {
            int phpnePermission = ContextCompat.checkSelfPermission(mAct, Manifest.permission.READ_EXTERNAL_STORAGE);
            int phpnePermission2 = ContextCompat.checkSelfPermission(mAct, Manifest.permission.CAMERA);
            if (phpnePermission != PackageManager.PERMISSION_GRANTED || phpnePermission2 != PackageManager.PERMISSION_GRANTED) {
                //没有授权，去请求授权
                ActivityCompat.requestPermissions(mAct, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constant.PERMISSION_PHONE);
                return;
            } else {
                //有权限， 继续跳转
                startActivity(startTag);
            }
        } else {
            //版本低于23 直接跳转
            startActivity(startTag);
        }

    }

    /**
     * 跳转页面
     *
     * @type 0打开相机 1打开相册
     */
    public void startActivity(int type) {
        if (type == 0) {
            Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mAct.startActivityForResult(it, Constant.CAPTURE);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            mAct.startActivityForResult(intent, Constant.ALBUM);
        }
    }

}
