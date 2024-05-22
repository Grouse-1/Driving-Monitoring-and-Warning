package com.mxy.mypro.entity;

public class emotionPieChart {
    String emotion;
    Integer count;

    @Override
    public String toString() {
        return "emotionPieChart{" +
                "emotion='" + emotion + '\'' +
                ", count=" + count +
                '}';
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
