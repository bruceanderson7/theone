package com.example.demo.service;

import com.example.demo.entity.RoomTemp;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/29 2:21 PM
 **/
public interface RoomTempService {
    int insert(RoomTemp roomTemp);

    RoomTemp queryById(long id);

    void delete(long id);
}
