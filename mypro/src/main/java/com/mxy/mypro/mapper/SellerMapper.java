package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.seller;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface SellerMapper extends BaseMapper<seller> {
    @Insert("insert into seller (name, password, auth, shopname, phone) values (#{name},#{password},#{auth},#{shopname}, #{phone})")
    int Insert(seller mseller);

    @Select("SELECT * FROM seller WHERE name = #{name}")
    IPage<seller> selectByName(Page<Object> objectPage,@Param("name")String name);

    @Select("select id from seller where name = #{seller}")
    int getID(String seller);
}
