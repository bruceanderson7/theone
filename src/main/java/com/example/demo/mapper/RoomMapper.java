package com.example.demo.mapper;

import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 3:00 下午
 **/
public interface RoomMapper {
    Boolean queryByRoomId(int roomId);

    int insert(Room room);

    Room queryById(long id);

    void update(Room room);

    List<Room> queryAllByLimit(int page, int count);

    List<Room> queryLeisureByLimit(int page, int count);
}
