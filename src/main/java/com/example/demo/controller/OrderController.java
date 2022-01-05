package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Hotel;
import com.example.demo.entity.Order;
import com.example.demo.entity.Room;
import com.example.demo.service.*;
import com.example.demo.util.DataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.baidu.ip.getIp;
import static com.example.demo.util.BaiduAddressUtil.getAddress;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 11:47 AM
 **/
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AdminService adminService;


    /*
      酒店管理员接单
       */
    @GetMapping("/accept")
    public DataReturn<Void> acceptReverse(@RequestParam("reserveId") long id, @RequestParam("roomId") long roomId,@RequestParam("userId")String uuid) {
        Order order = orderService.queryById(id);
        order.setStatus(2);   //2代表商家已接受订单
        orderService.update(order);

        Room room = roomService.queryById(roomId);
        room.setStatus(2);
        room.setClientName(order.getClientName());
        room.setClientPhone(order.getClientPhone());
        roomService.update(room);
        Hotel hotel = hotelService.queryById(room.getHotelId());
        String msg = "您的订单已被接受\n";
        String receiver = userService.queryByUUid(uuid).getEmail();
        String title = "商家已接单";
        mailService.sendMsgMail(msg, hotel.getHotelName(), receiver, title);
        return DataReturn.success();
    }

    /*
    酒店管理员拒绝接单
     */
    @GetMapping("/defuse")
    public DataReturn<Void> defuseReverse(@RequestParam("reserveId") long id,@RequestParam("roomId") long roomId,@RequestParam("userId")String uuid) {
        Order order = orderService.queryById(id);
        order.setStatus(3);      //3代表商家拒绝接单
        Room room = roomService.queryById(roomId);
        orderService.update(order);

        Hotel hotel = hotelService.queryById(room.getHotelId());
        String msg = "您的订单被商家拒绝\n";
        String receiver = userService.queryByUUid(uuid).getEmail();
        String title = "商家拒绝接单";
        mailService.sendMsgMail(msg, hotel.getHotelName(), receiver, title);
        return DataReturn.success();
    }

    /*
    酒店管理员查看所有预订列表
     */
    @GetMapping("/getOrderList")
    public DataReturn<Map<String, Order>> getOrderList(@RequestParam("page") int page, @RequestParam("count") int count, @RequestParam("adminId")long adminId) {
        if (page < 1 || count < 1)
            return null;
        Map orderMap = new HashMap();
        Admin admin = adminService.queryById(adminId);
        Hotel hotel = hotelService.queryById(admin.getHotelId());
        List orderList = orderService.queryAllByLimit(page, count,hotel.getHotelName());
        int totals = 0;
        for (Object order : orderList) {
            totals++;
            Integer total = new Integer(totals);
            String roomKey = total.toString();
            orderMap.put(roomKey, order);
        }
        orderMap.put("总条数", totals);
        return DataReturn.success(orderMap);
    }

    @GetMapping("/orderDetail")
    public DataReturn<Order> orderDetail(@RequestParam("orderId")long orderId) {
            Order order = orderService.queryById(orderId);
            return DataReturn.success(order);
    }

    @GetMapping("/myOrderList")
    public DataReturn<Map<String,Order>> getMyOrder(@RequestParam("userId")long userId,@RequestParam("page")int page,@RequestParam("count")int count){
        if (page < 1 || count < 1)
            return null;
        Map orderMap = new HashMap();
        List orderList = orderService.queryMyOrderList(userId,page, count);
        int totals = 0;
        for (Object order : orderList) {
            totals++;
            Integer total = new Integer(totals);
            String roomKey = total.toString();
            orderMap.put(roomKey, order);
        }
        orderMap.put("总条数", totals);
        return DataReturn.success(orderMap);
    }

}
