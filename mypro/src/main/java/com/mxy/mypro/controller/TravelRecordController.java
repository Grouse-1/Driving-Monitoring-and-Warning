package com.mxy.mypro.controller;

import com.mxy.mypro.entity.TravelRecord;
import com.mxy.mypro.mapper.EmotionMapper;
import com.mxy.mypro.mapper.TravelRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TravelRecordController {

    private final TravelRecordMapper travelRecordMapper;

    @Autowired
    public TravelRecordController(TravelRecordMapper travelRecordMapper) {
        this.travelRecordMapper = travelRecordMapper;
    }


    @GetMapping("/info/travel")
    public List<TravelRecord> getTravelRecord() {
        return travelRecordMapper.getTravelRecord();
    }
}
