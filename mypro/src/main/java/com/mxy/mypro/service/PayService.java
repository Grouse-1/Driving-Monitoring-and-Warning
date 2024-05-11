package com.mxy.mypro.service;

import com.mxy.mypro.mapper.MenuMapper;
import com.mxy.mypro.mapper.PayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayService {
    private final PayMapper payMapper;
    @Autowired
    public PayService(PayMapper payMapper) {
        this.payMapper = payMapper;
    }
    public void addTransDetail(){

    }
    public int addTrans(Integer elderid, Integer shopid, float summary, String formattedTime, String key) {
       return payMapper.addTrans(elderid, shopid, summary, formattedTime, key);
    }

    public int getTransId(String key) {
        return payMapper.TransId(key);
    }

    public int addTransDetail(int transid, String goodsname, float quantity, float money) {
        return payMapper.addTransDetail(transid, goodsname, quantity, money);
    }

    public int getTransCount(String shopid, String currentDay) {
        return payMapper.getTransCount(shopid,currentDay);
    }

    public Float gettransMoney(String shopid, String currentDay) {
        return payMapper.gettransMoney(shopid,currentDay);
    }
}
