package com.mxy.mypro.service;

import com.mxy.mypro.entity.Food;
import com.mxy.mypro.mapper.AdviseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdviseService {
    private final AdviseMapper adviseMapper;

    @Autowired
    public AdviseService(AdviseMapper adviseMapper) {
        this.adviseMapper = adviseMapper;
    }

    public Map<String, List<String>> getFood(String[] array) {
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            String tmp = array[i];
            List<String> list = new ArrayList<>();
            List<Food> foods = adviseMapper.getFood(tmp);
            for (Food food : foods) {
                list.add(food.getName());
            }
            map.put(tmp, list);
        }
        return map;
    }


    // 其他业务逻辑方法...
}

