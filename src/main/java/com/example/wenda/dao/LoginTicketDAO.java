package com.example.wenda.dao;

import com.example.wenda.modle.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
public interface LoginTicketDAO {

    String Table_Name = "login_ticket";
    String Insert_Fields = " user_id, ticket, expired, status ";
    String Select_Fields = "id," + Insert_Fields;

    @Insert({"insert into ", Table_Name, " (", Insert_Fields, ") ", "values(#{userId},#{ticket},#{expired},#{status})"})
    int addTicket(LoginTicket loginTicket);

    @Select({"select ", Select_Fields, " from ", Table_Name, " where ticket=#{ticket}"})
    LoginTicket selectByTicket(String ticket);

    @Update({"update ", Table_Name, " status=#{status} ", " where ticket=#{ticket}"})
    void UpdateTicketBy(@Param("ticket") String ticket,
                        @Param("status") int status);
}
