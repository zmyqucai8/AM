package com.qiaotouxi.am.business.equipment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.base.BaseFragment;
import com.qiaotouxi.am.framework.base.Constant;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zmy on 2017/2/28.
 * 设备管理 fragment
 */
public class EquipmentManageFragment extends BaseFragment implements View.OnClickListener {


    private EquipmentSoldNoFragment mEquipmentNo;
    private EquipmentSoldYesFragment mEquipmentYes;
    private int mType;//当前显示的fragment类型
    @BindView(R.id.tv_wcs)
    TextView tvWcs;
    @BindView(R.id.tv_ycs)
    TextView tvYcs;
    @BindView(R.id.ll_tab)
    LinearLayout llTab;
    @BindColor(R.color.color1)
    int blueColor;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_equipment, container, false);
        ButterKnife.bind(this, rootView);
        if (savedInstanceState != null) {
            mEquipmentNo = (EquipmentSoldNoFragment) getFragmentManager().findFragmentByTag("mEquipmentNo");
            mEquipmentYes = (EquipmentSoldYesFragment) getFragmentManager().findFragmentByTag("mEquipmentYes");
        }
        return rootView;
    }

    @Override
    protected void initData() {
        tvYcs.setOnClickListener(this);
        tvWcs.setOnClickListener(this);
        setFragment(Constant.EQUIPMENT_SOLD_NO);
    }

    /**
     * @param type 0=设备管理 1=客户管理
     */
    private void setFragment(int type) {
        mType = type;
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        if (type == Constant.EQUIPMENT_SOLD_NO) {
            if (mEquipmentNo == null) {
                mEquipmentNo = new EquipmentSoldNoFragment();
                transaction.add(R.id.fragments, mEquipmentNo, "mEquipmentNo");
            }
            if (null != mEquipmentYes) {
                transaction.hide(mEquipmentYes);
            }
            transaction.show(mEquipmentNo);
        } else if (type == Constant.EQUIPMENT_SOLD_YES) {
            if (mEquipmentYes == null) {
                mEquipmentYes = new EquipmentSoldYesFragment();
                transaction.add(R.id.fragments, mEquipmentYes, "mEquipmentYes");
            }
            if (null != mEquipmentNo) {
                transaction.hide(mEquipmentNo);
            }
            transaction.show(mEquipmentYes);
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
            case Constant.EQUIPMENT_SOLD_NO:
                tvWcs.setBackground(getResources().getDrawable(R.drawable.shape_round_left_blue_bg));
                tvWcs.setTextColor(Color.WHITE);
                tvYcs.setBackground(getResources().getDrawable(R.drawable.shape_round_right_white_bg));
                tvYcs.setTextColor(blueColor);
                break;
            case Constant.EQUIPMENT_SOLD_YES:
                tvWcs.setBackground(getResources().getDrawable(R.drawable.shape_round_left_white_bg));
                tvWcs.setTextColor(blueColor);
                tvYcs.setBackground(getResources().getDrawable(R.drawable.shape_round_right_blue_bg));
                tvYcs.setTextColor(Color.WHITE);
                break;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_ycs:
                setFragment(Constant.EQUIPMENT_SOLD_YES);
                break;
            case R.id.tv_wcs:
                setFragment(Constant.EQUIPMENT_SOLD_NO);
                break;
        }
    }
}
