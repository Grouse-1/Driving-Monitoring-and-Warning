package com.mxy.mypro.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.Menu;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.Plant;
import com.mxy.mypro.mapper.MenuMapper;
import com.mxy.mypro.mapper.PlantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlantService {
    private final PlantMapper plantMapper;
    @Autowired
    public PlantService(PlantMapper plantMapper) {
        this.plantMapper = plantMapper;
    }
    public PaginationResult<Plant> getAllPlant(int pageNum, int pageSize) {
        IPage<Plant> page = plantMapper.selectPlant(new Page<>(pageNum,pageSize));
        List<Plant> list = page.getRecords();
        long total = page.getTotal();
//        PageHelper.startPage(pageNum, pageSize);
//        List<Family> list = familyMapper.selectFamily();
//        System.out.println("list: " + list);
//        PageInfo<Family> pageInfo = new PageInfo<>(list);
//        List<Family> dataList = pageInfo.getList(); // 获取当前页数据
        return new PaginationResult<Plant>(list, total,0);

    }

    public PaginationResult<Plant> selectByName(String name, int page, int pageSize) {
        IPage<Plant> famiyList = plantMapper.selectByName(new Page<>(page,pageSize),name);
        List<Plant> list = famiyList.getRecords();
        long total = famiyList.getTotal();
        return new PaginationResult<Plant>(list, total,0);
    }

    public int Insert(Plant plant) {
        int res = plantMapper.findName(plant.getName());
        if (res == 0) {
            return plantMapper.insert(plant);
        }else {
            return 0;
        }
    }

    public int editPlant(Plant plant) {
        int res = plantMapper.findName(plant.getName());
        if (res > 0) {
            return plantMapper.updateById(plant);
        }else {
            return 0;
        }
    }

    public int deletePlant(Integer id) {
        return  plantMapper.deleteById(id);
    }
}
