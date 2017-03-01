package com.qiaotouxi.am.framework.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yyyyyyy on 2017/2/28.
 */
public class BaseActivity extends FragmentActivity {
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
