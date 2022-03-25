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
