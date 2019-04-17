package com.example.wenda.modle;

import org.springframework.stereotype.Component;

@Component
public class HostHolder {
    //线程本地变量，为每一个线程分配一个变量副本，每个线程可以访问自己内部的副本变量
    //JDK建议ThreadLocal定义为private static，这样ThreadLocal的弱引用问题则不存在了
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public User getUer() {
        return userThreadLocal.get();
    }

    public void setUserThreadLocal(User user) {
        userThreadLocal.set(user);
    }

    public void clear() {
        userThreadLocal.remove();
    }
}
