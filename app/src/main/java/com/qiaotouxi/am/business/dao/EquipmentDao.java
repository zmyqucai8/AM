package com.qiaotouxi.am.business.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 设备表
 */
@Entity
public class EquipmentDao {


    @Id
    private Long id; //id 自增长
    @Property(nameInDb = "PHOTO_LIST")
    private String photo_list; //照片路径， 用，隔开
    @Property(nameInDb = "NAME")
    private String name;//设备名称
    @Property(nameInDb = "BRAND")
    private String brand;//设备品牌
    @Property(nameInDb = "ENGINE_ID")
    @Index(unique = true)
    private String engine_id;//发动机编号
    @Property(nameInDb = "MANUFACTURER")
    private String manufacturer;//厂家名称
    @Property(nameInDb = "FACTORY_ID")
    private String factory_id;//出厂编号
    @Property(nameInDb = "REMARK")
    private String remark;//备注信息
    @Property(nameInDb = "SELL")
    private boolean sell;//是否出售
    @Property(nameInDb = "PAYMENT")
    private boolean payment;//是否付款
    @Property(nameInDb = "DATE")
    private Date date;  //购机日期

    @Generated(hash = 1193611033)
    public EquipmentDao(Long id, String photo_list, String name, String brand,
                        String engine_id, String manufacturer, String factory_id, String remark,
                        boolean sell, boolean payment, Date date) {
        this.id = id;
        this.photo_list = photo_list;
        this.name = name;
        this.brand = brand;
        this.engine_id = engine_id;
        this.manufacturer = manufacturer;
        this.factory_id = factory_id;
        this.remark = remark;
        this.sell = sell;
        this.payment = payment;
        this.date = date;
    }

    @Generated(hash = 775307577)
    public EquipmentDao() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoto_list() {
        return this.photo_list;
    }

    public void setPhoto_list(String photo_list) {
        this.photo_list = photo_list;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEngine_id() {
        return this.engine_id;
    }

    public void setEngine_id(String engine_id) {
        this.engine_id = engine_id;
    }

    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getFactory_id() {
        return this.factory_id;
    }

    public void setFactory_id(String factory_id) {
        this.factory_id = factory_id;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean getSell() {
        return this.sell;
    }

    public void setSell(boolean sell) {
        this.sell = sell;
    }

    public boolean getPayment() {
        return this.payment;
    }

    public void setPayment(boolean payment) {
        this.payment = payment;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EquipmentDao(String photo_list, String name, String brand,
                        String engine_id, String manufacturer, String factory_id, String remark,
                        boolean sell, boolean payment, Date date) {
        this.photo_list = photo_list;
        this.name = name;
        this.brand = brand;
        this.engine_id = engine_id;
        this.manufacturer = manufacturer;
        this.factory_id = factory_id;
        this.remark = remark;
        this.sell = sell;
        this.payment = payment;
        this.date = date;
    }

    @Override
    public String toString() {
        return "EquipmentDao{" +
                "id=" + id +
                ", photo_list='" + photo_list + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", engine_id='" + engine_id + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", factory_id='" + factory_id + '\'' +
                ", remark='" + remark + '\'' +
                ", sell=" + sell +
                ", payment=" + payment +
                ", date=" + date +
                '}';
    }
}
