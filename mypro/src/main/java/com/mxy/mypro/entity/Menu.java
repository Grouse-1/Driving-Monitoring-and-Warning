package com.mxy.mypro.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import java.util.List;

public class Menu {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField(value = "authName")
    private String authName;
    private Integer auth;
    private String path;
    private String parent;
    private String icon;
    private List<Menu> children;

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName;
    }

    public Integer getAuth() {
        return auth;
    }

    public void setAuth(Integer auth) {
        this.auth = auth;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", authName='" + authName + '\'' +
                ", auth=" + auth +
                ", path='" + path + '\'' +
                ", parent='" + parent + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
