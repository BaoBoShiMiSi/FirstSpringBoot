package com.example.wenda;

import com.example.wenda.dao.QuestionDAO;
import com.example.wenda.dao.UserDAO;
import com.example.wenda.modle.Question;
import com.example.wenda.modle.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Random;

//@MapperScan("com.example.wenda.dao")
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDataBaseTests {

    @Resource
    UserDAO userDao;

    @Resource
    QuestionDAO questionDAO;

    @Test
    public void initDataBase() {

        Random random = new Random();
        userDao.deleteAll();
        questionDAO.deleteAll();
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            user.setName(String.format("User%d", i + 1));
            user.setPassword(String.format(""));
            user.setSalt("");
            userDao.addUser(user);

            user.setId(i + 1);
            user.setPassword("xxx");
            userDao.updatePassword(user);

            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000 * 3600 * i);
            question.setCreatedDate(date);
            question.setUserId(i + 1);
            question.setTitle(String.format("Title%d", i + 1));
            question.setContent(String.format("bbbb%d", i + 1));
            questionDAO.addQuestion(question);
        }
        List<Question> QList = questionDAO.selectLatestQuestions(0, 0, 10);
        for (Question qList : QList) {
            System.out.print(qList.getUserId());
        }
        //userDao.InsertByName("YXT","1234556","salt","headUrl");
    }
}
