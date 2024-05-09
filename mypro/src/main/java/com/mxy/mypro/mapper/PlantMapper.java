package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Family;
import com.mxy.mypro.entity.Plant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PlantMapper extends BaseMapper<Plant> {
    @Select("select * from plantInfo")
    IPage<Plant> selectPlant(Page<Object> objectPage);


    @Select("select * from plantInfo where name = #{name}")
    IPage<Plant> selectByName(Page<Plant> page, @Param("name")String name);

    @Select("SELECT count(*) from plantInfo where name = #{name}")
    int findName(String name);
}
