package com.zj.swaggerdemo.service;

import com.zj.swaggerdemo.pojo.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl {

    public User findOne(){
        return new User(1,"张三",new Date());
    }
}
