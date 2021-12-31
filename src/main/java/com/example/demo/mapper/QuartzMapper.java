package com.example.demo.mapper;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 9:41 AM
 **/
public interface QuartzMapper {
    void addJob(String jName, String jGroup, String tName, String tGroup, String cron);

    void pauseJob(String jName, String jGroup);

    void resumeJob(String jName, String jGroup);

    void deleteJob(String jName, String jGroup);
}
