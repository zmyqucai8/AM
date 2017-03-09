package com.qiaotouxi.am.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.BaseActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/9 0009.
 * 启动页
 */

public class SplashActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        ButterKnife.bind(this);
        setSpalashImg();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1500);
    }

    /**
     * 设置图片显示
     */
    private void setSpalashImg() {
        int flag = new Random().nextInt(1000) % 3;
        switch (flag) {
            case 0:
                img.setImageResource(R.drawable.img_splash1);
                break;
            case 1:
                img.setImageResource(R.drawable.img_splash2);
                break;
            case 2:
                img.setImageResource(R.drawable.img_splash3);
                break;
            default:
                img.setImageResource(R.drawable.img_splash4);
                break;
        }

    }
}
