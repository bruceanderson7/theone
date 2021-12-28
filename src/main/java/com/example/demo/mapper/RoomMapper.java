package com.example.demo.mapper;

import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 3:00 下午
 **/
public interface RoomMapper {
    Boolean queryByRoomId(int roomId);

    int insert(Room room);
}
