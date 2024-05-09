package com.mxy.mypro.service;

import com.mxy.mypro.entity.Menu;
import com.mxy.mypro.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {
    private final MenuMapper menumapper;
    @Autowired
    public MenuService(MenuMapper menumapper) {
        this.menumapper = menumapper;
    }
    public List<Menu> getAllMenu() {

        List<Menu> menulist = menumapper.selectMenu();
        return menulist;
    }
}
