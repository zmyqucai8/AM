package com.qiaotouxi.am.business.main;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.utils.AmUtlis;
import com.qiaotouxi.am.framework.utils.FileUtils;

import java.io.File;
import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/15 0015.
 * 文件adapter
 */

public class MyFileAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    private MyFileActivity mActivity;

    /**
     * 构造方法
     *
     * @param activity
     * @param data
     */
    public MyFileAdapter(MyFileActivity activity, List<File> data) {
        super(R.layout.folder_item, data);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, final File file) {
        LinearLayout linearLayout = holder.getView(R.id.ll_content);
        ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
        lp.height = AmUtlis.getScreenW(mContext) / 3;
        linearLayout.setLayoutParams(lp);
        if (file.getName().endsWith(".jpg")) {
            //判断文件类型， 如果是图片， 那么显示图片 如果是txt，则显示txt
            Glide.with(mActivity).load(file.getAbsolutePath()).asBitmap().placeholder(R.drawable.img_tx).into((ImageView) holder.getView(R.id.img_item));
            //图片单击事件
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AmUtlis.openPicture(mActivity, file);
                }
            });
            //图片长按事件
            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AmUtlis.shareMsg(mActivity, "发送到?", "发送图片", "图片", file.getAbsolutePath());
                    return false;
                }
            });

        } else if (file.getName().endsWith(".txt")) {
            holder.setBackgroundRes(R.id.img_item, R.drawable.img_txt);
            //文本单击事件 查看文本内容
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AmUtlis.showTxtAlert(mActivity, file.getName(), FileUtils.readTxt(file));
                }
            });
            //txt长按事件 发送txt文本内容
            holder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AmUtlis.shareMsg(mActivity, "发送到?", "发送文本", FileUtils.readTxt(file), "");
                    return false;
                }
            });
        }
        //设置文件夹名
        holder.setText(R.id.tv_text, file.getName());
    }
}
