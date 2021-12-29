package com.example.demo.service;

import com.example.demo.entity.Room;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 3:00 下午
 **/
public interface RoomService {
    Boolean queryByRoomId(int roomId);

    int insert(Room room);

    Room queryById(long id);

    void update(Room room);

    List<Room> queryAllByLimit(int page, int count);

    List<Room> queryLeisureByLimit(int page, int count);
}
