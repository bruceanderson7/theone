package com.example.demo.serviceImpl;

import com.example.demo.entity.RoomTemp;
import com.example.demo.mapper.RoomTempMapper;
import com.example.demo.service.RoomService;
import com.example.demo.service.RoomTempService;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/29 2:24 PM
 **/
@Service
public class RoomTempServiceImpl implements RoomTempService {
    @Resource
    private RoomTempMapper roomTempMapper;

    @Autowired
    private RedisUtil redisUtil;

    public int insert(RoomTemp roomTemp){
        int i = 0;
        i = roomTempMapper.insert(roomTemp);
        return i;
    }

    public RoomTemp queryById(long id){
        return roomTempMapper.queryById(id);
    }

    public void delete(long id){
       roomTempMapper.delete(id);
    }
}
