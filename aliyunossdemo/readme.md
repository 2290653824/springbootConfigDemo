> 关于如何注册阿里云的oss这里不再进行介绍，本文章主要针对对oss有一定的了解的朋友

# 如何在后端搭建oss服务

有两种方案：

## 1.原生版

直接从oss官网copy的代码

```java
package com.zj.aliyunossdemo.method1.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * 本类可以让controller层直接调用本类的静态方法即可，这种方式适合于spring的单体项目
 * 但是这里这样的方式也存在几个问题：其中关于路径、key等信息被写死在了代码中，不利于后期维护
 * 2.只适用于一个单体的项目，不适合于微服务的架构方式
 * 3.如果想要其他微服务调用该服务，我们就应该采取springboot的架构方式来为这个oss来开启一个服务，让其他的微服务
 * 也能够进行调用
 * 4.我们更加常用的方法请看方法二
 */
public class AliyunUtils {
    // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
    private static String endpoint = "https://oss-cn-beijing.aliyuncs.com";
    // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
    private static String accessKeyId = "LTAI5tPcM6iZu2C2F7xnq2Ao";
    private static String accessKeySecret = "AIjy0FJWIaloprQoOn67gp7jrll7Fw";
    private static String bucketName="health2001";




    /**
     * 上传文件
     * @param fileName
     * @param fileUrl
     */
    public static void upload(String fileName,String fileUrl){


// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建PutObjectRequest对象。
// 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, new File(fileUrl));

// 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
// ObjectMetadata metadata = new ObjectMetadata();
// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
// metadata.setObjectAcl(CannedAccessControlList.Private);
// putObjectRequest.setMetadata(metadata);

// 上传文件。
        ossClient.putObject(putObjectRequest);

// 关闭OSSClient。
        ossClient.shutdown();
    }


    /**
     * 上传流式文件
     * @param fileName
     * @param file
     */
    public static void upload(String fileName,byte[] file){


// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建PutObjectRequest对象。
// 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
// 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。

        ossClient.putObject(bucketName,fileName,new ByteArrayInputStream(file));

        ossClient.shutdown();
    }

    /**
     * 删除文件
     * @param fileName
     */
    public static void delete(String fileName){
        // 填写文件完整路径。文件完整路径中不能包含Bucket名称。
        String objectName = fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 删除文件或目录。如果要删除目录，目录必须为空。
        ossClient.deleteObject(bucketName, objectName);

// 关闭OSSClient。
        ossClient.shutdown();
    }



}

```

这里需要注意的是4个字段需要在阿里云上面去获取：

```java
private static String endpoint = "https://oss-cn-beijing.aliyuncs.com"; private static String accessKeyId = "LTAI3tPcM6iZu5C2F2xnq2Ao";
private static String accessKeySecret = "AIjb0FJWIalop0QoOn67dp7jrll7Fw";
private static String bucketName="hlth2001";
```

然后我们就可以在controller调用该类的静态方法来获取相应的服务，主要需要的是这里我们需要传入的是文件的二进制流和文件的名字

该方式的特点：

- 方法简单暴力，适合刚刚接触oss的用户使用。
- 基本就在一个服务模块里，其他模块（不同端口）的服务无法进行调用
- 一些重要的参数写在了类里，高耦合，不利于后期维护

为了解决以上问题，可以采取另一种方式，就是单独创建一个服务，并使用springboot的方式在配置文件中进行重要参数的配置。

## 2.springboot进阶版

1. 创建一个属性配类

```java
package com.zj.aliyunossdemo.method2.utils;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("aliyun.oss")
public class OssProperties {
    private String endpoint;
    private String keyId;
    private String keySecret;
    private String bucketName;
}

```

这里的@ConfigurationProperties可以根据yaml为文件的配置将数据通过set方法配置到属性中，并交由spring进行管理



接下来我们对yaml进行配置：

```java
server:
  port: 9000 # 服务端口

spring:
  profiles:
    active: dev
  application:
# 服务名
    name: service_oss

aliyun:
  oss:
    endpoint: https://oss-cn-beijing.aliyuncs.com
    keyId: LTAI5tPcM6iZuerw2F7xnq2Ao
    keySecret: AIjy0FJWIasfdprQoOn67gp7jrll7Fw
    bucketName: hth2001


```

其实最关键的是上面aliyun.oss的配置，一定先要在阿里云上找到自己对应的参数



我们在写一个`FileService`接口，用于将阿里云的sdk关联实现FileService，这样Controller层可以直接调用

```JAVA
public interface FileService {

    /**
     * 阿里云 oos 文件上传
     *
     * @param inputStream 上传文件的 输入流
     * @param module 上传到 oss 哪个文件目录
     * @param originalFileName  原始文件名
     * @return 返回上传上去之后的 url 地址
     */
    String upload(InputStream inputStream, String module, String originalFileName);
}

```



