package com.example.demo.service;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 9:38 AM
 **/
public interface QuartzService {
    void addJob(String jName, String jGroup, String tName, String tGroup, String cron);

    void pauseJob(String jName, String jGroup);

    void resumeJob(String jName, String jGroup);

    void deleteJob(String jName, String jGroup);
}
