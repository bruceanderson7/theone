package com.example.demo.job;

import com.example.demo.entity.Hotel;
import com.example.demo.service.HotelService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 9:36 AM
 **/
public class DemoJob extends QuartzJobBean {
    @Autowired
    private HotelService hotelService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException{
        hotelService.queryById(1);
        System.out.println("testing quartz");
    }
}
