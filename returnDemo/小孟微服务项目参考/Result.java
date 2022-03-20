package com.zj.common_utils.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**

 - 全局统一返回结果
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result {

    @ApiModelProperty(value = "是否成功")
    private Boolean success;
    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<>();

    public Result() {
    }

    public static Result ok() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;
    }

    public static Result error() {
        Result result = new Result();
        result.setSuccess(ResultCodeEnum.UNKNOWN_REASON.getSuccess());
        result.setCode(ResultCodeEnum.UNKNOWN_REASON.getCode());
        result.setMessage(ResultCodeEnum.UNKNOWN_REASON.getMessage());
        return result;
    }

    public static Result setResult(ResultCodeEnum resultCodeEnum) {
        Result result = new Result();
        result.setSuccess(resultCodeEnum.getSuccess());
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
