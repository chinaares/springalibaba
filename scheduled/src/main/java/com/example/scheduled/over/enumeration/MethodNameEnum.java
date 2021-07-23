package com.example.scheduled.over.enumeration;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-23 14:47:04
 */
public enum MethodNameEnum {
    taskWithParams("taskWithParams","带有参数的任务"),
    taskNoParams("taskNoParams","无参数的任务"),
    ;

    private String method;
    private String describe;

    MethodNameEnum(String method,String describe) {
        this.method = method;
        this.describe = describe;
    }
}
