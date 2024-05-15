package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Food;
import com.mxy.mypro.service.AdviseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AdviseController {
    private final AdviseService adviseService;

    @Autowired
    public AdviseController(AdviseService adviseService) {
        this.adviseService = adviseService;
    }

    @PostMapping("/info/getadvise")
    public Map<String, List<String>> getadvise(@RequestBody String[] array) {
//         for (int i = 0; i < array.length; i++) {
//
//        }
        Map<String, List<String>> map =  adviseService.getFood(array);
        System.out.println(map);
        //{蛋白质类=[豆腐, 猪肉, 鸡胸肉, 羊肉], 谷物类=[土豆, 全麦面包, 荞麦, 大麦], 脂肪类=[牛油, 橄榄油, 腰果, 奶酪]}
        return map;
    }
}
