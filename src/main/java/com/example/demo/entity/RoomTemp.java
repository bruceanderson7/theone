package com.example.demo.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (RoomTemp)实体类
 *
 * @author makejava
 * @since 2021-12-29 14:04:09
 */
@Data
public class RoomTemp implements Serializable {
    private static final long serialVersionUID = 209473587593449076L;
    
    private Long id;
    
    private String clientName;
    
    private String clientPhone;
    
    private Date createTime;

    private long roomId;
}