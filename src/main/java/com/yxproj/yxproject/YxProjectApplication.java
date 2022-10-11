package com.yxproj.yxproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yxproj.yxproject.mapper")
public class YxProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(YxProjectApplication.class, args);
        System.out.println("go  on ");
    }

}
