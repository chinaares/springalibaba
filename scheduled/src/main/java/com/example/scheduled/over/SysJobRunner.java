package com.example.scheduled.over;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.scheduled.over.entity.Scheduled;
import com.example.scheduled.over.enumeration.JobStatusEnum;
import com.example.scheduled.over.mapper.ScheduledMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SysJobRunner implements CommandLineRunner {


    @Autowired
    private ScheduledMapper scheduledMapper;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @Override
    public void run(String... args) {
        // 初始加载数据库里状态为正常的定时任务
        List<Scheduled> jobList = scheduledMapper.selectList(new QueryWrapper<Scheduled>().eq("job_status",JobStatusEnum.NORMAL));
        if (CollUtil.isNotEmpty(jobList)) {
            for (Scheduled job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            log.info("定时任务已加载完毕...");
        }
    }
}