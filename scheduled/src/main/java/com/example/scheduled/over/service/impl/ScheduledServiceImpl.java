package com.example.scheduled.over.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.scheduled.over.entity.Scheduled;
import com.example.scheduled.over.mapper.ScheduledMapper;
import com.example.scheduled.over.service.ScheduledService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("scheduledService")
public class ScheduledServiceImpl extends ServiceImpl<ScheduledMapper, Scheduled> implements ScheduledService {

    @Autowired
    private ScheduledMapper scheduledMapper;

    @Override
    public boolean addScheduled(Scheduled scheduled) {
        return scheduledMapper.insert(scheduled) > 0;
    }

    @Override
    public Scheduled updateScheduled(Scheduled scheduled) {
        final Scheduled exist = scheduledMapper.selectById(scheduled.getJobId());
        scheduledMapper.updateById(scheduled);
        return exist;
    }

    @Override
    public Scheduled deleteScheduled(Integer id) {
        final Scheduled exist = scheduledMapper.selectById(id);
         scheduledMapper.deleteById(id);
        return exist;
    }

    @Override
    public Scheduled selectById(Integer id) {
        return scheduledMapper.selectById(id);
    }
}