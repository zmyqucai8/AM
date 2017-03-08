package com.qiaotouxi.am.business.customer;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.BaseActivity;
import com.qiaotouxi.am.framework.utils.AmUtlis;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/7 0007.
 * 客户选择activity
 */

public class CustomerSelectActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.fragment_select)
    FrameLayout fragment;
    @BindView(R.id.tv_button)
    TextView tv_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_select_activity);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化view
     */
    private void initView() {
        tv_button.setTypeface(AmUtlis.getTTF());

        tv_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerSelectActivity.this, AddCustomerActivity.class);
                startActivity(intent);
            }
        });
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        CustomerManageFragment mCustomerManageFragment = new CustomerManageFragment();
        mCustomerManageFragment.setIsSelect(true);
        transaction.add(R.id.fragment_select, mCustomerManageFragment, "mCustomerManageFragment");
        transaction.show(mCustomerManageFragment);
        transaction.commit();
    }
}
