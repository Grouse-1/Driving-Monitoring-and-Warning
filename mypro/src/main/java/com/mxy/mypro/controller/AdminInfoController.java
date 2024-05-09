package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.PaginationResult;
import com.mxy.mypro.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AdminInfoController {
    private final AdminService adminService;
    @Autowired
    public AdminInfoController(AdminService adminService) {
        this.adminService = adminService;
    }
    @GetMapping("/info/admin")
    public PaginationResult<Admin> GetAdminInfo(String name, String page, String pageSize) {
        int Ipage = Integer.parseInt(page);
        int IPageSize = Integer.parseInt(pageSize);
        System.out.println("11111"+name+Ipage+IPageSize);
        PaginationResult<Admin> list;
        if(name.equals("")){
            list = adminService.GetPage(Ipage,IPageSize);
            System.out.println("22222"+list);
        return list;
        }
        else {
            list = adminService.selectByName(name, Ipage, IPageSize);
            return list;
        }
        //return null;

    }

    @PostMapping("/info/addadmin")
    public int AddAdmin(@RequestBody Admin admin) {
        int result = adminService.insert(admin);
        System.out.println("add:"+result);
        return result;
    }

    @PostMapping("/info/editadmin")
    public int EditAdmin(@RequestBody Admin admin) {
        System.out.println(admin.toString());
        return adminService.editadmin(admin);
    }
    @PostMapping("/info/deladmin")
    public int DelAdmin(@RequestBody Map id) {
//        int Iid = Integer.parseInt(id);
        //System.out.println("del:"+(Integer) id.get("id"));
        //return  0;
       return adminService.deleteadmin((Integer)id.get("id"));
    }
}
