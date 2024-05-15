package com.mxy.mypro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("outstate")
public class EmotionData {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Integer location;
    private String emotion;
    private String Ltime;
    private Integer elderid;
    private Integer count;

    public EmotionData(int location, String emotion, String ltime, Integer elderid, Integer count) {
        this.location = location;
        this.emotion = emotion;
        this.Ltime = ltime;
        this.elderid = elderid;
        this.count = count;
    }

    @Override
    public String toString() {
        return "EmotionData{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", emotion='" + emotion + '\'' +
                ", Ldate='" + Ltime + '\'' +
                '}';
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getElderid() {
        return elderid;
    }

    public void setElderid(Integer elderid) {
        this.elderid = elderid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getLtime() {
        return Ltime;
    }

    public void setLtime(String ltime) {
        Ltime = ltime;
    }
}
