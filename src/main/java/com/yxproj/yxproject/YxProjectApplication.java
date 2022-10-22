package com.yxproj.yxproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.yxproj.yxproject.mapper")
@EnableTransactionManagement
public class YxProjectApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(YxProjectApplication.class, args);
    }

}
