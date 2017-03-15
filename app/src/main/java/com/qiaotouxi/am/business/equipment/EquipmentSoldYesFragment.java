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
import com.qiaotouxi.am.business.dao.EquipmentDao;
import com.qiaotouxi.am.framework.base.BaseFragment;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.AmUtlis;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * @Created by zmy.
 * @Date 2017/3/1 0001.
 * 设备已出售 列表
 */
public class EquipmentSoldYesFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private EquipmentSoldAdapter mAdapter;//设备列表adapter

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.equipment_sold_fragment, container, false);
        ButterKnife.bind(this, rootView);
        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    protected void initData() {
        mAdapter = new EquipmentSoldAdapter(getActivity(), getEquipmentSoldYesData());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter.isFirstOnly(true);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        mAdapter.setEmptyView(AmUtlis.getEmptyView(getActivity(), "你还没有已出售的设备\n点击右上角按钮进行添加吧"));
        mRecyclerView.setAdapter(mAdapter);

    }


    /**
     * 接受刷新event
     *
     * @param event
     */
    public void onEventMainThread(EquipmentManageEvent event) {
        if (event.type == Constant.EQUIPMENT_SOLD_YES || event.type == Constant.EQUIPMENT_ALL) {
            AmUtlis.showLog("evnet 刷新已出售列表");
            refreshData();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 刷新数据的方法
     */
    public void refreshData() {
        mAdapter.setNewData(getEquipmentSoldYesData());
        mRecyclerView.scrollToPosition(mAdapter.getItemCount());
    }

    /**
     * 获取未出售数据，并且刷新数量角标
     *
     * @return
     */
    private List<EquipmentDao> getEquipmentSoldYesData() {
        List<EquipmentDao> listData = DaoUtils.getEquipmentSoldYesData(getActivity());

        for (EquipmentDao list : listData) {
            AmUtlis.showLog("已出售数据=" + list.toString() + "\n");
        }
        AmUtlis.refreshEquipmentManageCount(Constant.EQUIPMENT_SOLD_YES, listData.size());
        return listData;
    }


}
