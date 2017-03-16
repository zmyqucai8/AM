package com.qiaotouxi.am.business.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * @Created by zmy.
 * @Date 2017/3/6 0006.
 * 设备表
 */
@Entity
public class EquipmentDao {
    @Override
    public String toString() {
        return "EquipmentDao{" +
                "id=" + id +
                ", photo_fdjbh='" + photo_fdjbh + '\'' +
                ", photo_ccbh='" + photo_ccbh + '\'' +
                ", photo_rjhy='" + photo_rjhy + '\'' +
                ", name='" + name + '\'' +
                ", engine_id='" + engine_id + '\'' +
                ", factory_id='" + factory_id + '\'' +
                ", remark='" + remark + '\'' +
                ", sell=" + sell +
                ", payment=" + payment +
                ", date='" + date + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    @Id
    private Long id; //id 自增长
    @Property(nameInDb = "PHOTO_FDJBH")
    private String photo_fdjbh; //发动机编号
    @Property(nameInDb = "PHOTO_CCBH")
    private String photo_ccbh; //出厂编号
    @Property(nameInDb = "PHOTO_RJHY")
    private String photo_rjhy; //人机合影
    @Property(nameInDb = "NAME")
    private String name;//设备名称
    //    @Property(nameInDb = "BRAND")
//    private String brand;//设备品牌
    @Property(nameInDb = "ENGINE_ID")
    @Index(unique = true)
    private String engine_id;//发动机编号  作为设备唯一id
    //    @Property(nameInDb = "MANUFACTURER")
//    private String manufacturer;//厂家名称
    @Property(nameInDb = "FACTORY_ID")
    private String factory_id;//出厂编号
    @Property(nameInDb = "REMARK")
    private String remark;//备注信息
    @Property(nameInDb = "SELL")
    private boolean sell;//是否出售
    @Property(nameInDb = "PAYMENT")
    private boolean payment;//是否付款
    @Property(nameInDb = "DATE")
    private String date;  //购机日期
    @Property(nameInDb = "PHONE")
    private String phone;  //购机者phone sell=true时才有值

    @Property(nameInDb = "DIR_PATH")
    private String dirPath;  //当前用户存储的路径dir
    @Property(nameInDb = "TXT_NAME")
    private String txtName;//当前设备资料的txt文件名


    public EquipmentDao(String photo_fdjbh, String photo_ccbh,
                        String photo_rjhy, String name, String engine_id,
                        String factory_id, String remark, boolean sell,
                        boolean payment, String date, String phone, String txtname) {
        this.photo_fdjbh = photo_fdjbh;
        this.photo_ccbh = photo_ccbh;
        this.photo_rjhy = photo_rjhy;
        this.name = name;
        this.engine_id = engine_id;
        this.factory_id = factory_id;
        this.remark = remark;
        this.sell = sell;
        this.payment = payment;
        this.date = date;
        this.phone = phone;
        this.dirPath = name + engine_id;
        this.txtName = txtname;
    }


    @Generated(hash = 208140055)
    public EquipmentDao(Long id, String photo_fdjbh, String photo_ccbh, String photo_rjhy, String name,
                        String engine_id, String factory_id, String remark, boolean sell, boolean payment,
                        String date, String phone, String dirPath, String txtName) {
        this.id = id;
        this.photo_fdjbh = photo_fdjbh;
        this.photo_ccbh = photo_ccbh;
        this.photo_rjhy = photo_rjhy;
        this.name = name;
        this.engine_id = engine_id;
        this.factory_id = factory_id;
        this.remark = remark;
        this.sell = sell;
        this.payment = payment;
        this.date = date;
        this.phone = phone;
        this.dirPath = dirPath;
        this.txtName = txtName;
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


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEngine_id() {
        return this.engine_id;
    }

    public void setEngine_id(String engine_id) {
        this.engine_id = engine_id;
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

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoto_fdjbh() {
        return this.photo_fdjbh;
    }

    public void setPhoto_fdjbh(String photo_fdjbh) {
        this.photo_fdjbh = photo_fdjbh;
    }

    public String getPhoto_ccbh() {
        return this.photo_ccbh;
    }

    public void setPhoto_ccbh(String photo_ccbh) {
        this.photo_ccbh = photo_ccbh;
    }

    public String getPhoto_rjhy() {
        return this.photo_rjhy;
    }

    public void setPhoto_rjhy(String photo_rjhy) {
        this.photo_rjhy = photo_rjhy;
    }


    public String getFactory_id() {
        return this.factory_id;
    }


    public String getDirPath() {
        return this.dirPath;
    }


    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }


    public String getTxtName() {
        return this.txtName;
    }


    public void setTxtName(String txtName) {
        this.txtName = txtName;
    }

}
