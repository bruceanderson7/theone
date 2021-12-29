package com.example.demo.mapper;

import com.example.demo.entity.RoomTemp;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/29 2:21 PM
 **/
public interface RoomTempMapper {
    int insert(RoomTemp roomTemp);

    RoomTemp queryById(long id);

    void delete(long id);
}
