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

//     /**
//      * 对C端用户的接口文档
//      *
//      * @return
//      */
//     @Bean
//     public Docket webApiDoc() {
//         return new Docket(DocumentationType.OAS_30)
//                 .groupName("用户端接口文档")
//                 .pathMapping("/")
//                 //定义是否开启Swagger，false是关闭，可以通过变量去控制，线上关闭
//                 .enable(true)
//                 //配置文档的元信息
//                 .apiInfo(apiInfo())
//                 .select()
//                 .apis(RequestHandlerSelectors.basePackage("com.java3y.austin.web.controller"))
//                 //正则匹配请求路径，并分配到当前项目组
//                 //.paths(PathSelectors.ant("/api/**"))
//                 .build();
//     }
//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//                 .title("austin平台")
//                 .description("消息推送接口接口文档")
//                 .contact(new Contact("3y", "http://gitee.com/zhongfucheng/austin", "403686131@qq.com"))
//                 .version("v1.0")
//                 .build();
//     }


}
