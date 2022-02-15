package com.example.demo.controller;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Room;
import com.example.demo.service.*;
import com.example.demo.util.DataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/12 3:53 PM
 * 管理员类主要是辅助其他业务逻辑作为标识，具体业务逻辑很少
 **/
@RestController
@RequestMapping("/admin")
public class AdminController {
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
    将一个用户设为酒店管理员
     */
    @GetMapping("/setAdmin")
    public DataReturn<Void> setAdmin(@RequestParam("userId")long userId,@RequestParam("hotelId")long hotelId,@RequestParam("adminName")String adminName){
        Admin admin = new Admin();
        admin.setHotelId(hotelId);
        admin.setUserId(userId);
        admin.setName(adminName);
        adminService.insert(admin);
        return DataReturn.success();
    }

    /*
    将一个酒店管理员设为普通用户
     */
    @GetMapping("/deleteAdmin")
    public DataReturn<Void> deleteAdmin(@RequestParam("id")long id){
        adminService.deleteById(id);
        return DataReturn.success();
    }

    /*
    查看某酒店管理员列表
     */
    @GetMapping("/getList")
    public DataReturn<Map<String, Admin>> getList(@RequestParam("page")int page, @RequestParam("count")int count){
        if (page < 1 || count < 1)
            return null;
        Map adminMap = new HashMap();
        List adminList = adminService.queryAllByLimit(page, count);
        int totals = 0;
        for (Object admin : adminList) {
            totals++;
            Integer total = new Integer(totals);
            String adminKey = total.toString();
            adminMap.put(adminKey,admin);
        }
        adminMap.put("总条数", totals);
        return DataReturn.success(adminMap);
    }

    /*
    查看某酒店管理员信息
     */
    @GetMapping("/getDetail")
    public DataReturn<Admin> getDetail(@RequestParam("id")long id){
        Admin admin = adminService.queryById(id);
        return DataReturn.success(admin);
    }
}
