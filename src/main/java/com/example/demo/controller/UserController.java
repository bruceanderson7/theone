package com.example.demo.controller;

import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import com.example.demo.util.DataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:19 上午
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @GetMapping("/register")
    public DataReturn<Void> UserRegister(@RequestParam("name") String name,
                                         @RequestParam("password") String password,
                                         @RequestParam("email") String email,
                                         @RequestParam("verifyCode") String verifyCode,
                                         @RequestParam("phone") String phone,
                                         @RequestParam("gender", required = false) Integer gender,
                                         @RequestParam(value = "imgPath", required = false) String imgPath){

    }
}
