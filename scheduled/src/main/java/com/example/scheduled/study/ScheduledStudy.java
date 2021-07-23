package com.example.scheduled.study;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author wuhao
 * @desc ...
 * @date 2021-07-22 13:31:26
 */
//@EnableScheduling
@Slf4j
@Component
public class ScheduledStudy {
    /**
     * .cron一共有7位，但是最后一位是年（1970－2099），可以留空，所以我们可以写6位,按顺序依次为
     * 秒（0~59）
     * 分钟（0~59）
     * 小时（0~23）
     * 天（月）（0~31，但是你需要考虑你月的天数）
     * 月（0~11）
     * 星期（1~7 1=SUN，MON，TUE，WED，THU，FRI，SAT）
     * cron的一些特殊符号
     * (*)星号：
     * 可以理解为每的意思，每秒，每分，每天，每月，每年…
     * (?)问号：
     * 问号只能出现在日期和星期这两个位置，表示这个位置的值不确定，每天3点执行，所以第六位星期的位置，我们是不需要关注的，就是不确定的值。同时：日期和星期是两个相互排斥的元素，通过问号来表明不指定值。比如，1月10日，比如是星期1，如果在星期的位置是另指定星期二，就前后冲突矛盾了。
     * (-)减号：
     * 表达一个范围，如在小时字段中使用“10-12”，则表示从10到12点，即10,11,12
     * (,)逗号：
     * 表达一个列表值，如在星期字段中使用“1,2,4”，则表示星期一，星期二，星期四
     * (/)斜杠：如：x/y，x是开始值，y是步长，比如在第一位（秒） 0/15就是，从0秒开始，每15秒，最后就是0，15，30，45，60 另：*-/y，等同于0/y
     */
    // fixedRate = 5000表示每隔5秒，Spring scheduling会调用一次该方法，不论该方法的执行时间是多少
    @Scheduled(fixedRate = 5000)
    public void task() {
        log.info("fixedRate每隔5秒执行一次");
    }

    // fixedDelay = 5000表示当方法执行完毕5秒后，Spring scheduling会再次调用该方法
    @Scheduled(fixedDelay = 5000)
    public void taskAfter() {
        log.info("fixedDelay当方法执行完毕5秒后执行");
    }

    // cron = "*/5 * * * * * *" 通用的定时任务表达式，表示每隔5秒执行一次
    @Scheduled(cron = "*/5 * * * * *")
    public void taskCron() {
        log.info("每隔5秒执行一次");
    }
}
