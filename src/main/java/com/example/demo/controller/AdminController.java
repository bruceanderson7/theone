package com.example.demo.controller;

import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    /*
    将一个酒店管理员设为普通用户
     */

    /*
    查看某酒店管理员列表
     */
}
