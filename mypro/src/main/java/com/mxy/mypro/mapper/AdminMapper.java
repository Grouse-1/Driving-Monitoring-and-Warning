package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Admin;
import org.apache.ibatis.annotations.*;

@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Insert("insert into admin (name, password, auth) values (#{name},#{password},#{auth})")
    int Insert(Admin admin);

    @Select("SELECT * FROM admin WHERE name = #{name}")
    IPage<Admin> selectByName(Page<Object> objectPage, @Param("name")String name);
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
