package com.mxy.mypro.controller;

import com.mxy.mypro.entity.User;
import com.mxy.mypro.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class userController {

    private final UserMapper userMapper;
    @Autowired
    public userController(UserMapper userMapper){
        this.userMapper = userMapper;
    }


    @GetMapping("/user")
    public List query(){
        List<User> list = userMapper.selectList(null);
        System.out.println(list);
        return list;

    }
    @PostMapping("/useradd")
    public String save(User user){
        int count = userMapper.insert(user);
        return "insert finish :"+count;
    }
}
