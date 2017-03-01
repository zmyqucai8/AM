package com.qiaotouxi.am.business.customer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qiaotouxi.am.R;

/**
 * Created by zmy on 2017/2/28.
 * 客户管理fragment
 */
public class CustomerManageFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }
}
