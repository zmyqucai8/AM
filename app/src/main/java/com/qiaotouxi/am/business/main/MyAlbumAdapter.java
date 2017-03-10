package com.qiaotouxi.am.business.main;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.utils.AmUtlis;

import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/13 0013.
 * 相册 的adapter
 */

public class MyAlbumAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private Activity activity;

    public MyAlbumAdapter(Activity activity, List<String> data) {
        super(R.layout.album_item, data);
        this.activity = activity;
    }

    @Override
    protected void convert(BaseViewHolder holder, final String s) {
        ImageView img = holder.getView(R.id.img_item);
        ViewGroup.LayoutParams lp = img.getLayoutParams();
        lp.height = AmUtlis.getScreenW(mContext) / 3;
        img.setLayoutParams(lp);
        Glide.with(activity).load(s).asBitmap().placeholder(R.drawable.img_tx).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmUtlis.shareMsg(activity, "发送到?", "发送图片", "内容", s);
            }
        });
    }
}
