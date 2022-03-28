package com.zj.mybatisplusdemo.paginationTest;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.mybatisplusdemo.pojo.User;

/**
 * 怎么进行分页
 */
public class ControllerTest {

    public String test1(int page,int limit){
        //这样就设置了分页对象，通长在controller设置后，传递给service
        Page<User> userPage = new Page<>(page, limit);

        //然后在service层中调用basemapper的selectPage即可，讲page带对象传入即可

        return "page";
    }
}
