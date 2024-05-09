package com.mxy.mypro.controller;

import org.springframework.web.bind.annotation.*;

//标记为控制器
@RestController
public class helloController {
    //浏览器发起get请求
    @RequestMapping(value = "/hello1", method = RequestMethod.POST)
    public String hello1(@RequestParam(value = "name", required = false)String name){
        return "hello world11"+name;
    }

}
