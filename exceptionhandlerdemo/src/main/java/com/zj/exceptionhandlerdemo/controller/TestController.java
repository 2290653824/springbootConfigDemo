package com.zj.exceptionhandlerdemo.controller;

import com.zj.exceptionhandlerdemo.entity.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {


    @RequestMapping("ex")
    public Result getEx(){
        //这里发生异常，我们没用try catch进行捕获，而是使用spring的异常捕获框架
        int i=1/0;
        return new Result(true,"200","成功",null);
    }
}
