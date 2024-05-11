package com.mxy.mypro.mapper;

import com.mxy.mypro.entity.BuyData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BuyDataMapper {

    @Select("SELECT " +
            "    t.tdate, " +
            "    g.name, " +
            "    td.quantity, " +
            "    g.price, " +
            "    td.money, " +
            "    s.shopname " +
            "FROM " +
            "    `transaction` t " +
            "JOIN " +
            "    `transdetail` td ON t.id = td.transid " +
            "JOIN " +
            "    `goods` g ON td.goodsid = g.id " +
            "JOIN " +
            "    `seller` s ON t.shopid = s.id " +
            "WHERE " +
            "    t.elderid = #{elderId}")
    List<BuyData> getData(int elderid);


}
