package com.mxy.mypro.controller;

import cn.hutool.core.util.StrUtil;
import com.mxy.mypro.entity.*;
import com.mxy.mypro.mapper.AdminMapper;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.FamilyService;
import com.mxy.mypro.service.SellerService;
import com.mxy.mypro.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;


@RestController
public class loginController {
    private final AdminService adminService;
    private final FamilyService familyService;
    private final SellerService sellerService;
    @Autowired
    public loginController(AdminService adminService, FamilyService familyService,SellerService sellerService) {
        this.adminService = adminService;
        this.familyService = familyService;
        this.sellerService = sellerService;
    }
    @PostMapping("/login")
    public Result vuelogin(@RequestBody Role role){
        System.out.println("username:"+role.getName()+",password:"+role.getPassword()+",role:"+role.getRole());
        if(StrUtil.isBlank(role.getName()) || StrUtil.isBlank(role.getPassword())){
            return Result.error("数据输入不合法");
        }
        List list = new ArrayList();
        if(role.getRole().equals("admin")){
            Admin admin = new Admin();
            admin.setName(role.getName());
            admin.setPassword(role.getPassword());
            list = adminService.login(admin);
        }else if(role.getRole().equals("family")){
            Family family = new Family();
            family.setName(role.getName());
            family.setPassword(role.getPassword());
            list = familyService.login(family);
        }else if(role.getRole().equals("seller")){
            seller seller1 = new seller();
            seller1.setName(role.getName());
            seller1.setPassword(role.getPassword());
            list = sellerService.login(seller1);
        }else {
            return Result.error("用户名或密码错误");
        }
        System.out.println("list123:"+list);
        if(list == null || list.isEmpty()) {
            return Result.error("用户名或密码错误");
        }
        String mytoken = JwtUtils.createToken(role.getName()+":"+role.getRole());
        return Result.ok("提交成功").data("token", mytoken);
    }
    @PostMapping("/info")
    public Result info(String token){
        String role = JwtUtils.getClaimsByToken(token).getSubject();
        System.out.println(",role:"+role);
        return Result.ok("验证成功").data("username", role);
    }
}
