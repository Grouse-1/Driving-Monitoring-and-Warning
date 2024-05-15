package com.mxy.mypro.mapper;

import com.mxy.mypro.entity.Food;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdviseMapper {

    @Select("SELECT * FROM goods WHERE gtype = #{tmp} ORDER BY RAND() LIMIT 2;")
    List<Food> getFood(String tmp);
    // 其他数据访问方法...
}
