package com.zj.rabbitmqspringboot.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableKnife4j
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket getDocket(){
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
                .select().apis(RequestHandlerSelectors.basePackage("com.zj.rabbitmqspringboot.controller")).build();
    }

    @Bean
    public ApiInfo getApiInfo(){
        return new ApiInfo(
                "springboot电商平台接口文档",
                "前后端分离项目api文档",
                "1.0",
                "第一组",
                new Contact("郑剑","39.103.200.101","2290653824@qq.com"),
                "通行证",
                "通行证url",
                new ArrayList<>()

        );
    }
}