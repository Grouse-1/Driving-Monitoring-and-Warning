package com.mxy.mypro.controller;

import com.mxy.mypro.entity.EmotionData;
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
    public Map<String, Integer> getSleepyData() {
        Map<String, Integer> map = new HashMap<>();
        for (int hour = 0; hour < 24; hour++) {
            String timeFilter = String.format("%02d:%%:", hour);
            timeFilter = "%" + timeFilter +  "%";
            int queryResult = emotionMapper.getCount(timeFilter);
            map.put(String.format("%02d:00", hour), queryResult);
        }
        return map;
    }
    @GetMapping("/info/getHappyLocation")
    public String getHappyLocation(String elderid) {
        System.out.println("elderid123:" + elderid);
        //查数据库 happy数据 排名最高的地点id 再查地点名字  返回地点字符串
        return "菜市场";
    }

}
