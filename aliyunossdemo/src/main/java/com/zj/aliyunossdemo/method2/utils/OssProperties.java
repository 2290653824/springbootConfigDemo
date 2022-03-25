package com.zj.aliyunossdemo.method2.utils;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 第二部：创建一个Properties方面进行yaml配置
 * 这里主要用于yaml文件的配置，方面解耦
 */
@Data
@Component
@ConfigurationProperties("aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
}
