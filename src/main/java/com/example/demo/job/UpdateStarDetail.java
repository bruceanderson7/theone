package com.example.demo.job;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/23 4:55 PM
 **/
public class UpdateStarDetail {
    public static void main(String args[]) throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        //启动schedule
        scheduler.start();
        //创建更新评分实例，并设置name和group
        JobDetail jobDetail = JobBuilder.newJob(UpdateStar.class).
                withIdentity("UpdateStar","1").build();
        //创建Trigger
        Trigger trigger = TriggerBuilder.newTrigger().
                withIdentity("UpdateStarTrigger","1").
                startNow().
                withSchedule(CronScheduleBuilder.cronSchedule("0/3 15 3 * * *")).build();
        //注册JobDetail实例到schedule
        scheduler.scheduleJob(jobDetail, trigger);

    }
}
