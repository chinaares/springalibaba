package com.example.elasticsearch.service;

import com.example.elasticsearch.entity.EmployeeBean;
import com.example.elasticsearch.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.filter.FiltersAggregator;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-06-22 15:43:16
 */
@Service
@Slf4j
public class ElasticServiceTemplate {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;


    /*-------------------------索引------------------------*/

    /**
     * 判断索引是否存在
     *
     * @return boolean
     */
    public boolean indexExists() {
        return restTemplate.indexExists(EmployeeBean.class);
    }

    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexExists(String indexName) {
        return restTemplate.indexExists(indexName);
    }

    /**
     * 创建索引（推荐使用：因为Java对象已经通过注解描述了Setting和Mapping）
     *
     * @return boolean
     */
    public boolean indexCreate() {
        return restTemplate.createIndex(EmployeeBean.class);
    }

    /**
     * 创建索引
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexCreate(String indexName) {
        return restTemplate.createIndex(indexName);
    }

    /**
     * 索引删除
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexDelete(String indexName) {
        return restTemplate.deleteIndex(indexName);
    }


    /**
     * 修改数据
     *
     * @param indexName 索引名称
     * @param type      索引类型
     * @param bean      修改数据对象，ID不能为空
     */
    public UpdateResponse update(String indexName, String type, EmployeeBean bean) {
        Document document = Document.create();
        document.putIfAbsent("studentCode", bean.getDesc());
        document.putIfAbsent("name", bean.getName());
        document.putIfAbsent("desc", bean.getDesc());
        document.putIfAbsent("type", bean.getType());
        document.putIfAbsent("mobile", bean.getMobile());
        document.setId(bean.getId());
        UpdateQuery build = UpdateQuery.builder(bean.getId()).withDocument(document).withScriptedUpsert(true).build();
        return restTemplate.update(build, IndexCoordinates.of(indexName).withTypes(type));
    }


    /**
     * 批量新增
     *
     * @param indexName 索引名称
     * @param type      索引类型
     * @param beanList  新增对象集合
     */
    public void batchSave(String indexName, String type, List<EmployeeBean> beanList) {
        List<IndexQuery> list = new ArrayList<>();
        beanList.forEach(bean -> {
            Document document = Document.create();
            document.putIfAbsent("studentCode", bean.getDesc());
            document.putIfAbsent("name", bean.getName());
            document.putIfAbsent("desc", bean.getDesc());
            document.putIfAbsent("type", bean.getType());
            document.putIfAbsent("mobile", bean.getMobile());
            document.setId(bean.getId());
            IndexQuery indexQuery = new IndexQueryBuilder().withId(bean.getId())
                    .withObject(bean).build();
            list.add(indexQuery);
        });
        restTemplate.bulkIndex(list, IndexCoordinates.of(indexName).withTypes(type));
    }

    /**
     * 批量修改
     *
     * @param indexName 索引名称
     * @param type      索引类型
     * @param beanList  修改对象集合
     */
    public void batchUpdate(String indexName, String type, List<EmployeeBean> beanList) {
        List<UpdateQuery> updateQuerys = new ArrayList<>();
        //生成批量更新操做
        beanList.stream().forEach(bean -> {
            Document document = Document.create();
            document.putIfAbsent("studentCode", bean.getDesc());
            document.putIfAbsent("name", bean.getName());
            document.putIfAbsent("desc", bean.getDesc());
            document.putIfAbsent("type", bean.getType());
            document.putIfAbsent("mobile", bean.getMobile());
            document.setId(bean.getId());
            UpdateQuery build = UpdateQuery.builder(bean.getId()).withDocument(document).withScriptedUpsert(true).build();
            updateQuerys.add(build);
        });
        restTemplate.bulkUpdate(updateQuerys, IndexCoordinates.of(indexName).withTypes(type));
    }

