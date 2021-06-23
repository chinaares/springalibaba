package com.example.elasticsearch.repository;

import com.example.elasticsearch.entity.EmployeeBean;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EmployeeRepository extends ElasticsearchRepository<EmployeeBean, String> {

    List<EmployeeBean> findByStudentCode(String studentCode);
}