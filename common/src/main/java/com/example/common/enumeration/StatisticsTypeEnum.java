package com.example.common.enumeration;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-08 13:28:45
 */
public enum StatisticsTypeEnum {
    D("按日筛选"),
    W("按周筛选"),
    M("按月筛选"),
    ;

    private String  name;
    private Integer num;
    StatisticsTypeEnum(String name){
        this.name = name;
        this.num = this.ordinal();
    }

    public String getName() {
        return name;
    }

    public Integer getNum(){
        return num;
    }
}
