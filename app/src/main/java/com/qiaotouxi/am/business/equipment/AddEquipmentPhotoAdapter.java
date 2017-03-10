//package com.qiaotouxi.am.business.equipment;
//
//import android.graphics.Bitmap;
//import android.view.View;
//
//import com.bigkoo.alertview.AlertView;
//import com.bigkoo.alertview.OnItemClickListener;
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.qiaotouxi.am.R;
//import com.qiaotouxi.am.framework.utils.BitmapUtils;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Created by zmy.
// * @Date 2017/3/6 0006.
// * <p>
// * 添加设备 照片的adapter
// */
//
//public class AddEquipmentPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
//
//    private AddEquipmentActivity addEquipmentActivity;
//    private EquipmentDetailsActivity equipmentDetailsActivity;
//
//    private boolean isShowAlert = true;
//    /**
//     * 0=添加设备 ，1=设备详情
//     */
//    private int type;
//
//    public AddEquipmentPhotoAdapter(AddEquipmentActivity activity, List<String> list, int type) {
//
//        super(R.layout.item_add_equipment_photo, list);
//        this.addEquipmentActivity = activity;
//        this.type = type;
//    }
//
//    public AddEquipmentPhotoAdapter(EquipmentDetailsActivity activity, List<String> list, int type) {
//
//        super(R.layout.item_add_equipment_photo, list);
//        this.equipmentDetailsActivity = activity;
//        this.type = type;
//    }
//
//    @Override
//    protected void convert(final BaseViewHolder holder, String str) {
//
//
//        Bitmap diskBitmap = BitmapUtils.getDiskBitmap(str);
//
//
//        //删除按钮
//        holder.setImageBitmap(R.id.img_tx, diskBitmap)
//                .setOnClickListener(R.id.img_delete, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if (!isShowAlert) {
//                            return;
//                        }
//                        isShowAlert = false;
//                        new AlertView.Builder().setContext(mContext)
//                                .setStyle(AlertView.Style.Alert)
//                                .setTitle("温馨提示")
//                                .setMessage("确定要删除这张照片吗？")
//                                .setCancelText("取消")
//                                .setDestructive("确定")
//                                .setOthers(null)
//                                .setOnItemClickListener(new OnItemClickListener() {
//                                    @Override
//                                    public void onItemClick(Object o, int position) {
//                                        isShowAlert = true;
//                                        if (position != -1) {
//                                            //确定删除
//                                            if (type == 0) {
//                                                addEquipmentActivity.mImgPathList.remove(holder.getAdapterPosition());
//                                                if (addEquipmentActivity.mImgPathList.size() == 0) {
//                                                    addEquipmentActivity.mAdapter.setNewData(new ArrayList<String>());
//                                                }
//                                                addEquipmentActivity.mAdapter.notifyDataSetChanged();
//                                            } else if (type == 1) {
//                                                equipmentDetailsActivity.mImgPathList.remove(holder.getAdapterPosition());
//                                                if (equipmentDetailsActivity.mImgPathList.size() == 0) {
//                                                    equipmentDetailsActivity.mAdapter.setNewData(new ArrayList<String>());
//                                                }
//                                                equipmentDetailsActivity.mAdapter.notifyDataSetChanged();
//                                            }
//
//                                        }
//                                    }
//                                })
//                                .build()
//                                .show();
//                    }
//
//                });
//
//
//    }
//}
