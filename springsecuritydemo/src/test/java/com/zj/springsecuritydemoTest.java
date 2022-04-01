package com.zj;


import com.zj.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class springsecuritydemoTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test1(){
        System.out.println(userMapper.selectList(null));
    }
}
