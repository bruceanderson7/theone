package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.Room;
import com.example.demo.service.HotelService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RoomService;
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


    /*
      酒店管理员接单
       */
    @GetMapping("/accept")
    public DataReturn<Void> acceptReverse(@RequestParam("reserveId") long id, @RequestParam("roomId") long roomId) {
        Order order = orderService.queryById(id);
        order.setStatus(2);   //2代表商家已接受订单
        orderService.update(order);

        Room room = roomService.queryById(roomId);
        room.setStatus(2);
        room.setClientName(order.getClientName());
        room.setClientPhone(order.getClientPhone());
        roomService.update(room);
        return DataReturn.success();
        //还需要添加发送邮件功能
    }

    /*
    酒店管理员拒绝接单
     */
    @GetMapping("/defuse")
    public DataReturn<Void> defuseReverse(@RequestParam("reserveId") long id) {
        Order order = orderService.queryById(id);
        order.setStatus(3);      //3代表商家拒绝接单
        orderService.update(order);
        return DataReturn.success();
        //还需要添加发送邮件功能
    }

    /*
    酒店管理员查看所有预订列表
     */
    @GetMapping("/getOrderList")
    public DataReturn<Map<String, Order>> getOrderList(@RequestParam("page") int page, @RequestParam("count") int count) {
        if (page < 1 || count < 1)
            return null;
        Map orderMap = new HashMap();
        List orderList = orderService.queryAllByLimit(page, count);
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

    @GetMapping("/test")
    public DataReturn<Void> justatest(HttpServletRequest request) throws URISyntaxException {
        String ip = getIp(request);
        Object object = getAddress(ip);
        System.out.println(object);
        return DataReturn.success();
    }
}
