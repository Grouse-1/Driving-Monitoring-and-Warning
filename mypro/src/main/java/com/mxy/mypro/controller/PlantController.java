package com.mxy.mypro.controller;

import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.Plant;
import com.mxy.mypro.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PlantController {
    private final PlantService plantService;
    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @GetMapping("/info/plant")
    public PaginationResult<Plant> GetPlantInfo(String name, String page, String pageSize) {
        int Ipage = Integer.parseInt(page);
        int IPageSize = Integer.parseInt(pageSize);
        PaginationResult<Plant> list;
        if(name.equals("")){
            list = plantService.getAllPlant(Ipage, IPageSize);
            return list;
        }else {
            System.out.println("name"+name);
            list = plantService.selectByName(name, Ipage, IPageSize);
            return list;
        }
    }

    @PostMapping("/info/addplant")
    public int AddPlant(@RequestBody Plant plant) {
        int result = plantService.Insert(plant);
        System.out.println("add:"+result);
        return result;
    }
    //
    @PostMapping("/info/editplant")
    public int EditPlant(@RequestBody Plant plant) {
        System.out.println(plant.toString());
        return plantService.editPlant(plant);
    }
    @PostMapping("/info/delplant")
    public int DelPlant(@RequestBody Map id) {
//        int Iid = Integer.parseInt(id);
        //System.out.println("del:"+(Integer) id.get("id"));
        //return  0;
        return plantService.deletePlant((Integer)id.get("id"));
    }

}
