package com.mxy.mypro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class TravelRecord {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String name;
    private String location;
    private String Tdate;

    @Override
    public String toString() {
        return "TravelRecord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", Tdate='" + Tdate + '\'' +
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTdate() {
        return Tdate;
    }

    public void setTdate(String tdate) {
        Tdate = tdate;
    }
}
