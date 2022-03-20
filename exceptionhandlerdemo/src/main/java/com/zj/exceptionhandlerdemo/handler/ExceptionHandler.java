package com.zj.exceptionhandlerdemo.handler;

import com.zj.exceptionhandlerdemo.entity.Result;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 用于controller层异常捕获
 */
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public Result Exhandler(Exception e){
        e.printStackTrace();
        return new Result(false,"404","失败",null);
    }
}
