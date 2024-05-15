package com.mxy.mypro.controller;

import com.mxy.mypro.entity.PayInfo;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

@RestController
public class PayController {
    private final PayService payService;
    @Autowired
    public PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/info/addpay")
    public int[] addPay(@RequestBody List<PayInfo> payInfo) {
        int res1 = 0;
        int res2 = 0;
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        long timestamp = System.currentTimeMillis();
        //String test = Long.toString(timestamp);
        //System.out.println("test"+test);
        String key = Base64.getEncoder().encodeToString(String.valueOf(timestamp).getBytes()); //base64编码
        System.out.println("key:"+key);
        float summary = 0;
        for (PayInfo payInfo1 : payInfo) {
            summary += payInfo1.getMoney();
        }
        res1 = payService.addTrans(payInfo.get(0).getElderid(),payInfo.get(0).getShopid(),summary,formattedTime,key);
        int transid = payService.getTransId(key);
        for(PayInfo payInfo1 : payInfo){
            res2 = payService.addTransDetail(transid, payInfo1.getGoodsname(), payInfo1.getQuantity(), payInfo1.getMoney());
        }
        System.out.println("res1:"+res1+";res2:"+res2);
        return new int[]{res1,res2};
    }

    @GetMapping("/info/transcount")
    public ArrayList<String> Trans(String shopid){
        LocalDateTime now = LocalDateTime.now();
        String currentDay = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))+"%";
        System.out.println("currentDay:"+currentDay);
        ArrayList<String> res = new ArrayList<>();
        String res1 = String.valueOf(payService.getTransCount(shopid, currentDay));
        String res2 = String.valueOf(payService.gettransMoney(shopid, currentDay));
        if(res2.equals("null")){
            res2 = "0";
        }
        res.add(res1);
        res.add(res2);
        System.out.println(res);
        return res;
    }

    @GetMapping("/info/sevenIncome")
    public ArrayList<String> SevenIncome(String shopid){
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ArrayList<String> sevenDate = new ArrayList<>();
        ArrayList<String> res = new ArrayList<>();
        // 打印近七天的日期
        for (int i = 0; i < 7; i++) {
            String dateStr = currentDate.format(dateFormatter);
            sevenDate.add(dateStr+"%");
            currentDate = currentDate.minusDays(1);
        }
        System.out.println("sevenDate:"+sevenDate);
        for (String dateStr1 : sevenDate) {
            String tmp = String.valueOf(payService.gettransMoney(shopid, dateStr1));
            if(tmp.equals("null")){
                tmp = "0";
            }
            res.add(tmp);
        }
        Collections.reverse(res);
        System.out.println("res2:"+res);

        return res;
    }
}
