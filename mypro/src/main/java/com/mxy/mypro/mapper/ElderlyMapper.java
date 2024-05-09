package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.elderly;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ElderlyMapper extends BaseMapper<elderly> {
    @Insert("insert into elderly (name, password, sex, age, balance, rfidtag, location_id, auth ) values (#{name},#{password},#{sex}, #{age}, #{balance}, #{rfidtag}, #{locationId}, #{auth})")
    int Insert(elderly eld);

    @Select("SELECT * FROM elderly WHERE name = #{name}")
    IPage<elderly> selectByName(Page<Object> objectPage, @Param("name") String name);

    @Select("SELECT * FROM elderly WHERE name = #{name}")
    List<elderly> getByname(String name);

    @Select("SELECT COUNT(*) FROM elderly WHERE location_id = (SELECT id FROM location WHERE name = #{location})")
    int getNum(String location);
}
