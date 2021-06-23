package com.example.elasticsearch.service;

import com.example.elasticsearch.entity.EmployeeBean;
import com.example.elasticsearch.repository.EmployeeRepository;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-06-22 15:43:16
 */
@Service
@Slf4j
public class ElasticService {

    @Autowired
    private EmployeeRepository repository;

    /*-----------------------数据-------------------------------*/

    /**
     * 新增数据/修改数据（根据id区分）
     *
     * @param bean 数据对象
     */
    public void save(EmployeeBean bean) {
        repository.save(bean);
    }

    /**
     * 批量新增数据/修改数据（根据id区分）
     *
     * @param list 数据集合
     */
    public void saveAll(List<EmployeeBean> list) {
        repository.saveAll(list);
    }

    /**
     * 根据ID，删除数据
     *
     * @param id 数据ID
     */
    public void deleteById(String id) {
        repository.deleteById(id);
    }

    /**
     * 根据对象删除数据，主键ID不能为空
     *
     * @param bean 对象
     */
    public void deleteByBean(EmployeeBean bean) {
        repository.delete(bean);
    }

    /**
     * 根据对象集合，批量删除
     *
     * @param beanList 对象集合
     */
    public void deleteAll(List<EmployeeBean> beanList) {
        repository.deleteAll(beanList);
    }

    /**
     * 删除所有
     */
    public void deleteAll() {
        repository.deleteAll();
    }

    /**
     * 查询所有
     *
     * @return
     */
    public List<EmployeeBean> findAll() {
        final Iterable<EmployeeBean> all = repository.findAll();
        return Lists.newArrayList(all);
    }

    /**
     * 按条件查询
     * And	findByNameAndPrice	{"bool" : {"must" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}}
     * Or	findByNameOrPrice	{"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"price" : "?"}} ]}}
     * Is	findByName	{"bool" : {"must" : {"field" : {"name" : "?"}}}}
     * Not	findByNameNot	{"bool" : {"must_not" : {"field" : {"name" : "?"}}}}
     * Between	findByPriceBetween	{"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
     * LessThanEqual	findByPriceLessThan	{"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
     * GreaterThanEqual	findByPriceGreaterThan	{"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : null,"include_lower" : true,"include_upper" : true}}}}}
     * Before	findByPriceBefore	{"bool" : {"must" : {"range" : {"price" : {"from" : null,"to" : ?,"include_lower" : true,"include_upper" : true}}}}}
     * After	findByPriceAfter	{"bool" : {"must" : {"range" : {"price" : {"from" : ?,"to" : null,"include_lower" : true,"include_upper" : true}}}}}
     * Like	findByNameLike	{"bool" : {"must" : {"field" : {"name" : {"query" : "?*","analyze_wildcard" : true}}}}}
     * StartingWith	findByNameStartingWith	{"bool" : {"must" : {"field" : {"name" : {"query" : "?*","analyze_wildcard" : true}}}}}
     * EndingWith	findByNameEndingWith	{"bool" : {"must" : {"field" : {"name" : {"query" : "*?","analyze_wildcard" : true}}}}}
     * Contains/Containing	findByNameContaining	{"bool" : {"must" : {"field" : {"name" : {"query" : "**?**","analyze_wildcard" : true}}}}}
     * In	findByNameIn(Collection<String>names)	{"bool" : {"must" : {"bool" : {"should" : [ {"field" : {"name" : "?"}}, {"field" : {"name" : "?"}} ]}}}}
     * NotIn	findByNameNotIn(Collection<String>names)	{"bool" : {"must_not" : {"bool" : {"should" : {"field" : {"name" : "?"}}}}}}
     * Near	findByStoreNear	Not Supported Yet !
     * True	findByAvailableTrue	{"bool" : {"must" : {"field" : {"available" : true}}}}
     * False	findByAvailableFalse	{"bool" : {"must" : {"field" : {"available" : false}}}}
     * OrderBy	findByAvailableTrueOrderByNameDesc	{"sort" : [{ "name" : {"order" : "desc"} }],"bool" : {"must" : {"field" : {"available" : true}}}}
     *
     * @return
     */
    public List<EmployeeBean> findByStudentCode(String studentCode) {
       return repository.findByStudentCode(studentCode);
    }

}
