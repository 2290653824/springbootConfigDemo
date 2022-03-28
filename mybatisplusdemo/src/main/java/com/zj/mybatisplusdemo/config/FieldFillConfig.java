package com.zj.mybatisplusdemo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.sql.Date;

/**
 * 主要用于字段属性的填充
 */
@Component
public class FieldFillConfig implements MetaObjectHandler {

    /**
     * 当字段上为INSERT时，会调用该方法
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {

        this.strictInsertFill(metaObject, "insertTime",()->new java.util.Date() , java.util.Date.class);
        this.strictInsertFill(metaObject, "updateTime",()->new java.util.Date() , java.util.Date.class);
        this.strictInsertFill(metaObject,"version",()->1,Integer.class);


    }

    /**
     * 当字段上有UPDATE时，会调用该方法
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {

        this.strictUpdateFill(metaObject, "updateTime", ()->new java.util.Date() , java.util.Date.class);
    }


}
