package com.mxy.mypro.entity;

public class PayInfo {
    private String goodsname;
    private float quantity;
    private float price;
    private float money;
    private Integer shopid;
    private Integer elderid;

    @Override
    public String toString() {
        return "PayInfo{" +
                "goodsname='" + goodsname + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", money='" + money + '\'' +
                ", shopid=" + shopid +
                ", elderid=" + elderid +
                '}';
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public Integer getElderid() {
        return elderid;
    }

    public void setElderid(Integer elderid) {
        this.elderid = elderid;
    }
}
