package com.zj.aliyunossdemo.service;

import java.io.InputStream;

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
