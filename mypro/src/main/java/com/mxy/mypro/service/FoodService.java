package com.mxy.mypro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mxy.mypro.entity.Food;
import com.mxy.mypro.mapper.FamilyMapper;
import com.mxy.mypro.mapper.FoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodMapper foodMapper;

    @Autowired
    public FoodService(FoodMapper foodMapper) {
        this.foodMapper = foodMapper;
    }

    public List<Food> getFoodList(String val) {
        QueryWrapper<Food> queryWrapper = Wrappers.query();
        queryWrapper.like("name", val);
        List<Food> foodList = foodMapper.selectList(queryWrapper);
        return foodList;
    }
}
