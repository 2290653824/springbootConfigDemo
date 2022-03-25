一款由阿里巴巴开源的java操作excel组件

## 步骤一：导入相关依赖

```xml
<dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>easyexcel</artifactId>
            <version>3.0.5</version>
</dependency>
```

## 步骤二：获取要导入的表的表头信息

![在这里插入图片描述](https://img-blog.csdnimg.cn/74b7bb41a51d4818b9cb661e39abc6a6.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_10,color_FFFFFF,t_70,g_se,x_16)

如图，这里的表头就是一级分类和二级分类

## 步骤三:创建一个ExcelObjectData类，其中定义一级分类和二级分类成员变量

```java
/**
package com.zj.exceleasydemo.com.zj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 步骤三：创建与excel的映射类
 */
@Data
public class ExcelObjectData {

    @ExcelProperty("一级分类")
    private String levelOneTitle;

    @ExcelProperty("二级分类")
    private String levelTwoTitle;
}

```

注意：这里为了实现类中属性与excel表中头部标题信息相对应，需要在@ExcelProperty中进行映射指定



## 步骤四：

创建service以及其相关接口：

ExcelService.java接口增加接口方法

```java
/**
 * 批量导入
 *
 * @param inputStream 输入流
 */
void batchImport(InputStream inputStream);
```

ExcelServiceImpl.java类增加实现功能

```JAVA
/**
 * 第四步：创建service层，主要用来获取excel中的数据
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public void BatchPost(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelObjectData.class,new ExcelDataListener())
                .excelType(ExcelTypeEnum.XLS).sheet().doRead();
    }
}

```

这里我们需要注意一下exceleasy给我们提供的这个read方法：


![在这里插入图片描述](https://img-blog.csdnimg.cn/fd4758c0f0b041b3addc711ff5fa70b0.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)


EasyExcel中的方法非常多，我们这里使用参数最多的一个方法：

```java
public static ExcelReaderBuilder read(File file, Class head, ReadListener readListener)
```

这里的file就是我们从前端上传过来的文件，head就是我们之前写的java类与excel头部的映射类ExcelSubjectData.class，而readListener类是我们需要重点关注的一个对象：

我们创建一个监听器ExcelDataListener.java

```java
/**
 * easyExcel中最重要的一个类，在这里实现数据的加入和读取
 */
@Data
public class ExcelDataListener extends AnalysisEventListener<ExcelObjectData> {
    /**
     * 一行一行的进行数据的获取
     * @param data
     * @param context
     */
    @Override
    public void invoke(ExcelObjectData data, AnalysisContext context) {
        System.out.println("开始获得一组数据");
        String levelOneTitle = data.getLevelOneTitle();
        System.out.print("levelOneTitle:"+levelOneTitle);
        String levelTwoTitle = data.getLevelTwoTitle();
        System.out.println("   levelTwoTitle:"+levelTwoTitle);

    }

    /**
     * 当所有数据获取完以后，执行的方法
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("所有数据获取结束");
    }
}

```

注意该监视器类不能被spring所托管，所以我们不能使用在该类中使用spring相关的功能或注解，官网的代码注释如下：

![在这里插入图片描述](https://img-blog.csdnimg.cn/a0578f40c4f2436ca6d84b6b19acde85.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)



## 步骤五：完成Controller层

在步骤四中，已经完成了数据的读取，我们可以发现，将数据从excel中读取过后，数据的存储并不是在service中发生的，而是在listener中发生的，只不过service又间接调用了listener，实现了数据存储的逻辑。另外上面的数据存储用到的是mysql的mybatis-plus，这里就不给出代码了。另外关于上面service的逻辑部分请根据自己的开发实际来定，我们这里easyexcel主要是用来获取excel中的数据而已，对于数据的存储已经不属于easyexcel的范畴了

步骤六：ExcelController

```java
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
}package com.javaclimb.service.edu.controller.admin;

import com.javaclimb.service.base.result.R;
import com.javaclimb.service.base.result.ResultCodeEnum;
import com.javaclimb.service.edu.service.SubjectService;
import com.javaclimb.servicebase.exception.JavaclimbException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

/**
 * <p>
 * 课程分类 前端控制器
 * </p>
 *
 * @author javaclimb
 * @since 2021-12-08
 */
@RestController
@RequestMapping("/admin/edu/subject")
@CrossOrigin
public class SubjectController {
    @Resource
    private SubjectService subjectService;

    @ApiOperation("excel批量导入课程分类")
    @PostMapping("/import")
    public R batchImport(@ApiParam(value = "Excel文件",required = true) @RequestParam("file")MultipartFile file){
        try {
            InputStream inputStream = file.getInputStream();
            subjectService.batchImport(inputStream);
            return R.ok().message("批量导入成功");
        } catch (IOException e) {
            throw new JavaclimbException(ResultCodeEnum.EXCEL_DATA_IMPORT_ERROR);
        }
    }
}
```

步骤六：调用api测试


![在这里插入图片描述](https://img-blog.csdnimg.cn/0556ab872d0446eabffc15603ad49dbd.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)


api调用没有问题，我们在看看控制台的打印：

![\[外链图片转存失败,源站可能有防盗链机制,建议将图片保存下来直接上传(img-JnE6qZFZ-1648208733836)(C:\Users\dell\AppData\Roaming\Typora\typora-user-images\image-20220325182114393.png)\]](https://img-blog.csdnimg.cn/360c2de564d24d3db38d2ac8d24bbeea.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA57yW56CB6Lev5LiK55qE5p-Q5YmR,size_20,color_FFFFFF,t_70,g_se,x_16)


测试成功



以上就是easyExcel的基本使用，更多使用请看官方文档：

[easyExcel官网](https://alibaba-easyexcel.github.io/quickstart/read.html)

