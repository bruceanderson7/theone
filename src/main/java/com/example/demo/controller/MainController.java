package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/2/18 9:06 AM
 **/
@Controller
public class MainController {
    @RequestMapping("/login")
    public String mainLogin(){
    return "login2";
    }

    @RequestMapping("/register")
    public String mainRegister(){
        return "register";
    }
}
