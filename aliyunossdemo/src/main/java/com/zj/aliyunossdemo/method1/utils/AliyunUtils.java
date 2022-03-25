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
