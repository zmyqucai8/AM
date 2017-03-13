package com.qiaotouxi.am.business.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.customer.AddCustomerActivity;
import com.qiaotouxi.am.business.customer.CustomerManageFragment;
import com.qiaotouxi.am.business.dao.DaoUtils;
import com.qiaotouxi.am.business.equipment.AddEquipmentActivity;
import com.qiaotouxi.am.business.equipment.EquipmentManageFragment;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.SPUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zmy on 2017/2/28.
 * app主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_sbgl)
    LinearLayout llSbgl;
    @BindView(R.id.ll_khgl)
    LinearLayout llKhgl;
    @BindView(R.id.ll_btn)
    LinearLayout llBtn;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_sbgl_ttf)
    TextView tv_sbgl_ttf;
    @BindView(R.id.tv_photo)
    TextView tv_photo;
    @BindView(R.id.tv_khgl_ttf)
    TextView tv_khgl_ttf;
    @BindView(R.id.tv_khgl)
    TextView tv_khgl;
    @BindView(R.id.tv_sbgl)
    TextView tv_sbgl;
    @BindView(R.id.tv_button)
    TextView tvButton;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindColor(R.color.text_gray)
    int text_gray;
    @BindColor(R.color.colorPrimaryDark)
    int text_blue;

    private EquipmentManageFragment mEquipmentManage;
    private CustomerManageFragment mCustomerManage;
    private int mType = -1;//当前显示的fragment类型

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setDefaultData();//TODO：设置默认数据，只会设置一次，发布请删除
        if (savedInstanceState != null) {
            mEquipmentManage = (EquipmentManageFragment) getFragmentManager().findFragmentByTag("mEquipmentManage");
            mCustomerManage = (CustomerManageFragment) getFragmentManager().findFragmentByTag("mCustomerManage");
        }
        initView();
        setFragment(Constant.TYPE_EQUIPMENT);

    }


    /**
     * TODO：设置默认数据  只设置一次。 正式版请注释
     */
    private void setDefaultData() {

        if (SPUtils.getisAddData(MainActivity.this)) {
            DaoUtils.testAddCustomer(MainActivity.this);
            DaoUtils.testAddEquipmentDao(MainActivity.this);
            SPUtils.setisAddData(MainActivity.this, false);
        }
    }

    /**
     * 初始化view
     */
    private void initView() {
        ButterKnife.bind(this);
        tv_khgl_ttf.setTypeface(AmUtlis.getTTF());
        tv_photo.setTypeface(AmUtlis.getTTF());
        tvButton.setTypeface(AmUtlis.getTTF());
        tv_sbgl_ttf.setTypeface(AmUtlis.getTTF());
        llSbgl.setOnClickListener(this);
        tvButton.setOnClickListener(this);
        llKhgl.setOnClickListener(this);
        tv_photo.setOnClickListener(this);
    }


    /**
     * @param type 0=设备管理 1=客户管理
     */
    private void setFragment(int type) {
        if (mType == type) {
            return;
        }
        mType = type;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (type == Constant.TYPE_EQUIPMENT) {
            if (mEquipmentManage == null) {
                mEquipmentManage = new EquipmentManageFragment();
                transaction.add(R.id.fragment, mEquipmentManage, "mEquipmentManage");
            }
            if (null != mCustomerManage) {
                transaction.hide(mCustomerManage);
            }
            transaction.show(mEquipmentManage);
        } else if (type == Constant.TYPE_CUSTOMER) {
            if (mCustomerManage == null) {
                mCustomerManage = new CustomerManageFragment();
                transaction.add(R.id.fragment, mCustomerManage, "mCustomerManage");
            }
            if (null != mEquipmentManage) {
                transaction.hide(mEquipmentManage);
            }
            transaction.show(mCustomerManage);
        }
        transaction.commit();
        setTabSelectTitle(type);
    }

    /**
     * 设置底部tab选中 以及title文字 0=设备管理 1=客户管理
     *
     * @param type
     */
    private void setTabSelectTitle(int type) {

        switch (type) {
            case Constant.TYPE_EQUIPMENT:

                tvTitle.setText("设备管理");
                tv_sbgl_ttf.setTextColor(text_blue);
                tv_sbgl.setTextColor(text_blue);
                tv_khgl_ttf.setTextColor(text_gray);
                tv_khgl.setTextColor(text_gray);
                AmUtlis.startScaleAnimation(MainActivity.this, tv_sbgl_ttf, tv_sbgl);
                break;
            case Constant.TYPE_CUSTOMER:
                tvTitle.setText("客户管理");
                tv_sbgl_ttf.setTextColor(text_gray);
                tv_sbgl.setTextColor(text_gray);
                tv_khgl_ttf.setTextColor(text_blue);
                tv_khgl.setTextColor(text_blue);
                AmUtlis.startScaleAnimation(MainActivity.this, tv_khgl_ttf, tv_khgl);
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_sbgl:
                setFragment(Constant.TYPE_EQUIPMENT);
                break;
            case R.id.ll_khgl:
                setFragment(Constant.TYPE_CUSTOMER);
                break;
            case R.id.tv_button:
                startAddActivity(mType);
                break;
            case R.id.tv_photo:
                startActivity(new Intent(MainActivity.this, MyAlbumActivity.class));
                break;
        }
    }

    /**
     * 跳转到添加页面 根据type判断类型
     *
     * @param mType
     */
    private void startAddActivity(int mType) {
        Intent intent = null;
        if (mType == Constant.TYPE_EQUIPMENT) {
            intent = new Intent(MainActivity.this, AddEquipmentActivity.class);
        } else {
            intent = new Intent(MainActivity.this, AddCustomerActivity.class);
        }
        startActivity(intent);
    }


    /**
     * 菜单、返回键响应
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            exitAllActivitys();
            // android.os.Process.killProcess(android.os.Process.myPid()); // 杀死该进程
        }
    }

}
