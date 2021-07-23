package com.example.scheduled.over.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.scheduled.over.entity.Scheduled;

public interface ScheduledService extends IService<Scheduled> {

    boolean addScheduled(Scheduled scheduled);

    Scheduled updateScheduled(Scheduled scheduled);

    Scheduled deleteScheduled(Integer id);

    Scheduled selectById(Integer id);
}

