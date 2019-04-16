package com.example.wenda.controller;

import com.example.wenda.modle.modelTest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

//@Controller
public class IndexController {

    @RequestMapping(path = {"/", "/index"}, method = {RequestMethod.GET})
    @ResponseBody
    public String index(HttpSession httpSession) {
        return "Hello,New Spring Boot" + httpSession.getAttribute("msg");
    }

    @RequestMapping(path = {"/profile/{groupId}/{userId}"})
    @ResponseBody
    public String profile(@PathVariable("groupId") String groupId,
                          @PathVariable("userId") int userId,
                          @RequestParam(value = "type", defaultValue = "type_value", required = false) int type,
                          @RequestParam(value = "key", defaultValue = "key_value", required = false) String key) {

        return String.format("Profile Page of groupId=%s / userId=%d, type=%d, key=%s", groupId, userId, type, key);
    }

    //request与response封装内容显示
    @RequestMapping(path = {"/request"}, method = {RequestMethod.GET})
    @ResponseBody
    public String request(Model model, HttpServletRequest request,
                          HttpServletResponse response,
                          HttpSession session) {
        StringBuilder sb = new StringBuilder();
        //request的内容
        sb.append(request.getMethod() + "<br>");
        sb.append(request.getQueryString() + "<br>");
        sb.append(request.getRemoteAddr() + "<br>");
        sb.append(request.getRequestURL() + "<br>");

        //header中的内容
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            sb.append(headerName + "  :" + request.getHeader(headerName) + "<br>");
            System.out.println();
        }
        //cookie内容
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                sb.append("Cookie:" + cookie.getName() + " value:" + cookie.getValue());
            }
        }
        //添加response返回的内容
        response.addHeader("New Header", "header_string");
        response.addCookie(new Cookie("new_cookie", "cookie_string"));
        return sb.toString();
    }

    //重定向跳转，用session传值到跳转至页面
    @RequestMapping(path = {"/redirect/{code}"})
    public String redirect(@PathVariable("code") int code,
                           HttpSession httpSession) {

        httpSession.setAttribute("msg", "jump from redirect");

        return "redirect:/";
    }

    //重定向跳转，用session传值至重定向页面
    @RequestMapping(path = {"/Redirect/{code}"})
    public RedirectView Redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        //传值session
        httpSession.setAttribute("msg", "jump from redirect");
        RedirectView redv = new RedirectView("/", true);
        if (code == 301) {
            redv.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }

        return redv;
    }

    @RequestMapping(path = {"/admin"}, method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key) throws IllegalAccessException {
        if ("admin".equals(key)) {
            return "hello,admin";
        }

        throw new IllegalAccessException("参数不正确！");
    }

    //异常统一处理，上面的IllegalAccessException（）会调用此方法处理异常
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {

        return "error:" + e.getMessage();
    }

    //thymeleaf测试
    @RequestMapping(path = {"/thymeleaf_zhushi"})
    //@ResponseBody
    public String tmplate(Map<String, String> map, Model model) {

        //变量测试
        map.put("value1", "测试变量value1");
        model.addAttribute("value2", "测试变量value2");

        //字符串转义测试
        String htmlContent = "<p style='color:red'> 红色文字</p>";
        model.addAttribute("htmlContent", htmlContent);

        //对象测试
        modelTest test = new modelTest("YXT", 25, "hello");
        boolean testBoolean = true;
        model.addAttribute("modelTest", test);
        //thymeleaf if语句测试
        model.addAttribute("testBoolean", testBoolean);

        //thymeleaf 循环遍历测试
        List<modelTest> testList = new ArrayList<>();
        testList.add(new modelTest("YXT", 25, "大写名"));
        testList.add(new modelTest("yxt", 25, "小写名"));
        testList.add(new modelTest("bsyangxt", 25, "英文名"));
        model.addAttribute("testList", testList);

        //循环map
        Map<String, String> testMap = new HashMap<>();
        testMap.put("YXT", "大写名");
        testMap.put("yxt", "小写名");
        testMap.put("bsyangxt", "英文名");
        model.addAttribute("testMap", testMap);

        return "home";
    }
}
