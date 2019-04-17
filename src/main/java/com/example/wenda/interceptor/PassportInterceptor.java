package com.example.wenda.interceptor;

import com.example.wenda.dao.LoginTicketDAO;
import com.example.wenda.dao.UserDAO;
import com.example.wenda.modle.HostHolder;
import com.example.wenda.modle.LoginTicket;
import com.example.wenda.modle.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    LoginTicketDAO loginTicketDAO;
    @Autowired
    UserDAO userDAO;
    @Autowired
    HostHolder hostHolder;

    //进入拦截器之前先执行，如果返回false，请求结束
    //用于判断用户身份
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {

        String ticket = null;
        if (httpServletRequest.getCookies() != null) {
            for (Cookie cookie : httpServletRequest.getCookies()) {

                if (cookie.getName().equals("ticket")) {
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket != null) {
            //如果ticket是无效的，返回true，返回false接下来的就不会执行了
            LoginTicket loginTicket = loginTicketDAO.selectByTicket(ticket);
            if (loginTicket == null || loginTicket.getExpired().before(new Date()) || loginTicket.getStatus() != 0) {
                return true;
            }
            User user = userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUserThreadLocal(user);
            //System.out.println("#########"+ user.toString());
        }

        return true;
    }

    //handler处理完成之后调用此回调函数
    //用于渲染返回的页面
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //与Model关联template相似，将user变量发送到模板页面
        if (modelAndView != null) {
            modelAndView.addObject("user", hostHolder.getUer());
        }
    }

    //所有页面都渲染完成调用此回调函数,清除所有数据
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
