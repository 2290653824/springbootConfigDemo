package com.zj.swaggerdemo.config;

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

/**
 * 关于swagger的配置类，主要配置与swagger信息相关的内容
 */
@Configuration
    @EnableKnife4j
    @EnableSwagger2
    public class SwaggerConfig {

    /**
     * 在这里配置扫面的路径
     * @return
     */
    @Bean
        public Docket getDocket(){
            return new Docket(DocumentationType.SWAGGER_2).apiInfo(getApiInfo())
                    .select().apis(RequestHandlerSelectors.basePackage("com.zj.swaggerdemo")).build();
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
        
        
    //以下是做项目时遇到的另一种写法
        
//     @Bean
//     public Docket webApiConfig() {
//         return new Docket(DocumentationType.SWAGGER_2)
//                 // 分组名
//                 .groupName("WebApi")
//                 .apiInfo(apiInfo())
//                 .select()
//                 .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
//                 .paths(Predicates.not(PathSelectors.regex("/error.*")))
//                 .build();
//     }

//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//                 .title("Java攀登网API文档")
//                 .description("描述服务端的 api 接口定义")
//                 .version("1.0")
//                 .contact(new Contact("javaclimb", "http://www.javaclimb.com", "xxxx.@xxx.com"))
//                 .build();
//     }
}
