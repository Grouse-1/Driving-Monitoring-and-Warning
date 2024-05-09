package com.mxy.mypro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("rfidtemp")
public class Rfidtmp {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String uid;

    @Override
    public String toString() {
        return "rfidtmp{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
