package com.example.demo.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Order)实体类
 *
 * @author makejava
 * @since 2022-01-04 11:46:04
 */
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 827735166632883876L;
    
    private Long id;
    
    private String clientName;
    
    private String clientPhone;
    
    private Object price;
    
    private String hotelName;
    
    private String hotelAddress;
    
    private Date createTime;
    
    private String roomType;
    
    private String roomDescription;
    
    private Integer status;

    private long roomId;

    private long userId;

}