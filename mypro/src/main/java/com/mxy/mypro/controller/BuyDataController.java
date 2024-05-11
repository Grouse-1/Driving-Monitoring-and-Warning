package com.mxy.mypro.controller;

import com.mxy.mypro.entity.BuyData;
import com.mxy.mypro.service.BuyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BuyDataController {
    private final BuyDataService buyDataService;

    @Autowired
    public BuyDataController(BuyDataService buyDataService) {
        this.buyDataService = buyDataService;
    }

    @GetMapping("/info/buydata")
    public List<BuyData> getData(int elderid) {
        System.out.println("elderid: " + elderid);
        List<BuyData> list = buyDataService.getData(elderid);
        System.out.println(list);
        return list;
    }
}
