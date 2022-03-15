package com.zj.mybatisplusdemo;

import com.zj.mybatisplusdemo.mapper.UserMapper;
import com.zj.mybatisplusdemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisplusdemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
    }

    /**
     * 简单操作之查找操作
     */
    @Test
    void selectTest(){

        //null表示不加条件约束
        Integer integer = userMapper.selectCount(null);
        System.out.println(integer);
    }

    /**
     * 简单操作之插入操纵
     */
    @Test
    void insertTest(){
        User user = new User();
        user.setAge(13);
        user.setEmail("2290653824@qq.com");
        user.setName("张三");
        userMapper.insert(user);
    }

    /**
     * 简单测试之更新操作
     */
    @Test
    void updateTest(){
        User user = new User();
        user.setId(1L);
        user.setName("猪八戒");
        user.setAge(190);
        userMapper.updateById(user);

    }

}
