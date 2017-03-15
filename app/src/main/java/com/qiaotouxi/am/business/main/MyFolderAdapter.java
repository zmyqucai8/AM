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

import java.io.File;
import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/15 0015.
 * 文件夹 adapter
 */

public class MyFolderAdapter extends BaseQuickAdapter<File, BaseViewHolder> {

    private MyFileActivity mActivity;

    /**
     * 构造方法
     *
     * @param activity
     * @param data
     */
    public MyFolderAdapter(MyFileActivity activity, List<File> data) {
        super(R.layout.folder_item, data);
        this.mActivity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, final File file) {
        LinearLayout linearLayout = holder.getView(R.id.ll_content);
        ViewGroup.LayoutParams lp = linearLayout.getLayoutParams();
        lp.height = AmUtlis.getScreenW(mContext) / 3;
        linearLayout.setLayoutParams(lp);

        //第一张图作为当前文件夹的封面
        final File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].getName().endsWith(".jpg")) {
                Glide.with(mActivity).load(files[i].getAbsolutePath()).asBitmap().placeholder(R.drawable.img_tx).into((ImageView) holder.getView(R.id.img_item));
                break;
            }
        }
        //设置文件夹名
        holder.setText(R.id.tv_text, file.getName());
        //设置点击事件
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.setViewShowOpenFolder(files);
                mActivity.setPathText(file.getAbsolutePath());
            }
        });

    }
}