    /**
     * 数据查询，返回List
     *
     * @param field 查询字段
     * @param value 查询值
     * @return List<EmployeeBean>
     */
    public List<EmployeeBean> queryMatchList(String indexName, String type,String field, String value) {
        String[] includes = new String[]{"paperBaseId", "auditInfoStatus"};
        SourceFilter sourceFilter = new FetchSourceFilterBuilder().withIncludes(includes).build();
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withSourceFilter(sourceFilter)
                // 添加查询条件
                .withQuery(QueryBuilders.termsQuery(field, value))
                .build();
        return restTemplate.queryForList(searchQuery, EmployeeBean.class,IndexCoordinates.of(indexName).withTypes(type));
    }

    public String delete(EmployeeBean bean) {
        return restTemplate.delete(bean);
    }

    /*-----------------------------------------查询-----------------------------*/

    /**
     * 关键字匹配查询
     *
     * @param name  字段的名称
     * @param value 查询值
     */
    public static TermQueryBuilder termQuery(String name, String value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, int value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, long value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, float value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, double value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, boolean value) {
        return new TermQueryBuilder(name, value);
    }

    public static TermQueryBuilder termQuery(String name, Object value) {
        return new TermQueryBuilder(name, value);
    }

    /**
     * 关键字查询，同时匹配多个关键字
     *
     * @param name   字段名称
     * @param values 查询值
     */
    public static TermsQueryBuilder termsQuery(String name, String... values) {
        return new TermsQueryBuilder(name, values);
    }

    /**
     * 创建一个匹配多个关键字的查询，返回boolean
     *
     * @param fieldNames 字段名称
     * @param text       查询值
     */
    public static MultiMatchQueryBuilder multiMatchQuery(Object text, String... fieldNames) {
        return new MultiMatchQueryBuilder(text, fieldNames); // BOOLEAN is the default
    }

    /**
     * 关键字，精确匹配
     *
     * @param name 字段名称
     * @param text 查询值
     */
    public static MatchQueryBuilder matchQuery(String name, Object text) {
        return new MatchQueryBuilder(name, text);
    }

    /**
     * 关键字范围查询（后面跟范围条件）
     *
     * @param name 字段名称
     */
    public static RangeQueryBuilder rangeQuery(String name) {
        return new RangeQueryBuilder(name);
    }

    /**
     * 判断字段是否有值
     *
     * @param name 字段名称
     */
    public static ExistsQueryBuilder existsQuery(String name) {
        return new ExistsQueryBuilder(name);
    }

    /**
     * 模糊查询
     *
     * @param name  字段名称
     * @param value 查询值
     */
    public static FuzzyQueryBuilder fuzzyQuery(String name, String value) {
        return new FuzzyQueryBuilder(name, value);
    }

    /**
     * 组合查询对象，可以同时引用上面的所有查询对象
     */
    public static BoolQueryBuilder boolQuery() {
        return new BoolQueryBuilder();
    }

    /*--------------------------------聚合查询---------------------------------------*/

    /**
     * 根据字段聚合，统计该字段的每个值的数量
     */
    public static TermsAggregationBuilder terms(String name) {
        return new TermsAggregationBuilder(name, null);
    }

    /**
     * 统计操作的，过滤条件
     */
    public static FilterAggregationBuilder filter(String name, QueryBuilder filter) {
        return new FilterAggregationBuilder(name, filter);
    }

    /**
     * 设置多个过滤条件
     */
    public static FiltersAggregationBuilder filters(String name, FiltersAggregator.KeyedFilter... filters) {
        return new FiltersAggregationBuilder(name, filters);
    }

    /**
     * 统计该字段的数据总数
     */
    public static ValueCountAggregationBuilder count(String name) {
        return new ValueCountAggregationBuilder(name, null);
    }

    /**
     * 计算平均值
     */
    public static AvgAggregationBuilder avg(String name) {
        return new AvgAggregationBuilder(name);
    }

    /**
     * 计算最大值
     */
    public static MaxAggregationBuilder max(String name) {
        return new MaxAggregationBuilder(name);
    }

    /**
     * 计算最小值
     */
    public static MinAggregationBuilder min(String name) {
        return new MinAggregationBuilder(name);
    }

    /**
     * 计算总数
     */
    public static SumAggregationBuilder sum(String name) {
        return new SumAggregationBuilder(name);
    }
}
