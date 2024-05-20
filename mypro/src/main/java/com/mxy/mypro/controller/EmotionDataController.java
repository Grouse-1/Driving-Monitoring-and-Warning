package com.mxy.mypro.controller;

import com.mxy.mypro.entity.EmotionData;
import com.mxy.mypro.enums.LocationEnum;
import com.mxy.mypro.mapper.BuyDataMapper;
import com.mxy.mypro.mapper.EmotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class EmotionDataController {
    private final EmotionMapper emotionMapper;

    @Autowired
    public EmotionDataController(EmotionMapper emotionMapper) {
        this.emotionMapper = emotionMapper;
    }

    @GetMapping("/info/emotion")
    public List<EmotionData> getEmotionData() {
        return emotionMapper.selectList(null);
    }
    @GetMapping("/info/sleepydata")
    public Map<String, Integer> getSleepyData(Integer elderid) {
        Map<String, Integer> map = new HashMap<>();
        for (int hour = 0; hour < 24; hour++) {
            String timeFilter = String.format("%02d:%%:", hour);
            timeFilter = "%" + timeFilter +  "%";
            int queryResult = emotionMapper.getCount(timeFilter, elderid);
            map.put(String.format("%02d:00", hour), queryResult);
        }
        return map;
    }
    @GetMapping("/info/getHappyLocation")
    public String getHappyLocation(String elderid) {
        Integer market = emotionMapper.getHappyMCount(elderid);
        Integer park = emotionMapper.getHappyPCount(elderid);
        if(market == null){
            System.out.println("无数据置0");
            market = 0;
        }
        if(park == null){
            System.out.println("无数据置0");
            park = 0;
        }
        if(market > park){
            return LocationEnum.MARKET.getName();
        }else if(market < park){
            return LocationEnum.PARK.getName();
        }else {
            return "";
        }
    }

    @GetMapping("/info/getHappyList")
    public Map<String, Integer> getHappyList(String elderid) {
        Integer count1 = emotionMapper.getHappyMCount(elderid);
        Integer count2 = emotionMapper.getHappyPCount(elderid);
        System.out.println("count1:" + count1+",count2:" + count2);
        if(count1 == null){
            System.out.println("无数据置0");
            count1 = 0;
        }
        if(count2 == null){
            System.out.println("无数据置0");
            count2 = 0;
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("market", count1);
        map.put("park", count2);
        return map;
    }
}
