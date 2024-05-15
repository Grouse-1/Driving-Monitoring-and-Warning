package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.mypro.entity.TravelRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TravelRecordMapper extends BaseMapper<TravelRecord> {

    @Select("SELECT "
            + "t.id, "
            + "e.name, "
            + "l.name AS location, "
            + "t.Tdate "
            + "FROM travel t "
            + "JOIN elderly e ON t.elderid = e.id "
            + "JOIN location l ON t.locationid = l.id")
    List<TravelRecord> getTravelRecord();
}
