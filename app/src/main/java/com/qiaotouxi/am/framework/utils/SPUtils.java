package com.qiaotouxi.am.framework.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Created by zmy.
 * @Date 2017/3/9 0009.
 * SharedPreferences 工具类
 */

public class SPUtils {

    private static SharedPreferences mSp;
    private static final String SP_NAME = "am";

    /**
     * 获取Preferences 对象
     *
     * @param context
     * @return
     */
    private static SharedPreferences getPreferences(Context context) {
        if (mSp == null) {
            mSp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mSp;
    }

    /**
     * 设置是否添加过测试数据
     */
    public static void setisAddData(Context context, boolean isAdd) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putBoolean("AddData", isAdd);
        editor.commit();
    }

    /**
     * 获取是否添加过测试数据
     */
    public static boolean getisAddData(Context context) {
        return getPreferences(context).getBoolean("AddData", true);
    }

}
