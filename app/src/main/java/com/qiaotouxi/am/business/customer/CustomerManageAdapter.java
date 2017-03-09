package com.qiaotouxi.am.business.customer;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.CustomerDao;
import com.qiaotouxi.am.framework.base.Constant;

import java.util.ArrayList;
import java.util.List;

import ca.barrenechea.widget.recyclerview.decoration.StickyHeaderAdapter;


/**
 * @Created by zmy.
 * @Date 2017/3/6 0007.
 * 客户管理页面的adapter 结合字母索引
 */

public class CustomerManageAdapter extends RecyclerView.Adapter<CustomerManageAdapter.ViewHolder> implements
        StickyHeaderAdapter<CustomerManageAdapter.HeaderHolder> {

    private LayoutInflater mInflater;
    private List<CustomerDao> mListData;
    private char lastChar = '\u0000';
    private int DisplayIndex = 0;
    private Activity activity;
    /**
     * 点击item是否是选择item
     */
    private boolean isSelect;

    public CustomerManageAdapter(Activity context, List<CustomerDao> mListData, boolean isSelect) {
        mInflater = LayoutInflater.from(context);
        this.mListData = mListData;
        this.activity = context;
        this.isSelect = isSelect;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View view = mInflater.inflate(R.layout.item_contacts_item, viewGroup, false);

        return new ViewHolder(view);
    }

    //条目文本填充 及点击事件
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        String name = mListData.get(i).getName();
        String phone = mListData.get(i).getPhone();
        if (!TextUtils.isEmpty(highlightText) && name.contains(highlightText)) {
            String html = "<font color='red'>" + highlightText + "</font>";
            html = name.replaceFirst(highlightText, html);
            html = "<font>" + html + "</font>";
            viewHolder.item.setText(Html.fromHtml(html));
        } else if (!TextUtils.isEmpty(highlightText) && phone.contains(highlightText)) {
            viewHolder.item.setText(Html.fromHtml("<font color='blue'>" + name + "</font>"));
        } else {
            viewHolder.item.setText(name);
        }

        viewHolder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isSelect) {
                    Intent intent = new Intent();
                    intent.putExtra(Constant.CUSTMER_PHONE, mListData.get(i).getPhone());
                    activity.setResult(Activity.RESULT_OK, intent);
                    activity.finish();
                } else {
                    Intent intent = new Intent(activity, CustomerDetailsActivity.class);
                    intent.putExtra(Constant.CUSTMER_PHONE, mListData.get(i).getPhone());
                    activity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public long getHeaderId(int position) {

        //这里面的是如果当前position与之前position重复（内部判断）  则不显示悬浮标题栏  如果不一样则显示标题栏
        char ch = mListData.get(position).getPinyin().charAt(0);
        if (lastChar == '\u0000') {
            lastChar = ch;
            return DisplayIndex;
        } else {
            if (lastChar == ch) {
                return DisplayIndex;
            } else {
                lastChar = ch;
                DisplayIndex++;
                return DisplayIndex;
            }

        }

    }

    public HeaderHolder onCreateHeaderViewHolder(ViewGroup parent) {
        final View view = mInflater.inflate(R.layout.item_contacts_head, parent, false);
        return new HeaderHolder(view);
    }

    //悬浮标题栏填充文本
    public void onBindHeaderViewHolder(HeaderHolder viewholder, int position) {
        viewholder.header.setText(mListData.get(position).getPinyin().charAt(0) + "");
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item;

        public ViewHolder(View itemView) {
            super(itemView);

            item = (TextView) itemView;
        }
    }

    static class HeaderHolder extends RecyclerView.ViewHolder {
        public TextView header;

        public HeaderHolder(View itemView) {
            super(itemView);
            header = (TextView) itemView;
        }
    }

    /**
     * 获得指定首字母的位置
     *
     * @param ch
     * @return
     */
    public int getPositionForSection(char ch) {

        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = mListData.get(i).getPinyin().charAt(0);
            if (firstChar == ch) {
                return i;
            }
        }
        return -1;

    }

    // 被搜索的文字
    private String highlightText = "";

    /**
     * 设置新数据
     *
     * @param data
     * @param str  如果不为null，说明是搜索后天就新数据，被搜索字段要高亮显示
     */
    public void setNewData(List<CustomerDao> data, String str) {
        this.mListData = (List) (data == null ? new ArrayList() : data);
        this.highlightText = str;
        this.notifyDataSetChanged();
    }
}
