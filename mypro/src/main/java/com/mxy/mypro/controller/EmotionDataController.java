package com.mxy.mypro.controller;

import com.mxy.mypro.entity.EmotionData;
import com.mxy.mypro.mapper.BuyDataMapper;
import com.mxy.mypro.mapper.EmotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
