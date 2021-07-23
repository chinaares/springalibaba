package com.example.scheduled.over.enumeration;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-23 10:22:55
 */
public enum JobStatusEnum {
    PAUSE("暂停"),
    NORMAL("正常"),
    ;

    private String name;

    JobStatusEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
