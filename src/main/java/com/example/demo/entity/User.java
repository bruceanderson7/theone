package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Set;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:17 上午
 **/
@Data
public class User implements Serializable {
    private int id;

    private String uuid;

    private String name;

    private String password;

    private String email;

    private String phone;

    private int gender;

    private int role;

    private int scout;

    private int status;

    private String imgPath;

}
