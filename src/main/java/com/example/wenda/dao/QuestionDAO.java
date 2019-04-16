package com.example.wenda.dao;

import com.example.wenda.modle.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface QuestionDAO {

    String Table_Name = "question";
    String Insert_Fields = " title,content,user_id,created_date,comment_count ";
    String Select_Fields = "id," + Insert_Fields;

    @Insert({"insert into ", Table_Name, "(", Insert_Fields, ") " +
            "values(#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    void addQuestion(Question question);

    //@Select({ "select ", Select_Fields ," from ", Table_Name,"where id=#{id}"})
    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset") int offset,
                                         @Param("limit") int limit);


    @Delete({"delete from", Table_Name, "where id =#{id}"})
    void deleteById(int id);

    @Delete({"truncate table ", Table_Name})
    void deleteAll();


}
