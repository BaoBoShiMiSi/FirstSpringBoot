package com.example.wenda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.Scanner;

@SpringBootApplication
public class WendaApplication extends WebMvcConfigurationSupport {

    public static void main(String[] args) {
        SpringApplication.run(WendaApplication.class, args);

    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/")//static/")
                .addResourceLocations("classpath:/");//resources/");
        super.addResourceHandlers(registry);
    }


}
