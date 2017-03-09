package com.qiaotouxi.am.business.customer;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 刷新设备管理的evnet
 */
public class CostomerManageEvent {

    public int type;
    /**
     * 实例对象
     */
    public static CostomerManageEvent mEvent;

    /**
     * 单列模式获取是咧
     *
     * @return
     */
    public static synchronized CostomerManageEvent getInstance() {

        if (mEvent == null) {
            return mEvent = new CostomerManageEvent();
        } else {
            return mEvent;
        }

    }

}
