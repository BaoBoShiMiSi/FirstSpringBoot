package com.example.wenda.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Aspect
@Component
public class LogAspect {

    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(LogAspect.class);

    //在调用所有Controller方法之前调用beforeMethod()
    //joinPoint为切点
    @Before("execution(* com.example.wenda.controller.IndexController.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder sb = new StringBuilder();
        for (Object arg : joinPoint.getArgs()) {
            sb.append("arg:" + arg.toString() + "|");
        }
        logger.info("before method" + sb.toString() + "\n" + new Date());

    }

    ////在调用所有Controller方法之后调用afterMethod()
    @After("execution(* com.example.wenda.controller.IndexController.*(..))")
    public void afterMethod() {

        logger.info("after method" + new Date());
    }
}
