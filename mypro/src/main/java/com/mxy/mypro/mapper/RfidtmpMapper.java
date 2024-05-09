package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.mypro.entity.Rfidtmp;
import org.apache.ibatis.annotations.*;

@Mapper
public interface RfidtmpMapper extends BaseMapper<Rfidtmp> {
    @Select("SELECT COUNT(*) FROM rfidtemp")
    int selectcount();

    @Insert("INSERT into rfidtemp (uid) values (#{uid})")
    int Insert(Rfidtmp rfidtmp);

    @Update("update rfidtemp set uid = #{uid} where id = #{id}")
    int UpdateById(Rfidtmp rfidtmp);

    @Select("select id from rfidtemp")
    String getID();

    @Update("update elderly set location_id = #{locationID} where rfidtag = #{rfidID}")
    void updateLocation(@Param("rfidID")String rfidID, @Param("locationID")int locationID);
}
