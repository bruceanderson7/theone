package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Admin)实体类
 *
 * @author makejava
 * @since 2022-01-05 15:08:30
 */
@Data
public class Admin implements Serializable {
    private static final long serialVersionUID = 214578368536934372L;
    
    private long id;
    
    private String name;
    
    private long hotelId;
    
    private long userId;


}