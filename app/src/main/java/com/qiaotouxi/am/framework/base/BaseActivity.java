package com.qiaotouxi.am.framework.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * @Created by zmy.
 * @Date 2017/3/2 0002.
 * 基类activity
 */
public class BaseActivity extends FragmentActivity {
    /**
     * activity集合
     */
    public static List<BaseActivity> activities = new ArrayList<BaseActivity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);

    }

    /**
     * 关闭所有的Activity
     */
    public void exitAllActivitys() {
        for (BaseActivity act : activities) {
            if (act != null) {
                act.finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        activities.remove(this);
        super.onDestroy();
    }
}
