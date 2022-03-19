package com.zj.swaggerdemo.controller;

import com.zj.swaggerdemo.pojo.User;
import com.zj.swaggerdemo.service.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/User")
@Api("用户层")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @ApiOperation("获取一个User对象")
    @RequestMapping("/findOne")
    public User findOne(){
        return userService.findOne();
    }


}
