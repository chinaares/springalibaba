### 自定义定时任务
1. 执行scheduled.sql,定义自定义任务类，方法，执行cron表达式
2. SysJobRunner初始化加载数据库已经定义好的定时任务并启动
3. DemoTask编写可供选择的定时任务方法,反射待用
4. 根据添加schedlud执行SchedulingRunnable,cronTaskRegistrar
5. 自定义开启或关闭和执行规则表达式
### 定时任务
1. @EnableScheduling定义定时任务开启
2. @Scheduled定义方法规则
3. ```java
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
    ```