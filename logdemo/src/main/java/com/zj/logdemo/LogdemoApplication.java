package com.zj.logdemo;

import com.zj.logdemo.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@Slf4j
@SpringBootApplication
public class LogdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogdemoApplication.class, args);

        //下面是方式三的演示
        try{
            int i=1/0;
        }catch (Exception e){
            log.error(ExceptionUtils.getMessage(e));
        }
    }

}
