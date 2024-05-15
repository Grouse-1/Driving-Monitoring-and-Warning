package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.mypro.entity.EmotionData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface EmotionMapper extends BaseMapper<EmotionData> {
    @Select("select location_id from elderly where id = 1")
    int GetElderLocation();

}
