package com.example.demo.config;

import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 9:22 AM
 **/
@Configuration
public class QuartzConfig {
    @Autowired
    private JobFactory jobFactory;

    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    @Bean
    public JobFactory jobFactory(){
        return new AdaptableJobFactory(){
            @Override
            protected  Object createJobInstance(TriggerFiredBundle bundle) throws Exception{
                Object jobInstance = super.createJobInstance(bundle);
                capableBeanFactory.autowireBean(jobInstance);
                return jobInstance;
            }
        };
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setStartupDelay(1);
        return schedulerFactoryBean;
    }

    @Bean
    public Scheduler scheduler(){
        return schedulerFactoryBean().getScheduler();
    }
}
