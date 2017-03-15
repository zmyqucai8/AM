package com.qiaotouxi.am;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.qiaotouxi.am.business.dao.DaoMaster;
import com.qiaotouxi.am.business.dao.DaoSession;


/**
 * @Created by zmy.
 * @Date 2017/3/1 0006.
 * 程序入口类
 */
public class App extends Application {

    private static Context mContext;
    private static Handler mHandler;
    private static Thread mMainThread;
    private static long mMainThreadId;
    private static Looper mMainThreadLooper;

    @Override
    public void onCreate() {
        super.onCreate();

        // 程序的入口
        mContext = this;

        // handler,用来子线程和主线程通讯
        mHandler = new Handler();

        // 主线程
        mMainThread = Thread.currentThread();
        // id
        // mMainThreadId = mMainThread.getId();
        mMainThreadId = android.os.Process.myTid();

        // looper
        mMainThreadLooper = getMainLooper();


    }

    /**
     * 数据库管理 及操作类
     */
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "am-db", null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }


    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static Looper getMainThreadLooper() {
        return mMainThreadLooper;
    }
}
