package com.zj.mybatisplusdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.atomic.AtomicInteger;

@SpringBootApplication
@MapperScan("com.zj.mybatisplusdemo.mapper")
public class MybatisplusdemoApplication {

    public static void main(String[] args) {
        
        SpringApplication.run(MybatisplusdemoApplication.class, args);
    }

}
