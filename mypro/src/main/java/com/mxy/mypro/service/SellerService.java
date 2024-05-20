package com.mxy.mypro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.entity.seller;
import com.mxy.mypro.exception.ServiceException;
import com.mxy.mypro.mapper.AdminMapper;
import com.mxy.mypro.mapper.SellerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerService {
    private final SellerMapper sellerMapper;
    @Autowired
    public SellerService(SellerMapper sellerMapper) {
        this.sellerMapper = sellerMapper;
    }
    public PaginationResult<seller> GetPage(int page, int size){
        Page<seller> adminPage = new Page<>(page, size);
        IPage<seller> result = sellerMapper.selectPage(adminPage, null);
        long total = result.getTotal();
        int totalPages = (int) Math.ceil((double) total / size);
        return new PaginationResult<>(result.getRecords(), total, totalPages);
    }

//    public List<seller> selectByName(String name) {
//        return sellerMapper.selectList(new QueryWrapper<seller>().eq("name", name));
//    }

    public int insert(seller mseller) {
        return sellerMapper.Insert(mseller);
    }

    public int deleteseller(int id) {
        return sellerMapper.deleteById(id);
    }

    public int editseller(seller mseller) {
        return sellerMapper.updateById(mseller);
    }

    public PaginationResult<seller> selectByName(String name, int ipage, int iPageSize) {
        IPage<seller> sellerList = sellerMapper.selectByName(new Page<>(ipage,iPageSize),name);
        List<seller> list = sellerList.getRecords();
        long total = sellerList.getTotal();
        return new PaginationResult(list, total,0);
    }

    public List<seller> login(seller seller1) {
        QueryWrapper<seller> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", seller1.getName());
        List<seller> list =  sellerMapper.selectList(queryWrapper);
        if(list == null || list.isEmpty()) {
            throw
                    new ServiceException("用户名或密码错误");
        }else {
            if(list.get(0).getPassword().equals(seller1.getPassword())){
                return list;
            }else {
                throw
                        new ServiceException("用户名或密码错误");
            }
        }
    }

    public int getID(String seller) {
        return sellerMapper.getID(seller);
    }
}
