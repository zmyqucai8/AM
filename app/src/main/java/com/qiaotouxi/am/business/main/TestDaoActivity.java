package com.qiaotouxi.am.business.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.qiaotouxi.am.App;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.CustomerDao;
import com.qiaotouxi.am.business.dao.CustomerDaoDao;
import com.qiaotouxi.am.framework.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Created by zmy.
 * @Date 2017/3/3 0003.
 * <p>
 * 测试 数据  页面
 */

public class TestDaoActivity extends BaseActivity {


    @BindView(R.id.et_input)
    EditText etInput;
    @BindView(R.id.btn_query_all_kh)
    Button btnQueryAllKh;
    @BindView(R.id.btn_delete_all)
    Button btn_delete_all;
    @BindView(R.id.btn_query_key_kh)
    Button btnQueryKeyKh;
    @BindView(R.id.btn_query_name_kh)
    Button btnQueryNameKh;
    @BindView(R.id.tv_text)
    TextView tvText;

    /**
     * dao对象
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.test_dao_activity);
        ButterKnife.bind(this);


        initView();
    }

    private void initView() {
        btnQueryAllKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询所有客户
                CustomerDaoDao customerDaoDao = App.getDaoSession(TestDaoActivity.this).getCustomerDaoDao();

                List<CustomerDao> list = customerDaoDao.queryBuilder().list();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < list.size(); i++) {

                    builder.append("第" + i + "条数据 = " + list.get(i).toString() + "\n");
                }
                if (TextUtils.isEmpty(builder.toString())) {
                    tvText.setText("没有客户数据");
                } else {
                    tvText.setText(builder.toString());
                }
            }
        });
        btnQueryKeyKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过身份证查询客户
                CustomerDaoDao customerDaoDao = App.getDaoSession(TestDaoActivity.this).getCustomerDaoDao();

                String cardId = etInput.getText().toString();

                if (TextUtils.isEmpty(cardId)) {
                    tvText.setText("请输入身份证号码");
                    return;
                }
                CustomerDao unique = customerDaoDao.queryRawCreate("where CARD_ID=? order by CARD_ID", cardId).unique();
                if (unique != null && unique.getCardId().equals(cardId)) {
                    StringBuilder builder = new StringBuilder();
                    tvText.setText(builder.append(unique.toString().toString()));
                } else {
                    tvText.setText("身份证 " + cardId + " 查询不到此客户");
                }
            }
        });
        btnQueryNameKh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过姓名查询客户
                CustomerDaoDao customerDaoDao = App.getDaoSession(TestDaoActivity.this).getCustomerDaoDao();

                String name = etInput.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    tvText.setText("请输入姓名");
                    return;
                }
                CustomerDao unique = customerDaoDao.queryRawCreate("where NAME=? order by NAME", name).unique();
                if (unique != null && unique.getName().equals(name)) {
                    StringBuilder builder = new StringBuilder();
                    tvText.setText(builder.append(unique.toString().toString()));
                } else {
                    tvText.setText("客户姓名 " + name + " 查询不到此客户");
                }
            }

        });

        btn_delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    CustomerDaoDao customerDaoDao = App.getDaoSession(TestDaoActivity.this).getCustomerDaoDao();
                    customerDaoDao.deleteAll();
                    tvText.setText("删除成功");
                } catch (Exception e) {
                    tvText.setText("删除失败");
                }
            }
        });
    }
}
