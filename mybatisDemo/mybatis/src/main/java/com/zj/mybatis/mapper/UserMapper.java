package com.zj.mybatis.mapper;


import com.zj.mybatis.pojo.User;

public interface UserMapper {

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    User findById(int id);

}
