package com.zj.exceleasydemo.com.zj.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.zj.exceleasydemo.com.zj.entity.ExcelObjectData;
import com.zj.exceleasydemo.com.zj.listener.ExcelDataListener;
import com.zj.exceleasydemo.com.zj.service.ExcelService;
import org.springframework.stereotype.Service;

import java.io.InputStream;

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
