package com.example.demo.service;

import com.example.demo.entity.Room;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 3:00 下午
 **/
public interface RoomService {
    Boolean queryByRoomId(int roomId);

    int insert(Room room);
}
