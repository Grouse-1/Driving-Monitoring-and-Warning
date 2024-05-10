package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Food;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.FamilyService;
import com.mxy.mypro.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShopFoodController {
    private final FoodService foodService;
    @Autowired
    public ShopFoodController(FoodService foodService) {
        this.foodService = foodService;
    }

    @GetMapping("/info/food")
    public List<Food> info(String name) {
        List<Food> foodList = foodService.getFoodList(name);
        if (foodList.isEmpty()) {
            System.out.println("foodList is empty");
            return null;
        } else {
            System.out.println("foodList is not empty");
            return foodList;
        }

    }
}
