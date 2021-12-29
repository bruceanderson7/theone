package com.example.demo.serviceImpl;

import com.example.demo.entity.Room;
import com.example.demo.mapper.RoomMapper;
import com.example.demo.service.RoomService;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.example.demo.util.SerialGenerator.generateUUid;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 3:01 下午
 **/
@Service
public class RoomServiceImpl implements RoomService {
    @Resource
    private RoomMapper roomMapper;

    @Autowired
    private RedisUtil redisUtil;

    private String roomKey;

    public Boolean queryByRoomId(int roomId){
        if(roomMapper.queryByRoomId(roomId) == null)
            return false;
        else
            return true;
    }

    public int insert(Room room){
        int i = 1;
        roomKey = room.getUuid();
        redisUtil.set(roomKey,room);
        i = roomMapper.insert(room);
        return i;
    }

    public Room queryById(long id){
        return roomMapper.queryById(id);
    }

    public void update(Room room){
        String uuid = room.getUuid();
        redisUtil.set(uuid,room);
        roomMapper.update(room);
    }
}
