package com.example.wenda.controller;


import com.example.wenda.modle.HostHolder;
import com.example.wenda.sevice.UserService;
import com.example.wenda.utils.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(WendaUtil.class);
    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;

    //用户登录页面
    @RequestMapping(path = {"/login"}, method = RequestMethod.POST)
    public String login(Model model,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam(value = "next", required = false) String next,
                        @RequestParam(value = "remember_me", defaultValue = "false") boolean remember_me,
                        HttpServletResponse response) {
        try {
            Map<String, String> map = userService.login(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                logger.info("mycookie" + cookie.toString());
                //如果带有缓存页面next，那直接跳转到next页面
                if (!StringUtils.isEmptyOrWhitespace(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));
                return "login";
            }
        } catch (Exception e) {
            logger.error("登录发生异常！" + e.getMessage());
            return "login";
        }
    }

    //用户注册页面
    @RequestMapping(path = {"/register"}, method = RequestMethod.POST)
    public String register(Model model,
                           @RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam(value = "next", required = false) String next,
                           HttpServletResponse response) {
        try {
            Map<String, String> map = userService.register(username, password);
            if (map.containsKey("ticket")) {
                Cookie cookie = new Cookie("ticket", map.get("ticket"));
                cookie.setPath("/");
                response.addCookie(cookie);
                if (!StringUtils.isEmptyOrWhitespace(next)) {
                    return "redirect:" + next;
                }
                return "redirect:/";
            } else {
                model.addAttribute("msg", map.get("msg"));

                return "login";
            }
        } catch (Exception e) {
            logger.error("注册发生异常！" + e.getMessage());
            return "login";
        }
    }

    @RequestMapping(path = {"/logout"}, method = RequestMethod.GET)
    public String logout(@CookieValue("ticket") String ticket) {
        userService.logout(ticket);
        return "redirect:/";
    }

    @RequestMapping(path = {"/reglogin"}, method = RequestMethod.GET)
    public String log(Model model, @RequestParam(value = "next", required = false) String next) {
        model.addAttribute("next", next);
        logger.info("######:" + next);
        return "/login";
    }
}
