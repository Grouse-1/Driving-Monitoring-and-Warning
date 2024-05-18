package com.mxy.mypro.service;

//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.exception.ServiceException;
import com.mxy.mypro.mapper.FamilyMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyService {
    private final FamilyMapper familyMapper;

    @Autowired
    public FamilyService(FamilyMapper familyMapper) {
        this.familyMapper = familyMapper;
    }

    public PaginationResult<Family> getFamily(int pageNum, int pageSize) {
        IPage<Family> page = familyMapper.selectFamily(new Page<>(pageNum,pageSize));
        List<Family> list = page.getRecords();
        long total = page.getTotal();
//        PageHelper.startPage(pageNum, pageSize);
//        List<Family> list = familyMapper.selectFamily();
//        System.out.println("list: " + list);
//        PageInfo<Family> pageInfo = new PageInfo<>(list);
//        List<Family> dataList = pageInfo.getList(); // 获取当前页数据
        return new PaginationResult<Family>(list, total,0);
    }

    public int deleteFamily(Integer id){
        return familyMapper.delete(id);
    }

    public int Insert(Family mfamily) {
        int res = familyMapper.findElderly(mfamily.getElderly());
        if (res > 0) {
            return familyMapper.Insert(mfamily);
        }else {
            return 0;
        }

    }

    public int editfamily(Family mfamily) {
        int res = familyMapper.findElderly(mfamily.getElderly());
        if (res > 0) {
            return familyMapper.editfamily(mfamily);
        }else {
            return 0;
        }

    }

//    public PaginationResult<Family> searchFsmily(String name, int ipage, int iPageSize) {
//        return
//    }

    public PaginationResult<Family> selectByName(String name, int page, int pageSize) {
        IPage<Family> famiyList = familyMapper.selectByName(new Page<>(page,pageSize),name);
        List<Family> list = famiyList.getRecords();
        long total = famiyList.getTotal();
        return new PaginationResult(list, total,0);
    }

    public List<Family> login(Family family) {
        List<Family> list = familyMapper.selectList(family.getName());
        if(list == null || list.isEmpty()) {
            throw
                    new ServiceException("用户名或密码错误");
        }else {
            if(list.get(0).getPassword().equals(family.getPassword())){
                return list;
            }
            else  {
                throw
                        new ServiceException("用户名或密码错误");
            }
        }

    }
}
