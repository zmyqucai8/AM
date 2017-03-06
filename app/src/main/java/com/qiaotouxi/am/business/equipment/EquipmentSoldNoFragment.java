package com.qiaotouxi.am.business.equipment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.DaoUtils;
import com.qiaotouxi.am.framework.base.BaseFragment;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/1 0001.
 * 设备未出售 列表
 */

public class EquipmentSoldNoFragment extends BaseFragment {
    EquipmentSoldAdapter mAdapter;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.equipment_sold_no, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void initData() {
        mAdapter = new EquipmentSoldAdapter(getActivity(), DaoUtils.getEquipmentSoldNoData(getActivity()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.isFirstOnly(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setEmptyView(AmUtlis.getEmptyView(getActivity(), "你还没有未出售的设备\n点击右上角按钮进行添加吧"));
        mRecyclerView.setAdapter(mAdapter);

    }


    /**
     * 接受刷新event
     *
     * @param event
     */
    public void onEventMainThread(EquipmentManageEvent event) {
        if (event.type == Constant.EQUIPMENT_SOLD_NO) {
            AmUtlis.showLog("evnet 刷新未出售列表");
            refreshData();
        }
    }


    /**
     * 刷新数据的方法
     */
    public void refreshData() {
        mAdapter.setNewData(DaoUtils.getEquipmentSoldNoData(getActivity()));
    }


}
