package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Room)实体类
 *
 * @author makejava
 * @since 2021-12-28 14:55:17
 */
@Data
public class Room implements Serializable {
    private static final long serialVersionUID = 408406737569501288L;

    private Long id;

    private Long hotelId;

    private Integer roomId;

    private String type;

    private Integer status;

    private String imgPath;

    private double price;

    private String description;

    private String clientName;

    private String clientPhone;

    private String uuid;

}