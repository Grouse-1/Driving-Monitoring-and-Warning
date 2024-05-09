package com.mxy.mypro.controller;

import com.mxy.mypro.entity.Menu;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3001")
public class menuController {
    private final MenuService menuService;
    @Autowired
    public menuController(MenuService menuService) {
        this.menuService = menuService;
    }
    @GetMapping("/menu")
    public List<Menu> getMenu() {
        List<Menu> menuList = menuService.getAllMenu();
        System.out.println(menuList);
        return menuList;
    }
}
