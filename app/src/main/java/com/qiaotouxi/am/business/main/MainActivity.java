package com.qiaotouxi.am.business.main;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.utils.AmUtlis;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initView();
        if (savedInstanceState != null) {
            mEquipmentManage = (EquipmentManageFragment) getFragmentManager().findFragmentByTag("mEquipmentManage");
            mCustomerManage = (CustomerManageFragment) getFragmentManager().findFragmentByTag("mCustomerManage");
        }
        setFragment(0);


    }

    /**
     * 初始化view
     */
    private void initView() {


        tv_khgl_ttf.setTypeface(AmUtlis.getTTF());
        tvButton.setTypeface(AmUtlis.getTTF());
        tv_sbgl_ttf.setTypeface(AmUtlis.getTTF());

        llSbgl.setOnClickListener(this);
        llKhgl.setOnClickListener(this);

    }


    /**
     * @param type 0=设备管理 1=客户管理
     */
    private void setFragment(int type) {
        FragmentManager fm = getFragmentManager();
        // 开启Fragment事务
        FragmentTransaction transaction = fm.beginTransaction();

        if (type == 0) {
            if (mEquipmentManage == null) {
                mEquipmentManage = new EquipmentManageFragment();
                transaction.add(R.id.fragment, mEquipmentManage, "mEquipmentManage");

            }
            if (null != mCustomerManage) {
                transaction.hide(mCustomerManage);
            }
            transaction.show(mEquipmentManage);

        } else if (type == 1) {
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
            case 0:
                tvTitle.setText("设备管理");
                tv_sbgl_ttf.setTextColor(text_blue);
                tv_sbgl.setTextColor(text_blue);
                tv_khgl_ttf.setTextColor(text_gray);
                tv_khgl.setTextColor(text_gray);
                break;
            case 1:


                tvTitle.setText("客户管理");
                tv_sbgl_ttf.setTextColor(text_gray);
                tv_sbgl.setTextColor(text_gray);
                tv_khgl_ttf.setTextColor(text_blue);
                tv_khgl.setTextColor(text_blue);
                break;
        }
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.ll_sbgl:
                setFragment(0);
                break;
            case R.id.ll_khgl:
                setFragment(1);
                break;
        }
    }
}
