package com.etoak;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.etoak.mapper")
public class VideoServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(VideoServiceApp.class,args);
    }
}
