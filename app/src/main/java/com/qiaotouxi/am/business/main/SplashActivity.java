package com.qiaotouxi.am.business.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.BaseActivity;

import java.util.Random;

import butterknife.BindColor;
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
    @BindView(R.id.ll_root)
    LinearLayout ll_root;
    @BindColor(R.color.text_gray)
    int gray;
    @BindColor(R.color.bg_color)
    int blue;
    @BindColor(R.color.gc_red_tag)
    int red;
    @BindColor(R.color.mblue)
    int mblue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        int flag2 = new Random().nextInt(1000) % 3;
        switch (flag2) {
            case 0:
                ll_root.setBackgroundColor(mblue);
                break;
            case 1:
                ll_root.setBackgroundColor(blue);
                break;
            case 2:
                ll_root.setBackgroundColor(red);
                break;
            default:
                ll_root.setBackgroundColor(gray);
                break;
        }
        switch (flag) {
            case 0:
                img.setImageResource(R.drawable.img_splash1);
                ll_root.setBackgroundColor(mblue);
                break;
            case 1:
                img.setImageResource(R.drawable.img_splash2);
                ll_root.setBackgroundColor(blue);
                break;
            case 2:
                img.setImageResource(R.drawable.img_splash3);
                ll_root.setBackgroundColor(red);
                break;
            default:
                img.setImageResource(R.drawable.img_splash4);
                ll_root.setBackgroundColor(gray);
                break;
        }


    }
}
