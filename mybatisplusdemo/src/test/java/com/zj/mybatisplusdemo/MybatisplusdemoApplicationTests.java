package com.zj.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zj.mybatisplusdemo.mapper.UserMapper;
import com.zj.mybatisplusdemo.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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
        Long integer = userMapper.selectCount(null);
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

    /**
     * 乐观锁测试插入
     */
    @Test
    public void OptimisticTest(){
        User user = new User();
        user.setAge(13);
        user.setEmail("2290653824@qq.com");
        user.setName("张三");
        userMapper.insert(user);
    }

    /**
     * 乐观锁测试修改操作
     * 乐观锁触发原理，先将原数据取出，根据原数据的version来进行乐观锁的判读
     */
    @Test
    public void OptimisticTest2(){
        User user = userMapper.selectById(1);
        user.setAge(600);
        userMapper.updateById(user);

    }

    /**
     * 根据多个id进行查找
     */
    @Test
    public void SelectBatchByIdsTest(){

        List<User> users = userMapper.selectBatchIds(Arrays.asList(1, 2, 3, 4));
        users.forEach(System.out::println);


    }

    /**
     * map封装查询条件
     *
     */
    @Test
    public void selectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","猪八戒");
        map.put("age",600);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);


    }

    /**
     * 分页测试
     */
    @Test
    public void PageTest(){

        Page<User> page = new Page<>(2,3);
        Page<User> userPage = userMapper.selectPage(page, null);
        List<User> records = userPage.getRecords();//取得数据
        records.forEach(System.out::println);

    }

    /**
     * 根据id进行删除
     */
    @Test
    public void deleteByIdTest(){
        userMapper.deleteById(1503960995255169026L);
    }

    /**
     * 根据多个id进行删除
     */
    @Test
    public void deleteBatchByIdsTest(){
        userMapper.deleteBatchIds(Arrays.asList(2,3));
    }

    /**
     * 根据map条件进行删除
     */
    @Test
    public void deleteByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name","猪八戒");
        map.put("age",600);
        userMapper.deleteByMap(map);
    }


    /**
     * wrapper封装条件
     */
      @Test
    public void selectByWrapper(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.ge("age", 20);
        userQueryWrapper.eq("name","张三");
        List<User> users = userMapper.selectList(userQueryWrapper);
        users.forEach(System.out::println);
    }


    /**
     * 获取一个map集合
     */
    @Test
    public void WrapperTest(){
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        List<Map<String, Object>> maps = userMapper.selectMaps(null);
        System.out.println(maps);
    }

    /**
     * 修改条件测试
     */
    @Test
    public void UpdateWrapperTest(){
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.ge("age",20);
        List<User> users = userMapper.selectList(wrapper);
        for(User u:users){
            u.setName("修改名字");
            userMapper.updateById(u);
        }


    }


}
