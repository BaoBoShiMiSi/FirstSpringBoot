package com.example.wenda.dao;

import com.example.wenda.modle.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserDAO {

    String Table_Name = "user";
    String Insert_Fields = " name, password, salt,head_url ";
    String Select_Fields = "id," + Insert_Fields;

    @Insert({"insert into ", Table_Name, "(", Insert_Fields, ") " +
            "values(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select ", Select_Fields, " from ", Table_Name, "where id=#{id}"})
    User selectById(int id);

    @Select({"select ", Select_Fields, " from ", Table_Name, "where name=#{name}"})
    User selectByNmae(String name);

    @Update({"update ", Table_Name, " set password = #{password} where id=#{id}"})
    int updatePassword(User user);

    @Delete({"delete from", Table_Name, "where id =#{id}"})
    void deleteById(int id);

    @Delete({"truncate table ", Table_Name})
    void deleteAll();

    void InsertByName(@Param("name") String name,
                      @Param("password") String password,
                      @Param("salt") String salt,
                      @Param("headUrl") String headUrl);

}
