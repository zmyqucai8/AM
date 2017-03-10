package com.qiaotouxi.am.framework.base;

/**
 * @Created by zmy.
 * @Date 2017/3/1 0001.
 * @Describe
 */

public class Constant {

    /**
     * 权限请求回调
     */
    public static final int PERMISSION_PHONE = 103;
    /**
     * log开关
     */
    public static final boolean LogDebug = true;
    /**
     * 拍照上传的requestCode
     */
    public static final int CAPTURE = 101;
    /**
     * 相册选择的requestCode
     */
    public static final int ALBUM = 102;
    /**
     * 客户管理
     */
    public static final int TYPE_CUSTOMER = 1;
    /**
     * 设备管理
     */
    public static final int TYPE_EQUIPMENT = 0;


    /**
     * 设备已出售
     */
    public static final int EQUIPMENT_SOLD_YES = 1;
    /**
     * 设备未出售
     */
    public static final int EQUIPMENT_SOLD_NO = 0;
    /**
     * 设备管理刷新
     */
    public static final int EQUIPMENT_ALL = -1;

    /**
     * 设备id传递的name
     */
    public static final String EQUIPMENT_ID = "equipment_id";

    /**
     * 客户id传递的name
     */
    public static final String CUSTMER_PHONE = "custmer_phone";

    /**
     * 页面跳转之前的类型，用于操作刷新具体页面
     * 0=未出售设备
     * 1=已出售设备
     */
    public static final String START_TYPE = "start_type";

    /**
     * 跳转客户选择的rquestCode
     */
    public static final int CUSTMER_SELECT_REQUEST_CODE = 105;


    /**
     * 照片类型，
     * 0 = 发动机编号照
     * 1=出厂编号照
     * 2=人机合影照
     */
    public static final int PHOTO_FDJBH = 0;
    public static final int PHOTO_CCBH = 1;
    public static final int PHOTO_RJHY = 2;
}
