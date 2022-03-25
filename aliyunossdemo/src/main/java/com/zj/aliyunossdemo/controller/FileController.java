package com.zj.aliyunossdemo.controller;

import com.zj.aliyunossdemo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * 第六步：编写controller，让其他前端可以根据api进行调用，编写swagger文档规范，
 * 方便我们进行测试
 * 阿里云文件管理控制类
 */
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