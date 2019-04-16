package com.example.wenda.sevice;

import com.example.wenda.dao.LoginTicketDAO;
import com.example.wenda.dao.UserDAO;
import com.example.wenda.modle.LoginTicket;
import com.example.wenda.modle.User;
import com.example.wenda.utils.WendaUtil;
import groovyjarjarantlr.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    //获取user对象
    public User getUser(int id) {
        return userDAO.selectById(id);
    }

    //用户登录
    public Map<String, String> login(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (org.thymeleaf.util.StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (org.thymeleaf.util.StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDAO.selectByNmae(username);
        if (user == null) {
            map.put("msg", "该用户不存在！");
            return map;
        }
        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "用户密码错误！");
        }
        String ticket = addLoginticket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    //用户注册
    public Map<String, String> register(String username, String password) {
        Map<String, String> map = new HashMap<>();
        if (org.thymeleaf.util.StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空！");
            return map;
        }
        if (org.thymeleaf.util.StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空！");
            return map;
        }
        User user = userDAO.selectByNmae(username);
        if (user != null) {
            map.put("msg", "该用户已存在！");
            return map;
        }
        user = new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDAO.addUser(user);
        String ticket = addLoginticket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    //添加ticket到数据库表的方法
    private String addLoginticket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date now = new Date();
        now.setTime(3600 * 24 * 100 + now.getTime());
        loginTicket.setExpired(now);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(loginTicket);

        return loginTicket.getTicket();
    }
}
