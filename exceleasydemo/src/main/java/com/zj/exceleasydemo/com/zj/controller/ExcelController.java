package com.zj.exceleasydemo.com.zj.controller;

import com.zj.exceleasydemo.com.zj.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Result;
import java.io.InputStream;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/post")
    public String excelTest(@RequestParam("file") MultipartFile multipartFile){
        try{
            InputStream inputStream = multipartFile.getInputStream();
            excelService.BatchPost(inputStream);

        }catch(Exception e){
            e.printStackTrace();
            return "文件读取失败";
        }
        return "文件上传成功";

    }
}
