package com.example.scheduled.over;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CronTaskRegistrar implements DisposableBean {
  
    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);
  
    @Autowired
    private TaskScheduler taskScheduler;
  
    public TaskScheduler getScheduler() {  
        return this.taskScheduler;  
    }  
  
    public void addCronTask(Runnable task, String cronExpression) {  
        addCronTask(new CronTask(task, cronExpression));  
    }  
  
    public void addCronTask(CronTask cronTask) {
        if (cronTask != null) {  
            Runnable task = cronTask.getRunnable();  
            if (this.scheduledTasks.containsKey(task)) {  
                removeCronTask(task);  
            }  
  
            this.scheduledTasks.put(task, scheduleCronTask(cronTask));  
        }  
    }  
  
    public void removeCronTask(Runnable task) {  
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);  
        if (scheduledTask != null)  
            scheduledTask.cancel();  
    }  
  
    public ScheduledTask scheduleCronTask(CronTask cronTask) {  
        ScheduledTask scheduledTask = new ScheduledTask();  
        scheduledTask.future = this.taskScheduler.schedule(cronTask.getRunnable(), cronTask.getTrigger());  
  
        return scheduledTask;  
    }  
  
    //实现org.springframework.beans.factory.DisposableBean接口的bean允许在容器销毁该bean的时候获得一次回调。
    // DisposableBean接口也只规定了一个方法：destroy
    @Override  
    public void destroy() {  
        for (ScheduledTask task : this.scheduledTasks.values()) {  
            task.cancel();  
        }  
  
        this.scheduledTasks.clear();  
    }  
} 