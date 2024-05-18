package com.mxy.mypro.entity;

public class SleepyData {
    private String time;
    private Integer count;

    @Override
    public String toString() {
        return "SleepyData{" +
                "time='" + time + '\'' +
                ", count=" + count +
                '}';
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
