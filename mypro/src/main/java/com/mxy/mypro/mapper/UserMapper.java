package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mxy.mypro.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Insert("insert into user values(#{id},#{username},#{password},#{register_date},#{key}})")
//    int add(User user);
//    @Update("update user set username=#{username},password=#{password},register_date=#{register_date},key=#{key}} where id=#{id}")
//    int update(User user);
//    @Delete("delete fromuser whereid=#{id}")
//    int delete(int id);
//    @Select("select * from user where id=#{id}")
//    User findById(int id);
//    @Select("select * from user")
//    List<User> getAll();
}
