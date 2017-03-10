package com.qiaotouxi.am.business.customer;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.CustomerDao;
import com.qiaotouxi.am.business.dao.DaoUtils;
import com.qiaotouxi.am.framework.base.BaseFragment;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.view.QuickIndexBar;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ca.barrenechea.widget.recyclerview.decoration.DividerDecoration;
import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderDecoration;
import de.greenrobot.event.EventBus;


/**
 * Created by zmy on 2017/2/28.
 * 客户管理fragment
 */
public class CustomerManageFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.tv_search_ttf)
    TextView tvSearchTtf;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    @BindView(R.id.tv_nodata)
    TextView tv_nodata;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.qib)
    QuickIndexBar QIBar;
    private LinearLayoutManager manager;
    CustomerManageAdapter adapter;
    List<CustomerDao> allCustomerData;


    /**
     * 构造方法， 需要一个type 来表示是在设备管理页面显示， 还是在出售设备选择客户时显示
     *
     * @param isSelect
     */
    private boolean isSelect;

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        tvSearchTtf.setTypeface(AmUtlis.getTTF());
        tvSearch.setOnClickListener(this);
        return view;
    }

    @Override
    protected void initData() {
        //设置recycleview
        DividerDecoration divider = new DividerDecoration.Builder(getActivity())
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.text_gray)
                .build();
        manager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(divider);
        //获取数据 设置adapter
        allCustomerData = DaoUtils.getAllCustomerData(getActivity());
        Collections.sort(allCustomerData);
        adapter = new CustomerManageAdapter(getActivity(), allCustomerData, isSelect);
        //设置悬浮索引
        StickyHeaderDecoration decor = new StickyHeaderDecoration(adapter);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(decor, 1);
        setUiShow();
        //侧拉索引改变监听
        QIBar.setOnLetterChangeListener(new QuickIndexBar.OnLetterChangeListener() {
            @Override
            public void onLetterChange(String letter) {
                for (int i = 0; i < allCustomerData.size(); i++) {
                    if (letter.equals(allCustomerData.get(i).getPinyin().charAt(0) + "")) {
                        int position = adapter.getPositionForSection(allCustomerData.get(i).getPinyin().charAt(0));
                        if (position != -1) {
                            //滑动到指定位置
                            manager.scrollToPositionWithOffset(position, 0);
                        }
                        break;
                    }
                }
            }

            @Override
            public void onReset() {

            }
        });

        //文本监听
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //输入完成后
                if (s.toString().trim().length() > 0) {
                    search();
                } else {
                    refreshData();
                }


            }
        };
        etInput.addTextChangedListener(watcher);

    }

    /**
     * 刷新客户管理数据event
     *
     * @param event
     */
    public void onEventMainThread(CostomerManageEvent event) {
        AmUtlis.showLog("evnet 刷新客户管理数据");
        refreshData();
    }

    /**
     * 刷新数据
     */
    private void refreshData() {
        allCustomerData = DaoUtils.getAllCustomerData(getActivity());
        Collections.sort(allCustomerData);
        adapter.setNewData(allCustomerData, "");
        setUiShow();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search: //查找
//                search();  优化了下代码，改成输入自动查找， 先注释掉此按钮功能，防止重复点击查询数据
                break;
        }

    }

    /**
     * 查找客户
     */
    private void search() {
        String str = etInput.getText().toString().trim();
        List<CustomerDao> customerList;
        if (!TextUtils.isEmpty(str)) {
            customerList = DaoUtils.getCustomerByNamePhone(getActivity(), str);
        } else {
//            AmUtlis.showToast("请输入姓名或电话");
            return;
        }
        //如果查询到了数据，那么设置数据，刷新ui
        if (null != customerList && customerList.size() > 0) {
            allCustomerData.clear();
            allCustomerData.addAll(customerList);
            adapter.setNewData(allCustomerData, str);
            setUiShow();
        } else {
//            AmUtlis.showToast("查询不到客户");
        }
    }

    /***
     * 根据数据设置ui显示
     */
    private void setUiShow() {
        if (allCustomerData.size() < 1) {
            mRecyclerView.setVisibility(View.GONE);
            QIBar.setVisibility(View.GONE);
            tv_nodata.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            QIBar.setVisibility(View.VISIBLE);
            tv_nodata.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
