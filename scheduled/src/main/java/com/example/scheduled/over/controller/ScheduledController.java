package com.example.scheduled.over.controller;

import com.example.common.exception.basic.APIResponse;
import com.example.scheduled.over.CronTaskRegistrar;
import com.example.scheduled.over.SchedulingRunnable;
import com.example.scheduled.over.entity.Scheduled;
import com.example.scheduled.over.enumeration.JobStatusEnum;
import com.example.scheduled.over.service.ScheduledService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-23 10:30:17
 */
@RestController
@RequestMapping("/scheduled")
@Api(tags = "定时任务管理")
@Slf4j
public class ScheduledController {

    @Autowired
    private ScheduledService scheduledService;

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    @RequestMapping(value = "/addScheduled", method = RequestMethod.POST)
    @ApiOperation(value = "创建定时任务并开启", notes = "创建定时任务并开启")
    public APIResponse addScheduled(@RequestBody @Valid Scheduled scheduled){
        try {
            if(scheduledService.addScheduled(scheduled)){
                //加载定时任务
                if (scheduled.getJobStatus().equals(JobStatusEnum.NORMAL)) {
                    SchedulingRunnable task = new SchedulingRunnable(scheduled.getBeanName(), scheduled.getMethodName(), scheduled.getMethodParams());
                    cronTaskRegistrar.addCronTask(task, scheduled.getCronExpression());
                }
            }
            return APIResponse.ok(scheduled,"创建定时任务并开启");
        } catch (Exception e) {
            return APIResponse.fail("创建定时任务并开启失败！" + e.getMessage());
        }
    }

    @RequestMapping(value = "/updateScheduled", method = RequestMethod.POST)
    @ApiOperation(value = "修改定时任务并开启", notes = "修改定时任务并开启")
    public APIResponse updateScheduled(@RequestBody @Valid Scheduled scheduled){
        try {
            Scheduled success = scheduledService.updateScheduled(scheduled);
            if(Objects.nonNull(success)){
                //先移除再添加
                if (success.getJobStatus().equals(JobStatusEnum.NORMAL)) {
                    SchedulingRunnable task = new SchedulingRunnable(success.getBeanName(), success.getMethodName(), success.getMethodParams());
                    cronTaskRegistrar.removeCronTask(task);
                }
                if (scheduled.getJobStatus().equals(JobStatusEnum.NORMAL)) {
                    SchedulingRunnable task = new SchedulingRunnable(scheduled.getBeanName(), scheduled.getMethodName(), scheduled.getMethodParams());
                    cronTaskRegistrar.addCronTask(task, scheduled.getCronExpression());
                }
            }
            return APIResponse.ok(scheduled,"修改定时任务并开启");
        } catch (Exception e) {
            return APIResponse.fail("修改定时任务并开启失败！" + e.getMessage());
        }
    }

    @RequestMapping(value = "/deleteScheduled", method = RequestMethod.POST)
    @ApiOperation(value = "删除定时任务并关闭", notes = "删除定时任务并关闭")
    public APIResponse deleteScheduled(@RequestBody Integer id){
        try {
            Scheduled success = scheduledService.deleteScheduled(id);
            if(Objects.nonNull(success)){
                if (success.getJobStatus().equals(JobStatusEnum.NORMAL.ordinal())) {
                    SchedulingRunnable task = new SchedulingRunnable(success.getBeanName(), success.getMethodName(), success.getMethodParams());
                    cronTaskRegistrar.removeCronTask(task);
                }
            }
            return APIResponse.ok(success,"删除定时任务并关闭");
        } catch (Exception e) {
            return APIResponse.fail("删除定时任务并关闭失败！" + e.getMessage());
        }
    }

    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    @ApiOperation(value = "定时任务状态切换", notes = "定时任务状态切换")
    public APIResponse updateStatus(@RequestBody Integer id){
        try {
            Scheduled scheduled = scheduledService.selectById(id);
            if(Objects.nonNull(scheduled)){
                if (scheduled.getJobStatus().equals(JobStatusEnum.NORMAL.ordinal())) {
                    SchedulingRunnable task = new SchedulingRunnable(scheduled.getBeanName(), scheduled.getMethodName(), scheduled.getMethodParams());
                    cronTaskRegistrar.addCronTask(task, scheduled.getCronExpression());
                } else {
                    SchedulingRunnable task = new SchedulingRunnable(scheduled.getBeanName(), scheduled.getMethodName(), scheduled.getMethodParams());
                    cronTaskRegistrar.removeCronTask(task);
                }
            }
            return APIResponse.ok("定时任务状态切换");
        } catch (Exception e) {
            return APIResponse.fail("定时任务状态切换失败！" + e.getMessage());
        }
    }
}
