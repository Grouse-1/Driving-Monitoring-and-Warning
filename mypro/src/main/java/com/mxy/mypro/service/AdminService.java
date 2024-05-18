package com.mxy.mypro.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.exception.ServiceException;
import com.mxy.mypro.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService{
    private final AdminMapper adminmapper;
    @Autowired
    public AdminService(AdminMapper adminmapper) {
        this.adminmapper = adminmapper;
    }

    public List<Admin> login(Admin admin_t) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", admin_t.getName());
        List<Admin> adminList = adminmapper.selectList(queryWrapper);
        if(adminList == null || adminList.isEmpty()) {
            throw
        new ServiceException("用户名或密码错误");
        }else {
            if(admin_t.getPassword().equals(adminList.get(0).getPassword())) {
                return adminList;
            }else {
                throw
                        new ServiceException("用户名或密码错误");
            }
        }
    }
    public Integer getIdByName(String name) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", name)
                .select("id")
                .last("LIMIT 1");

        Admin admin = adminmapper.selectOne(queryWrapper);
        return admin != null ? admin.getId() : null;
    }

    public List<Admin> selectAll() {
        List<Admin> adminList = adminmapper.selectList(null);
        return adminList;
    }
    public PaginationResult<Admin> selectByName(String name, int page, int pageSize) {
        IPage<Admin> adminList = adminmapper.selectByName(new Page<>(page,pageSize),name);
        List<Admin> list = adminList.getRecords();
        long total = adminList.getTotal();
        return new PaginationResult(list, total,0);
    }
    public PaginationResult<Admin> GetPage(int page, int size){
        Page<Admin> adminPage = new Page<>(page, size);
        IPage<Admin> result = adminmapper.selectPage(adminPage, null);
        long total = result.getTotal();
        int totalPages = (int) Math.ceil((double) total / size);
        return new PaginationResult<>(result.getRecords(), total, totalPages);
    }

    public int insert(Admin admin) {
        return adminmapper.Insert(admin);
    }

    public int editadmin(Admin admin) {
        return adminmapper.updateById(admin);
    }
    public int deleteadmin(int id) {
        return adminmapper.deleteById(id);
    }
}
