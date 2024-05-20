package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.mypro.entity.EmotionData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmotionMapper extends BaseMapper<EmotionData> {
    @Select("select location_id from elderly where id = 1")
    int GetElderLocation();


    @Select("SELECT COUNT(*) FROM outstate WHERE Ltime LIKE #{timeFilter} and elderid = #{elderid}")
    int getCount(@Param("timeFilter")String timeFilter, @Param("elderid")Integer elderid);

    @Select("select name from location where id = (select location from outstate where emotion = 'happy' and elderid = #{elderid} order by count) ;")
    List<String> getHappyLocation(String elderid);

    @Select("SELECT SUM(count) FROM outstate where location = 1 and elderid = #{elderid} and emotion = 'happy'")
    Integer getHappyMCount(String elderid);

    @Select("SELECT SUM(count) FROM outstate where location = 2 and elderid = #{elderid} and emotion = 'happy'")
    Integer getHappyPCount(String elderid);
}
