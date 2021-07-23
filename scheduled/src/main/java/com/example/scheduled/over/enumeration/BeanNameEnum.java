package com.example.scheduled.over.enumeration;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-23 14:37:44
 */
public enum BeanNameEnum {
    demoTask("demoTask","默认展示定时任务类名"),
    ;

    private String target;
    private String describe;

    BeanNameEnum(String target,String describe) {
        this.target = target;
        this.describe = describe;
    }

}
