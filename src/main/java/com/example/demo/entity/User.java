package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:17 上午
 **/
@Data
public class User implements Serializable {
    private String uuid;

    private String name;

    private String password;

    private String email;

    private String phone;

    private int gender;

    private int role;

    private String scout;

    private String imgPath;
}
