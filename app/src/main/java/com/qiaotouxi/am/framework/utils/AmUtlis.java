package com.qiaotouxi.am.framework.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.TextView;
import android.widget.Toast;

import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.equipment.EquipmentManageEvent;
import com.qiaotouxi.am.framework.base.Constant;

import de.greenrobot.event.EventBus;

/**
 * Created by Yyyyyyy on 2017/2/28.
 */
public class AmUtlis {


    /**
     * 获取ttf字体库
     *
     * @return
     */
    public static Typeface getTTF() {
        return Typeface.createFromAsset(getResources().getAssets(), "iconfont.ttf");

    }

    /**
     * 获得上下文
     *
     * @return
     */
    public static Context getContext() {
        return App.getContext();
    }

    /**
     * 获得资源
     *
     * @return
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获得string类型的数据
     *
     * @param resId
     * @return
     */
    public static String getString(int resId) {
        return getContext().getResources().getString(resId);
    }

    /**
     * 获取string类型
     *
     * @param resId
     * @param formatArgs
     * @return
     */
    public static String getString(int resId, Object... formatArgs) {
        return getContext().getResources().getString(resId, formatArgs);
    }

    /**
     * 获得数组集合
     *
     * @param resId
     * @return
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获得颜色值
     *
     * @param resId
     * @return
     */
    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    /**
     * 获得handler
     *
     * @return
     */
    public static Handler getMainHandler() {
        return App.getHandler();
    }

    /**
     * 在主线程中执行任务
     *
     * @param task
     */
    public static void post(Runnable task) {
        getMainHandler().post(task);
    }

    /**
     * 延时执行任务
     *
     * @param task
     * @param delayMillis
     */
    public static void postDelayed(Runnable task, long delayMillis) {
        getMainHandler().postDelayed(task, delayMillis);
    }

    /**
     * 从消息队列中移除任务
     *
     * @param task
     */
    public static void removeCallbacks(Runnable task) {
        getMainHandler().removeCallbacks(task);
    }

    /**
     * 像素转dp
     *
     * @param px
     * @return
     */
    public static int px2dp(int px) {
        // px = dp * (dpi / 160)
        // dp = px * 160 / dpi

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;
        return (int) (px * 160f / dpi + 0.5f);
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    public static int dp2px(int dp) {
        // px = dp * (dpi / 160)
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dpi = metrics.densityDpi;

        return (int) (dp * (dpi / 160f) + 0.5f);
    }

    /**
     * 获得包名
     *
     * @return
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 显示暗屏
     *
     * @param activity
     */
    public static void showDarkScreen(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.4f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 隐藏暗屏
     *
     * @param activity
     */
    public static void hideDarkScreen(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1f;
        activity.getWindow().setAttributes(lp);
    }

    private static Toast toast;


    /**
     * 替换上一个toast 显示当前  Toast
     *
     * @param str
     */
    public static void showToast(String str) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.toast_item,null);
//
//        TextView tetView = (TextView) view.findViewById(R.id.toast_text);
//        textView.setText("  " + str + "  "); //setPadding不起作用，
//        textView.setTextColor(Color.WHITE);
//        textView.setGravity(Gravity.CENTER);
//        textView.setBackgroundColor(Color.parseColor("#96000000"));
//        view.setPadding(10,10,10,10);
//        toast.setView(view);
//        toast.setGravity(Gravity.BOTTOM, 0, 250);
//        toast.setDuration(Toast.LENGTH_SHORT);
        if (toast != null) {
            toast.setText(str);
        } else {
            toast = Toast.makeText(getContext(), str, Toast.LENGTH_SHORT);
        }
        toast.show();
    }


    /**
     * 显示red log
     */
    public static void showLog(String str) {
        if (Constant.LogDebug)
            Log.e("AM", str);
    }

    /**
     * 得到屏幕宽度的方法
     *
     * @param aty
     * @return
     */
    public static int getScreenW(Context aty) {
        DisplayMetrics dm;
        dm = aty.getResources().getDisplayMetrics();
        int w = dm.widthPixels;
        return w;
    }


    /**
     * 得到屏幕高度的方法
     *
     * @param aty
     * @return
     */
    public static int getScreenH(Context aty) {
        DisplayMetrics dm;
        dm = aty.getResources().getDisplayMetrics();
        int h = dm.heightPixels;
        return h;
    }


    /**
     * 获取一个没有数据时显示的view  ， textview
     *
     * @param act
     * @param str 提示文本
     * @return
     */
    public static View getEmptyView(Activity act, String str) {
        TextView tv = new TextView(act);
        tv.setText(str);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }


    /**
     * 启动一个缩放动画， 用在 主界面 底部tab点击选中时
     */
    public static void startScaleAnimation(Context context, final TextView tv_ttf, final TextView tv) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.click_scale);
        animation.setInterpolator(new AnticipateInterpolator());
        tv_ttf.startAnimation(animation);
        tv.startAnimation(animation);
    }

    /**
     * 发送event刷新设备管理数据
     *
     * @type EQUIPMENT_SOLD_NO  EQUIPMENT_SOLD_Yes
     */
    public static void refreshEquipmentManageData(int type) {
        EquipmentManageEvent event = new EquipmentManageEvent();
        event.type = type;
        EventBus.getDefault().post(event);
    }

}