```java
package com.zj.aliyunossdemo.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zj.aliyunossdemo.method2.utils.OssProperties;
import com.zj.aliyunossdemo.service.FileService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.InputStream;
import org.joda.time.DateTime;
import java.util.UUID;

/**
 * 文件上传服务实现类
 */
@Service
public class FileServiceImpl implements FileService {
    @Resource
    private OssProperties ossProperties;

    /**
     * 阿里云Oss文件上传
     *
     * @param inputStream     上传文件的输入流
     * @param module          上传到oss的文件目录
     * @param orginalFileName 原始文件名
     * @return 返回上传上去之后的url地址
     */
    @Override
    public String upload(InputStream inputStream, String module, String orginalFileName) {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ossProperties.getEndpoint();
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ossProperties.getKeyId();
        String accessKeySecret = ossProperties.getKeySecret();
        String bucketName = ossProperties.getBucketName();

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        String folder = new DateTime().toString("yyyy/MM/dd");
        // 文件名
        String fileName = UUID.randomUUID().toString();
        //文件扩展名
        String fileExtention = orginalFileName.substring(orginalFileName.lastIndexOf("."));
        //最终的路径 类似avatar/2021/12/05/xxxxxxxxx.jpg
        String objectName = module+"/"+folder+"/"+fileName+fileExtention;
        ossClient.putObject(bucketName, objectName, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
        //https://javaclimb-file.oss-cn-beijing.aliyuncs.com/avatar/01.jpeg
        return "https://"+bucketName+"."+endpoint+"/"+objectName;
    }
}

```



上面这个FileServiceImpl的实现就基本上模仿了oss的sdk，这里有一个重要的方法`upload`已经在上面的注释进行了解释。

接下来我们来写controller层：

```java
@Api("阿里云文件管理")
@RestController
@RequestMapping("admin/oss/file")
@Slf4j
public class FileController {
    @Resource
    private FileService fileService;

    @GetMapping("upload")
    public String upload(
            @ApiParam(value="文件",required = true) @RequestParam("file") MultipartFile file,
                    @ApiParam(value="模块",required = true) @RequestParam("module") String module)
    {
        try {
            InputStream inputStream = file.getInputStream();
            String orginalFileName = file.getOriginalFilename();
            String url = fileService.upload(inputStream,module,orginalFileName);
            return url;
        } catch (IOException e) {

            e.printStackTrace();
        }
        return "文件上传失败";
    }
}
```



> 上面的代码中采用knife进行了文档规范，关于swagger的代码可以看看我另一篇文章

这里用到了一个重要的组件MultipartFile，它是由spring提供的一个文件上传组件，非常方面。基本上文件的上传都会使用到它。

关于MultipartFilez注意事项：controller中参数的接收必须使用注解@Requestparam



接下来我们先进行swagger测试：
![在这里插入图片描述](https://img-blog.csdnimg.cn/50e63c9d20354d40af2563c150bf773f.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)



可以看到我们文件上传和模块名字都是正确的，但是返回的结果却是500，我们看看服务端哪儿出错了：


![在这里插入图片描述](https://img-blog.csdnimg.cn/3f16e63378464ddd826db3d181fea717.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)



定位错误：

```java
org.springframework.web.multipart.MultipartException: Current request is not a multipart request
```
# bug解决方案

我在网上查询了问题的方案：

其中找到了问题的关键：

我们对应是要上传文件，所以前端发起的请求因该是一个上传文件的请求，即content-type的类型应该为multipart/form-data，我们看看swagger发出的请求类型是什么：

![在这里插入图片描述](https://img-blog.csdnimg.cn/a14fe56ccc324120b151f7591161a5ad.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)


从这里可以看出swagger发出的请求contentType是application/json的形式。

我们目前并不知道在前端的情况下如何设置这个contenttype，但是我在网上看到了关于用postman调用成功的方法：

![在这里插入图片描述](https://img-blog.csdnimg.cn/4c8cd52ca7fc413fbe6631aedff0cc4b.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)


我们在postman中直接设置file和module，不用设置contentTYpe，这样postman会自动帮助我们进行设置。我们进行运行：

![在这里插入图片描述](https://img-blog.csdnimg.cn/92b0c4a66c4648e58e7814de3b40fbbe.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)



运行成功并成功返回了地址。



在浏览器中可以查看请求信息，但是在postman中怎样进行查看呢？

view=>show postman console

![在这里插入图片描述](https://img-blog.csdnimg.cn/8b5327ab1c9143008c3620adb0966dd6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)



这里可以看出，就是因为请求头中contentType是multipart/form-data而不是application/json，所以我们上传文件成功了。但是我并不知道swagger和postman为什么会有这样的区别。

当然，上面只是我们后端在测试时遇到的问题。在实际项目中，文件的上传还是要看前端怎么设计http的头部信息才能正确上传文件。

以上就是oss以及spring文件上传组件的介绍

参考文章：

[org.springframework.web.multipart.MultipartException: Current request is not a multipart request](https://stackoverflow.com/questions/42013087/multipartexception-current-request-is-not-a-multipart-request)
