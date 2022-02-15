package com.example.demo.serviceImpl;

import com.example.demo.job.UpdateStar;
import com.example.demo.service.QuartzService;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 9:41 AM
 **/
@Service
public class QuartzServiceImpl implements QuartzService {
    @Autowired
    private Scheduler scheduler;

    public void addJob(String jName, String jGroup, String tName, String tGroup, String cron){
        try{
            JobDetail jobDetail = JobBuilder.newJob(UpdateStar.class).withIdentity(jName,jGroup).build();
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(tName, tGroup).startNow().withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            scheduler.start();
            scheduler.scheduleJob(jobDetail,trigger);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void pauseJob(String jName, String jGroup){
        try{
            scheduler.pauseJob(JobKey.jobKey(jName,jGroup));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void resumeJob(String jName, String jGroup){
        try{
            scheduler.resumeJob(JobKey.jobKey(jName, jGroup));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

    public void deleteJob(String jName, String jGroup){
        try{
            scheduler.deleteJob(JobKey.jobKey(jName, jGroup));
        } catch(SchedulerException e){
            e.printStackTrace();
        }
    }

}
