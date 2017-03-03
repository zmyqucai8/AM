package com.qiaotouxi.am.business.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @Created by zmy.
 * @Date 2017/3/3 0003.
 * <p>
 * <p>
 * 客户dao
 */

@Entity
public class CustomerDao {

    @Id
    private Long id;

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

    @Property(nameInDb = "NAME")
    private String name;
    @Property(nameInDb = "PHONE")
    private String phone;
    @Property(nameInDb = "CARD_ID")
    @Index(unique = true)
    private String cardId;
    @Property(nameInDb = "LOCATION")
    private String location;
    @Property(nameInDb = "PHOTO_PATH")
    private String photo_path;
    @Property(nameInDb = "REMARK")
    private String remark;
    @Property(nameInDb = "SEX")
    private int sex;
    @Property(nameInDb = "DATE")
    private Date date;
//    @Property(nameInDb = "BILL")
//    private List<String> bll;

    public CustomerDao(String name, String phone, String cardId,
                       String location, String photo_path, String remark, int sex, Date date) {
        this.name = name;
        this.phone = phone;
        this.cardId = cardId;
        this.location = location;
        this.photo_path = photo_path;
        this.remark = remark;
        this.sex = sex;
        this.date = date;
    }

    @Generated(hash = 461357872)
    public CustomerDao() {
    }

    @Generated(hash = 509557247)
    public CustomerDao(Long id, String name, String phone, String cardId,
                       String location, String photo_path, String remark, int sex, Date date) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cardId = cardId;
        this.location = location;
        this.photo_path = photo_path;
        this.remark = remark;
        this.sex = sex;
        this.date = date;
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


}