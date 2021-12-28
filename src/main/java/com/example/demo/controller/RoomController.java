package com.example.demo.controller;

import com.example.demo.entity.Room;
import com.example.demo.service.RoomService;
import com.example.demo.util.DataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 2:56 下午
 **/
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @GetMapping("/register")
    public DataReturn<Void> registerRoom(@RequestParam("hotelId")long hotelId,@RequestParam("roomId")int roomId,@RequestParam("type")String type,
    @RequestParam(value = "imgPath",required = false)String imgPath,@RequestParam("price")int price,@RequestParam(value = "description",required = false)String description){
        if(roomService.queryByRoomId(roomId))
            return DataReturn.failure("房间号已存在");
        else{
            Room room = new Room();
            room.setHotelId(hotelId);
            room.setRoomId(roomId);
            room.setType(type);
            room.setPrice(price);
            if(imgPath == null || imgPath == "")
                room.setImgPath("默认");
            else
                room.setImgPath(imgPath);
            if(description == null || description == "")
                room.setDescription("无");
            else
                room.setDescription(description);

            room.setStatus(1);     //1代表空闲，2代表已满
            roomService.insert(room);
            return DataReturn.success();
        }
    }
}
