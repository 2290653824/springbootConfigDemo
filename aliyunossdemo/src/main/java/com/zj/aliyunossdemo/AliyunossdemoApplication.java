package com.zj.aliyunossdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 第七步：编写启动类
 * 这里需要排除数据库的DataSource自动加载服务，因为我们oss不来就不需要使用到数据库，
 * 只是需要向远端oss提交文件而已
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class AliyunossdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AliyunossdemoApplication.class, args);
    }

}
