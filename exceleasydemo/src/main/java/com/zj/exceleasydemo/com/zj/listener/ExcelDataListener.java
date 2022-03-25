package com.zj.exceleasydemo.com.zj.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.zj.exceleasydemo.com.zj.entity.ExcelObjectData;
import lombok.Data;

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
