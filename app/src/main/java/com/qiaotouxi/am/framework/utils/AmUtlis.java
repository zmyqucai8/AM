package com.qiaotouxi.am.framework.utils;

import android.animation.ValueAnimator;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.customer.CostomerManageEvent;
import com.qiaotouxi.am.business.equipment.EquipmentManageCountEvent;
import com.qiaotouxi.am.business.equipment.EquipmentManageEvent;
import com.qiaotouxi.am.framework.base.Constant;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
     * 指定字符进行切割
     *
     * @param str_char
     * @param str
     * @return
     */
    public static List<String> splitStringByChar(String str_char, String str) {
        List<String> chars = new ArrayList<String>();
        if (str != null) {
            if (str.contains(str_char)) {
                String[] strarray = str.split("[" + str_char + "]");
                for (int i = 0; i < strarray.length; i++) {
                    chars.add(strarray[i]);
                }

            } else {
                chars.add(str);
            }
        }
        return chars;
    }

    /**
     * 获取拼音
     *
     * @param text
     * @return
     */
    public static String getPinYin(String text) {
        char[] chars = text.toCharArray();

        StringBuilder sb = new StringBuilder();

        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

        //取消音调
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        //大写
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);

        for (char ch : chars) {
            if (Character.isWhitespace(ch)) {
                //如果是空格
                continue;
            }

            if (ch > 128 || ch < -127) {
                try {
                    //数组是有多音字
                    String[] array = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                    sb.append(array[0]);

                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.getMessage();
                }
            } else {
                //#$%^
                return "#";
            }
        }

        return sb.toString();

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
     * 获取系统当前时间 2017年3月7日18:01:04
     */
    public static String getYMD() {
        return new SimpleDateFormat("yyyy年MM月dd日　HH:mm:ss").format(new Date(System.currentTimeMillis()));
    }


    /**
     * scrollView  3s滚动到底部 或底部
     *
     * @param buttomOrTop ture =顶部 false =底部
     * @param scrollView
     */

    public static void scrollViewToButtomTop(final ScrollView scrollView, boolean buttomOrTop) {
        ValueAnimator valueAnm = new ValueAnimator();
        if (buttomOrTop) {
            //从底部滚动到顶部
            valueAnm.setIntValues(scrollView.getHeight(), 0);
        } else {
            //从顶部滚动到底部
            valueAnm.setIntValues(0, scrollView.getHeight());
        }
        valueAnm.setDuration(2500);
        valueAnm.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                scrollView.scrollTo(0, value);
            }
        });
        valueAnm.start();
    }


    /**
     * 设置角标数量显示及隐藏
     * 大于0小于100 正常显示
     * 大于100 显示99
     * 不满足条件则隐藏
     *
     * @param number   消息数量
     * @param textView textview
     */
    public static void setBadgerCount(TextView textView, int number) {
        if (number > 0 && number < 100) { //大于0小于100 正常显示
            textView.setVisibility(View.VISIBLE);
            if (number < 10) {
                textView.setText(" " + String.valueOf(number) + " ");
            } else
                textView.setText(String.valueOf(number));
        } else if (number > 100) {//大于100 显示99
            textView.setVisibility(View.VISIBLE);
            textView.setText(String.valueOf(99));
        } else {   //不满足条件隐藏
            textView.setVisibility(View.GONE);
        }
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

    /**
     * 发送event刷新客户管理
     */
    public static void refreshCustomerManageData() {
        CostomerManageEvent event = new CostomerManageEvent();
        EventBus.getDefault().post(event);
    }

    /**
     * 发送event刷新设备管理 ，已出售和未出售总数角标 ， 在获取数据后
     *
     * @param count 数量
     * @type 类型  EQUIPMENT_SOLD_NO  EQUIPMENT_SOLD_Yes
     */
    public static void refreshEquipmentManageCount(int type, int count) {
        EquipmentManageCountEvent event = new EquipmentManageCountEvent();
        event.type = type;
        event.count = count;
        EventBus.getDefault().post(event);
    }


    //显示提示框的限制
    public static boolean isShowAlert = true;


    /**
     * 关闭页面的提示
     */
    public static void showCloseAlert(final Activity context) {
        if (isShowAlert) {
            isShowAlert = false;
            new AlertView.Builder().setContext(context)
                    .setStyle(AlertView.Style.Alert)
                    .setTitle("温馨提示")
                    .setMessage("请确认是否已经保存了当前信息\n继续退出请按确定")
                    .setCancelText("取消")
                    .setDestructive("确定")
                    .setOthers(null)
                    .setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick(Object o, int position) {
                            isShowAlert = true;
                            if (position != -1) {
                                context.finish();
                            }
                        }
                    })
                    .build()
                    .show();


        }
    }

    /**
     * 照片的提示
     *
     * @param context
     */
    public static void showPhotoAlert(final Activity context) {


    }


}
