package com.example.demo.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Remark)实体类
 *
 * @author makejava
 * @since 2022-01-07 13:43:59
 */
@Data
public class Remark implements Serializable {
    private static final long serialVersionUID = -62040725204437045L;
    
    private Long id;
    
    private Integer star;
    
    private String text;
    
    private String imgPath;
    
    private Long orderId;
    
    private Date createTime;

    private Integer anonymity;

    private String reply;

    private String clientName;

}