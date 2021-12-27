package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Hotel)实体类
 *
 * @author makejava
 * @since 2021-12-24 09:38:28
 */
@Data
public class Hotel implements Serializable {
    private static final long serialVersionUID = 462239595701177680L;
    
    private Long id;
    
    private String uuid;
    
    private String name;    //酒店持有者名字
    
    private String area;
    
    private String province;
    
    private String city;
    
    private String traffic;
    
    private String metro;
    
    private String attraction;
    
    private double star;
    
    private double price;
    
    private Integer status;
    
    private String type;

    private String imgPath;

    private String hotelName;


    }