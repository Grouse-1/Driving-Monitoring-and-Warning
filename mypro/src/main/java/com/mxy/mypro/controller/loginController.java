package com.mxy.mypro.controller;

import cn.hutool.core.util.StrUtil;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.Result;
import com.mxy.mypro.mapper.AdminMapper;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController
public class loginController {
    private final AdminService adminService;
    @Autowired
    public loginController(AdminService adminService) {
        this.adminService = adminService;
    }
    @PostMapping("/login")
    public Result vuelogin(@RequestBody Admin admin){
        System.out.println(admin.getName()+admin.getPassword());
        if(StrUtil.isBlank(admin.getName()) || StrUtil.isBlank(admin.getPassword())){
            return Result.error("数据输入不合法");
        }
        List<Admin> list = adminService.login(admin);
        if(list == null || list.isEmpty()) {
            return Result.error("用户名或密码错误");
        } else {
            System.out.println(list);
            String token = JwtUtils.createToken(admin.getName());
            return Result.ok("提交成功").data("token", token);
        }

    }
    @PostMapping("/info")
    public Result info(String token){
        String username = JwtUtils.getClaimsByToken(token).getSubject();
        System.out.println(",username:"+username);
        return Result.ok("验证成功").data("username", username);
    }
}
