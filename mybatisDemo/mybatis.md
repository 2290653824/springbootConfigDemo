## 快速搭建项目

这里选择mybatis和mysql驱动包：

![image-20220225111333870](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20220225111333870.png)

这时项目的pom会出现：

![image-20220225111504780](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20220225111504780.png)

## springboot配置整合mybatis

```yaml
#数据源配置
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/mybatisDemo?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true
    username: root
    password: 12345678
    driver-class-name: com.mysql.jdbc.Driver

#配置mybatis映射配置文件、核心配置文件的位置以及开启驼峰命名
mybatis:
  mapper-locations: classpath:com/zj/mybatis/mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true
  config-location: classpath:mybatis-config.xml

#tomcat端口号
server:
  port: 8080
```



创建一个User实体类

```java
package com.zj.mybatis.pojo;

public class User {
    
    private String name;
    private int age;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public User() {
    }
}
```

创建UserMapper接口，并定义一个查询方法：

```java
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
```

创建一个xml映射配置文件：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="com.zj.mybatis.mapper.UserMapper" xmlns="http://mybatis.org/schema/mybatis-mapper">
    <select id="findById" resultType="com.zj.mybatis.pojo.User" parameterType="int">
        select * from t_user where id=#{id} 
    </select>
    
</mapper>
```

在测试里面进行测试：

```java
package com.zj.mybatis.mapper;

import com.zj.mybatis.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test1(){
        User user = userMapper.findById(1);
        System.out.println(user);

    }

}
```

在启动类中注意扫描mapper文件：

```java
package com.zj.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zj.mybatis.mapper")
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
```

最终测试成功：

![image-20220225114445898](C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20220225114445898.png)

