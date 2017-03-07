package com.qiaotouxi.am.business.equipment;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 刷新设备管理 已出售未出售角标数量的evnet
 */

public class EquipmentManageCountEvent {
    /**
     * 0=刷新未出售 ，1= 刷新已出售
     */
    public int type;
    /**
     * 未出售总数， 或者 已出售总数
     */
    public int count;

    /**
     * 实例对象
     */
    public static EquipmentManageCountEvent mEvent;

    /**
     * 单列模式获取是咧
     *
     * @return
     */
    public static synchronized EquipmentManageCountEvent getInstance() {

        if (mEvent == null) {
            return mEvent = new EquipmentManageCountEvent();
        } else {
            return mEvent;
        }

    }

}
