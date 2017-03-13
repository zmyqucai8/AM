package com.qiaotouxi.am.business.dao;

import android.content.Context;

import com.qiaotouxi.am.App;
import com.qiaotouxi.am.framework.utils.AmUtlis;

import java.util.ArrayList;
import java.util.Date;
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
        List<EquipmentDao> listDao = new ArrayList<EquipmentDao>();
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).getSell()) {
                listDao.add(list.get(i));
            }

        }
        return listDao;
    }

    /**
     * 获取已出售设备
     */
    public static List<EquipmentDao> getEquipmentSoldYesData(Context context) {
        EquipmentDaoDao daoDao = App.getDaoSession(context).getEquipmentDaoDao();
        List<EquipmentDao> list = daoDao.queryBuilder().list();
        List<EquipmentDao> listDao = new ArrayList<EquipmentDao>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getSell()) {
                listDao.add(list.get(i));
            }

        }
        return listDao;
    }

    /**
     * 删除一个设备
     *
     * @param dao 设备dao
     */
    public static boolean deleteEquipment(Context context, EquipmentDao dao) {

        try {
            EquipmentDaoDao daoDao = App.getDaoSession(context).getEquipmentDaoDao();
            daoDao.delete(dao);
            return true;
        } catch (Exception e) {
            return false;
        }


    }


    /**
     * 删除一个客户
     *
     * @param dao 客户dao
     */
    public static boolean deleteCustomer(Context context, CustomerDao dao) {

        try {
            CustomerDaoDao daoDao = App.getDaoSession(context).getCustomerDaoDao();
            daoDao.delete(dao);
            return true;
        } catch (Exception e) {
            return false;
        }


    }


    /**
     * 查询设备 根据设备id 即 发动机编号
     */
    public static EquipmentDao getEquipmentByID(Context context, String id) {
        return App.getDaoSession(context).getEquipmentDaoDao().queryRawCreate("where ENGINE_ID=? order by ENGINE_ID", id).unique();
    }

    /**
     * 查询设备 根据设备id 即 发动机编号
     *
     * @param ids "id1,id2,id3..."
     */
    public static List<EquipmentDao> getEquipmentByIDs(Context context, String ids) {
        List<EquipmentDao> list = new ArrayList<EquipmentDao>();
        List<String> strings = AmUtlis.splitStringByChar(",", ids);
        for (int i = 0; i < strings.size(); i++) {
            list.add(App.getDaoSession(context).getEquipmentDaoDao().queryRawCreate("where ENGINE_ID=? order by ENGINE_ID", strings.get(i)).unique());
        }
        AmUtlis.showLog("list.sezi=" + list.size());
        return list;
    }


    /**
     * 查询客户 根据客户id 及身份证
     */
    public static CustomerDao getCustomerByPhone(Context context, String phone) {
        return App.getDaoSession(context).getCustomerDaoDao().queryRawCreate("where PHONE=? order by PHONE", phone).unique();
    }


    /**
     * 通过姓名或者电话查询客户信息
     *
     * @param context
     * @param str     可能是name 可能是phone
     * @return 如果都查询不到 返回一个null 注意判断
     */
    public static List<CustomerDao> getCustomerByNamePhone(Context context, String str) {
//        CustomerDaoDao customerDaoDao = App.getDaoSession(context).getCustomerDaoDao();
//        CustomerDao nameUnique = customerDaoDao.queryRawCreate("where NAME=? order by NAME", str).unique();
//        if (nameUnique != null && nameUnique.getName().equals(str)) {
//            return nameUnique;
//        } else {
//            CustomerDao phoneUnique = customerDaoDao.queryRawCreate("where PHONE=? order by PHONE", str).unique();
//            if (phoneUnique != null && phoneUnique.getPhone().equals(str)) {
//                return phoneUnique;
//            } else {
//                return null;
//            }
//        }
        //模糊匹配查询,只要包含就显示
        List<CustomerDao> allCustomerData = getAllCustomerData(context);
        List<CustomerDao> list = new ArrayList<CustomerDao>();
        for (int i = 0; i < allCustomerData.size(); i++) {
            CustomerDao dao = allCustomerData.get(i);
            String phone = dao.getPhone();
            String name = dao.getName();
            if (name.contains(str)) {
                list.add(dao);
            } else if (phone.contains(str) && str.length() > 2) {//如果输入电话 长度必须大于2才开始匹配
                list.add(dao);
            }
        }
        return list;
    }

    /**
     * 获取所有客户数据
     *
     * @param context
     * @return
     */
    public static List<CustomerDao> getAllCustomerData(Context context) {

        try {
            return App.getDaoSession(context).getCustomerDaoDao().queryBuilder().list();
        } catch (Exception e) {

            return new ArrayList<CustomerDao>();
        }


    }


    /**
     * 更新一个客户资料
     *
     * @param dao
     */
    public static void updateCustomerDao(Context context, CustomerDao dao) {
        App.getDaoSession(context).getCustomerDaoDao().update(dao);
    }


    public static String[] zm = {"张",
            "阿达a",
            "匹配b",
            "哦哦",
            "已i1",
            "请求",
            "汪汪汪",
            "饿饿饿",
            "日日日",
            "略略略",
            "买买买",
            "你你你",
            "蹦蹦蹦",
            "啧啧啧",
            "看看看",
            "嘎嘎嘎",
            "发发发",
            "对对对",
            "谁是谁",
            "她她她",
            "吃吃吃",
            "没有",
            "还好",
            "想你",
            "不要",
            "你我",
            "粉色",
            "梦想",
            "你怕吗",
            "爱好呢",
    };

    /**
     * 测试方法  批量向数据库添加客户
     */
    public static void testAddCustomer(Context context) {
        CustomerDaoDao daoDao = App.getDaoSession(context).getCustomerDaoDao();
        ArrayList<CustomerDao> list = new ArrayList<CustomerDao>();
        for (int i = 0; i < 30; i++) {
            CustomerDao dao = new CustomerDao();
            dao.setCardId("cardid" + System.currentTimeMillis() + zm[i]);
            dao.setName(zm[i] + "姓名");
            dao.setLocation("深圳市" + i);
            dao.setSex(58 % 2);
            dao.setRemark("没有备注的咯" + i);
            dao.setBuy(false);
            dao.setPhoto_path("");
            dao.setDate(new Date());
            dao.setPhone("15" + i + System.currentTimeMillis());
            dao.setEngine_id_list("");

            list.add(dao);
        }
        daoDao.insertInTx(list);

    }


    /**
     * 测试方法  批量添加未出售设备
     */
    public static void testAddEquipmentDao(Context context) {
        EquipmentDaoDao daoDao = App.getDaoSession(context).getEquipmentDaoDao();
        ArrayList<EquipmentDao> list = new ArrayList<EquipmentDao>();
        for (int i = 0; i < 30; i++) {
            EquipmentDao dao = new EquipmentDao();
            dao.setPayment(false);
            dao.setSell(false);
            dao.setPhone("");//未出售就没有
            dao.setRemark(i + "备注信息随便咯");
            dao.setDate("");
            dao.setEngine_id(i + "发动机编号" + System.currentTimeMillis() + zm[i]);
            dao.setFactory_id(i + "生产编号");
            dao.setName(i + "设备名字");
            list.add(dao);
        }
        daoDao.insertInTx(list);

    }

}
