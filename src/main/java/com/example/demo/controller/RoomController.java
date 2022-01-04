package com.example.demo.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Order;
import com.example.demo.entity.Room;
import com.example.demo.service.HotelService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RoomService;
import com.example.demo.util.DataReturn;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.util.SerialGenerator.generateUUid;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/28 2:56 下午
 **/
@RestController
@RequestMapping("/room")
public class RoomController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderService orderService;

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
            if(imgPath == null || imgPath.equals(""))
                room.setImgPath("默认");
            else
                room.setImgPath(imgPath);
            if(description == null || description.equals(""))
                room.setDescription("无");
            else
                room.setDescription(description);

            room.setStatus(1);     //1代表空闲，2代表已满
            String uuid = generateUUid();
            room.setUuid(uuid);
            roomService.insert(room);
            return DataReturn.success();
        }
    }

    /*
    酒店管理员关闭房间
     */
    @GetMapping("/close")
    public DataReturn<Void> closeRoom(@RequestParam("id")long id){
        Room room = roomService.queryById(id);
        if(room != null){
            room.setStatus(2);
            roomService.update(room);
            return DataReturn.success();
        }
        else
            return DataReturn.failure("未能查找到该房间");
    }

    /*
    酒店管理员开启房间
    */
    @GetMapping("/open")
    public DataReturn<Void> openRoom(@RequestParam("id")long id){
        Room room = roomService.queryById(id);
        if(room != null){
            room.setStatus(1);
            roomService.update(room);
            return DataReturn.success();
        }
        else
            return DataReturn.failure("未能查找到该房间");
    }

    /*
    酒店管理员修改房间信息
    */
    @GetMapping("/edit")
    public DataReturn<Void> editRoom(@RequestParam("id")long id,@RequestParam(value = "type",required = false)String type,@RequestParam(value = "imgPath",required = false)String imgPath,
    @RequestParam(value = "price",required = false)double price,@RequestParam(value = "description",required = false)String description){
        Room room = roomService.queryById(id);
        Hotel hotel = hotelService.queryById(room.getHotelId());

            if(type != null && !type.equals(""))
                room.setType(type);
            if(imgPath != null && !imgPath.equals(""))
                room.setImgPath(imgPath);
            if(price > hotel.getPrice())
                room.setPrice(price);
            if(description != null && !description.equals(""))
                room.setDescription(description);

            roomService.update(room);
            return DataReturn.success();
    }

    /*
    查看房间详情
     */
    @GetMapping("/detail")
    public DataReturn<Room> getDetail(@RequestParam("id")long id){
        Room room = roomService.queryById(id);
        if(room != null)
            return DataReturn.success(room);
        else
            return null;
    }

    @GetMapping("/getList")
    public DataReturn<Map<String,Room>> getList(@RequestParam("page")int page,@RequestParam("count")int count){
        if (page < 1 || count < 1)
            return null;
        Map roomMap = new HashMap();
        List roomList = roomService.queryAllByLimit(page, count);
        int totals = 0;
        for (Object room : roomList) {
            totals++;
            Integer total = new Integer(totals);
            String roomKey = total.toString();
            roomMap.put(roomKey, room);
        }
        roomMap.put("总条数", totals);
        return DataReturn.success(roomMap);
    }

    /*
    顾客查看空房
     */
    @GetMapping("/getLeisureList")
    public DataReturn<Map<String,Room>> getLeisureList(@RequestParam("page")int page, @RequestParam("count")int count){
        if (page < 1 || count < 1)
            return null;
        Map roomMap = new HashMap();
        List roomList = roomService.queryLeisureByLimit(page, count);
        int totals = 0;
        for (Object room : roomList) {
            totals++;
            Integer total = new Integer(totals);
            String roomKey = total.toString();
            roomMap.put(roomKey, room);
        }
        roomMap.put("总条数", totals);
        return DataReturn.success(roomMap);
    }

    /*
    预定房间
     */
    @GetMapping("/reserve")
    public DataReturn<Void> reserveRoom(@RequestParam("roomId")long roomId,@RequestParam("clientName")String clientName,@RequestParam("clientPhone")String clientPhone){
        Order order = new Order();
        Room room = roomService.queryById(roomId);
        long hotelId = room.getHotelId();
        Hotel hotel = hotelService.queryById(hotelId);
            try{
                if(redisUtil.tryLock("reserveLock",room.getUuid())) {
                    order.setClientName(clientName);
                    order.setClientPhone(clientPhone);
                    order.setHotelName(hotel.getHotelName());
                    order.setPrice(room.getPrice());
                    order.setRoomType(room.getType());
                    order.setRoomDescription(room.getDescription());
                    order.setRoomId(roomId);
                    order.setHotelAddress(hotel.getAddress());
                    order.setStatus(1);        //1代表等待商家处理中
                    orderService.insert(order);
                }
            }catch (Exception e){

            }finally {
                redisUtil.unlock("reserveLock",room.getUuid());
            }
        return DataReturn.success();
    }


}
