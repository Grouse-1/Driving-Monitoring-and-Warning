package com.mxy.mypro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class elderly {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private Integer age;
    private String sex;
    private float balance;
    private String rfidtag;
    private Integer locationId;
    private Integer auth;

    @Override
    public String toString() {
        return "elderly{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", balance=" + balance +
                ", rfid_tag='" + rfidtag + '\'' +
                ", locationId=" + locationId +
                ", auth=" + auth +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getRfidtag() {
        return rfidtag;
    }

    public void setRfidtag(String rfidtag) {
        this.rfidtag = rfidtag;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setLocationId(Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }
}
