package com.qiaotouxi.am.business.dao;

import com.qiaotouxi.am.framework.utils.AmUtlis;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @Created by zmy.
 * @Date 2017/3/3 0003.
 * 客户表 Dao
 */

@Entity
public class CustomerDao implements Comparable<CustomerDao> {
    @Id
    private Long id; //id 自增长
    @Property(nameInDb = "NAME")
    private String name;//客户姓名
    @Property(nameInDb = "PHONE")
    private String phone;//客户手机
    @Property(nameInDb = "CARD_ID")
    @Index(unique = true)
    private String cardId;//客户身份证号码，  此号码作为客户唯一id， 禁止重复
    @Property(nameInDb = "LOCATION")
    private String location;//客户联系地址
    @Property(nameInDb = "PHOTO_PATH")
    private String photo_path;//客户头像
    @Property(nameInDb = "REMARK")
    private String remark;//客户备注
    @Property(nameInDb = "SEX")
    private int sex;//客户性别
    @Property(nameInDb = "DATE")
    private Date date;//date日期 未转
    //    @Property(nameInDb = "BILL")
//    private List<String> bll;
    @Property(nameInDb = "PINYIN")
    private String pinyin;
    @Property(nameInDb = "BUY")
    private boolean buy;//是否购机

    @Property(nameInDb = "ENGINE_ID_LIST")//已购设备id ，分开
    private String engine_id_list;


    @Override
    public int compareTo(CustomerDao another) {
        return this.pinyin.compareTo(another.pinyin);
    }

    @Generated(hash = 461357872)
    public CustomerDao() {
    }

    @Generated(hash = 199927924)
    public CustomerDao(Long id, String name, String phone, String cardId,
                       String location, String photo_path, String remark, int sex, Date date,
                       String pinyin, boolean buy, String engine_id_list) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cardId = cardId;
        this.location = location;
        this.photo_path = photo_path;
        this.remark = remark;
        this.sex = sex;
        this.date = date;
        this.pinyin = pinyin;
        this.buy = buy;
        this.engine_id_list = engine_id_list;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
        this.pinyin = AmUtlis.getPinYin(name);
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardId() {
        return this.cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoto_path() {
        return this.photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CustomerDao{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", cardId='" + cardId + '\'' +
                ", location='" + location + '\'' +
                ", photo_path='" + photo_path + '\'' +
                ", remark='" + remark + '\'' +
                ", sex=" + sex +
                ", date=" + date +
                '}';
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public boolean getBuy() {
        return this.buy;
    }

    public void setBuy(boolean buy) {
        this.buy = buy;
    }

    public String getEngine_id_list() {
        return this.engine_id_list;
    }

    public void setEngine_id_list(String engine_id_list) {
        this.engine_id_list = engine_id_list;
    }

}