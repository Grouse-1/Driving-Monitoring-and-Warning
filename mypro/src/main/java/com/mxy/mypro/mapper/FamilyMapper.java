package com.mxy.mypro.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mxy.mypro.entity.Family;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FamilyMapper {
    @Select("select count(1) from family")
    public int count();

//    @Select("select name from elderly where id = #{elderly_id}")
//    List<Family> selectElderly(int elderly_id);
//
//    @Select("select * from family")
//    @Results({
//            @Result(column = "id", property = "id"),
//            @Result(column = "name", property = "name"),
//            @Result(column = "password", property = "password"),
//            @Result(column = "relationship", property = "relationship"),
//            @Result(column = "auth", property = "auth"),
//            @Result(column = "elderly_id", property = "elderlyId"),
//            @Result(column = "e-mail", property = "e-mail"),
//            @Result(column = "phone", property = "phone"),
//            @Result(column = "elderly_id", property = "elderly", javaType = List.class,
//                    many = @Many(select = "selectElderly")),
//    })
//    List<Family> selectFamily();
//@Select("SELECT f.*, e.name FROM family f JOIN elderly e ON f.elderly_id = e.id WHERE u.name LIKE #{name}")
//IPage<Map<String, Object>> selectUsersWithProfile(Page page, @Param("age") int age, @Param("name") String name);
    @Select("SELECT f.*, e.name as elderly FROM family f JOIN elderly e ON f.elderly_id = e.id")
    IPage<Family> selectFamily(Page<Family> page);

    @Delete("DELETE FROM family WHERE id = #{id}")
    int delete(int id);

    @Insert("INSERT into family(name, password, relationship, auth, email, phone, elderly_id) " +
            "values (#{name}, #{password}, #{relationship}, #{auth}, #{email}, #{phone}," +
            "(SELECT id from elderly where name = #{elderly}))")
    int Insert(Family mfamily);

    @Update("UPDATE family f JOIN elderly e ON f.elderly_id = e.id " +
            "SET f.name = #{name}, f.password = #{password}, f.auth = #{auth}, f.email = #{email}, f.phone = #{phone}," +
            "f.elderly_id = (SELECT id from elderly where name = #{elderly}) where f.id = #{id}")
    int editfamily(Family mfamily);

    @Select("SELECT * FROM family where name = #{name}")
    List<Family> selectList(String name);

    @Select("SELECT f.*, e.name as elderly FROM family f JOIN elderly e ON f.elderly_id = e.id WHERE f.name = #{name}")
    IPage<Family> selectByName(Page<Family> page, @Param("name")String name);

    @Select("SELECT count(*) from elderly where name = #{elderly}")
    int findElderly(String elderly);
}
