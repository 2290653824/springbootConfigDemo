package com.zj.aliyunossdemo.config;


import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 第四步：配置swagger
 * 配置swagger只要适用于后面的api调用，但是本人实践证明
 * 使用swagger调用文件上传组件，可能出现未知错误
 * 建议使用postman进行文件的上传
 */
@Configuration
@EnableKnife4j
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 分组名
                .groupName("WebApi")
                .apiInfo(apiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))   //表示不显示前台得swagger文档
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("edu教育网Api接口文档")
                .description("描述服务端的 api 接口定义")
                .version("1.0")
                .contact(new Contact("zhengjian", "39.103.200.101", "2290653824@qq.com"))
                .build();

    }
}
