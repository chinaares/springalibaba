package com.example.elasticsearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;
/**
 * 员工对象
 * <p>
 * 注解：@Document用来声明Java对象与ElasticSearch索引的关系
 * indexName 索引名称
 * type      索引类型
 * shards    主分区数量，默认5
 * replicas  副本分区数量，默认1
 * createIndex 索引不存在时，是否自动创建索引，默认true*/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
@Document(indexName = "employee_index", type = "employee_type", shards = 1, replicas = 0,createIndex = true)
public class EmployeeBean {

    @Id
    private String id;

    /**
     * 员工编码
     */
    @Field(type = FieldType.Keyword)
    private String studentCode;

    /**
     * 员工姓名
     */
    @Field(type = FieldType.Keyword)
    private String name;

    /**
     * 员工简历
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String desc;

    /**
     * 员工住址
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private Integer type;

    /**
     * 手机号码
     */
    @Field(type = FieldType.Keyword)
    private String mobile;

}