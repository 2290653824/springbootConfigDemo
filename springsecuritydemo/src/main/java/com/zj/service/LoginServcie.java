package com.zj.service;

import com.zj.entity.ResponseResult;
import com.zj.entity.User;


public interface LoginServcie {

    ResponseResult login(User user);

    ResponseResult logout();
}
