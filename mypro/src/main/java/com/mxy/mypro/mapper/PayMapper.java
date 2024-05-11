package com.mxy.mypro.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface PayMapper {

    @Insert("insert into transaction (elderid, shopid, summary, tdate, Tkey) values (#{elderid}, #{shopid}, #{summary},#{CurrentTime},#{key})")
    int addTrans(@Param("elderid")int elderid, @Param("shopid")int shopid, @Param("summary")float summary, @Param("CurrentTime")String CurrentTime, @Param("key")String key);

    @Select("select id from transaction where Tkey = #{key}")
    int TransId(String key);

    @Insert("insert into transdetail (transid, goodsid, quantity, money) values (#{transid},(select id from goods where name = #{goodsname}), #{quantity}, #{money})")
    int addTransDetail(@Param("transid")int transid, @Param("goodsname")String goodsname, @Param("quantity")float quantity,  @Param("money")float money);

    @Select("select count(*) from transaction where shopid = #{shopid} and tdate like #{currentDay}")
    int getTransCount(@Param("shopid")String shopid, @Param("currentDay")String currentDay);

    @Select("select sum(summary) from transaction where shopid = #{shopid} and tdate like #{currentDay}")
    Float gettransMoney(@Param("shopid")String shopid,  @Param("currentDay")String currentDay);
}
