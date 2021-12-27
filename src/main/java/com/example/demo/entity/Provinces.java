package com.example.demo.entity;

import java.io.Serializable;

/**
 * province(Provinces)实体类
 *
 * @author makejava
 * @since 2021-12-27 09:35:07
 */
public class Provinces implements Serializable {
    private static final long serialVersionUID = -76010649110867361L;
    
    private Integer id;
    
    private String provinceid;
    
    private String province;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

}