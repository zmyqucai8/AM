package com.qiaotouxi.am.business.equipment;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.EquipmentDao;
import com.qiaotouxi.am.framework.utils.BitmapUtils;

import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * <p>
 * 设备管理列表适配器，包含 未出售与 已出售
 */


public class EquipmentSoldAdapter extends BaseQuickAdapter<EquipmentDao, BaseViewHolder> {


    Context mContext;

    public EquipmentSoldAdapter(Context context, List<EquipmentDao> data) {
        super(R.layout.item_equipment, data);
        this.mContext = context;

    }

    @Override
    protected void convert(BaseViewHolder holder, EquipmentDao bean) {

        //基本信息
        holder
                .setText(R.id.tv_name, "名　　　称： " + bean.getName())
                .setText(R.id.tv_pp, "品　　　牌： " + bean.getBrand())
                .setText(R.id.tv_cj, "厂　　　家： " + bean.getManufacturer())
                .setText(R.id.tv_ccbh, "出厂　编号： " + bean.getFactory_id())
                .setText(R.id.tv_fdjbh, "发动机编号： " + bean.getEngine_id());

        //出售状态
        if (bean.getSell()) {
            holder.setBackgroundRes(R.id.tv_status, R.drawable.shape_oval_red_bg)
                    .setText(R.id.tv_status, bean.getPayment() ? "已付款" : "未付款");
        } else {
            holder.setBackgroundRes(R.id.tv_status, R.drawable.shape_oval_gray_bg)
                    .setText(R.id.tv_status, "未出售");
        }

        //设置照片
        String photo_list = bean.getPhoto_list();
        if (TextUtils.isEmpty(photo_list)) {
            return;
        }
        String imgPath;
        try {
            //如果出现异常， 说明切割字符串出现问题，表示只有一张图片
            imgPath = photo_list.substring(0, photo_list.indexOf(","));
        } catch (Exception e) {
            imgPath = photo_list;
        }

        Bitmap bitmap = BitmapUtils.getDiskBitmap(imgPath);
        holder.setImageBitmap(R.id.img_tx, bitmap);

    }
}
