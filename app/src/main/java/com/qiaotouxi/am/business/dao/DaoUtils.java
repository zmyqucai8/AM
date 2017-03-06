package com.qiaotouxi.am.business.dao;

import android.content.Context;

import com.qiaotouxi.am.App;

import java.util.List;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 数据库操作工具类
 */

public class DaoUtils {


    /**
     * 获取未出售设备
     */
    public static List<EquipmentDao> getEquipmentSoldNoData(Context context) {
        EquipmentDaoDao daoDao = App.getDaoSession(context).getEquipmentDaoDao();
//                List<EquipmentDao> list = daoDao.queryRawCreate("where SELL=? order by SELL", false).list();
        List<EquipmentDao> list = daoDao.queryBuilder().list();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSell()) {
                list.remove(i);
            }

        }
        return list;
    }

}
