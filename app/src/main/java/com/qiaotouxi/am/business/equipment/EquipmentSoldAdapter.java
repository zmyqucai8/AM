package com.qiaotouxi.am.business.equipment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qiaotouxi.am.R;
import com.qiaotouxi.am.business.dao.EquipmentDao;
import com.qiaotouxi.am.framework.base.Constant;
import com.qiaotouxi.am.framework.utils.BitmapUtils;

import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 设备管理列表适配器， 未出售 与 已出售公用
 */
public class EquipmentSoldAdapter extends BaseQuickAdapter<EquipmentDao, BaseViewHolder> {

    private Context mContext;
    private List<EquipmentDao> mData;

    /**
     * 构造方法
     *
     * @param context
     * @param data
     */
    public EquipmentSoldAdapter(Context context, List<EquipmentDao> data) {
        super(R.layout.item_equipment, data);
        this.mContext = context;
        this.mData = data;
    }

    @Override
    protected void convert(final BaseViewHolder holder, final EquipmentDao bean) {
        //基本信息
        holder
                .setText(R.id.tv_name, "品牌　型号： " + bean.getName())
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

        //设置照片 如果未出售则显示出厂编号照片， 如果是已出售的，显示人机合影照片、
        if (bean.getSell()) {
            if (!TextUtils.isEmpty(bean.getPhoto_rjhy())) {
                Bitmap bitmap = BitmapUtils.getDiskBitmap(bean.getPhoto_rjhy());
                if (null != bitmap)
                    holder.setImageBitmap(R.id.img_tx, bitmap);
                else {
                    ImageView v = holder.getView(R.id.img_tx);
                    v.setImageResource(R.drawable.img_splash1);
                }
            }
        } else {
            if (!TextUtils.isEmpty(bean.getPhoto_fdjbh())) {
                Bitmap bitmap = BitmapUtils.getDiskBitmap(bean.getPhoto_fdjbh());
                if (null != bitmap)
                    holder.setImageBitmap(R.id.img_tx, bitmap);
                else {
                    ImageView v = holder.getView(R.id.img_tx);
                    v.setImageResource(R.drawable.img_splash1);
                }
            }
        }

        //设置点击事件, 根据出售状态
        holder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EquipmentDetailsActivity.class);
                intent.putExtra(Constant.EQUIPMENT_ID, getData().get(holder.getAdapterPosition()).getEngine_id());
                intent.putExtra(Constant.START_TYPE, getData().get(holder.getAdapterPosition()).getSell() ? Constant.EQUIPMENT_SOLD_YES : Constant.EQUIPMENT_SOLD_NO);
                mContext.startActivity(intent);
            }
        });


    }
}
