package com.mxy.mypro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.elderly;
import com.mxy.mypro.mapper.AdminMapper;
import com.mxy.mypro.mapper.ElderlyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElderlyService {
    private final ElderlyMapper elderlymapper;
    @Autowired
    public ElderlyService(ElderlyMapper elderlymapper) {
        this.elderlymapper = elderlymapper;
    }
    public PaginationResult<elderly> GetPage(int page, int size){
        Page<elderly> adminPage = new Page<>(page, size);
        IPage<elderly> result = elderlymapper.selectPage(adminPage, null);
        long total = result.getTotal();
        int totalPages = (int) Math.ceil((double) total / size);
        return new PaginationResult<>(result.getRecords(), total, totalPages);
    }
//    public List<elderly> selectByName(String name) {
//        List<elderly> adminList = elderlymapper.selectList(new QueryWrapper<elderly>().eq("name", name));
//        return adminList;
//    }

    public int insert(elderly eld) {
        return elderlymapper.Insert(eld);
    }

    public int editadmin(elderly eld) {
        return elderlymapper.updateById(eld);
    }

    public int deleteelderly(int id) {
        return elderlymapper.deleteById(id);
    }


    public PaginationResult<elderly> selectByName(String name, int ipage, int iPageSize) {
        IPage<elderly> elderlyList = elderlymapper.selectByName(new Page<>(ipage,iPageSize),name);
        List<elderly> list = elderlyList.getRecords();
        long total = elderlyList.getTotal();
        return new PaginationResult(list, total,0);
    }

    public List<elderly> getByName(String name) {
        return elderlymapper.getByname(name);
    }

    public int getNum(String location) {
        return elderlymapper.getNum(location);
    }

    public Integer getID(String family) {
        return elderlymapper.getID(family);
    }

    public String getName(Integer elderid) {
        return elderlymapper.getName(elderid);
    }

    public List<elderly>  getByRFID(String rfid) {
            return elderlymapper.getByRFID(rfid);
    }
}
