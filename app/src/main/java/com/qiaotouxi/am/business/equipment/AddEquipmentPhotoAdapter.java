package com.qiaotouxi.am.business.equipment;

import android.graphics.Bitmap;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.framework.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * <p>
 * 添加设备 照片的adapter
 */

public class AddEquipmentPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private AddEquipmentActivity activity;

    public AddEquipmentPhotoAdapter(AddEquipmentActivity activity, List<String> list) {

        super(R.layout.item_add_equipment_photo, list);
        this.activity = activity;
    }

    @Override
    protected void convert(final BaseViewHolder holder, String str) {


        Bitmap diskBitmap = BitmapUtils.getDiskBitmap(str);


        holder.setImageBitmap(R.id.img_tx, diskBitmap)
                .setOnClickListener(R.id.img_delete, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //删除按钮
                        activity.mImgPathList.remove(holder.getAdapterPosition());
                        if (activity.mImgPathList.size() == 0) {
                            activity.mAdapter.setNewData(new ArrayList<String>());
                        }
                        activity.mAdapter.notifyDataSetChanged();
                    }
                });


    }
}
