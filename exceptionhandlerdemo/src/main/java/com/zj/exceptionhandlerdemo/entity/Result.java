package com.zj.exceptionhandlerdemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private boolean suceess;
    private String code;
    private String message;
    private Object data;


}
