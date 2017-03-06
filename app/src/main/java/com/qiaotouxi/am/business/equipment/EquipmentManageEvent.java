package com.qiaotouxi.am.business.equipment;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 刷新设备管理的evnet
 */

public class EquipmentManageEvent {
    /**
     * 0=刷新未出售 ，1= 刷新已出售
     */
    public int type;

    /**
     * 实例对象
     */
    public static EquipmentManageEvent mEvent;

    /**
     * 单列模式获取是咧
     *
     * @return
     */
    public static synchronized EquipmentManageEvent getInstance() {

        if (mEvent == null) {
            return mEvent = new EquipmentManageEvent();
        } else {
            return mEvent;
        }

    }

}
