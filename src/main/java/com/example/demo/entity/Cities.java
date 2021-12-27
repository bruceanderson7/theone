package com.example.demo.entity;

import java.io.Serializable;

/**
 * 行政区域地州市信息表(Cities)实体类
 *
 * @author makejava
 * @since 2021-12-27 09:35:25
 */
public class Cities implements Serializable {
    private static final long serialVersionUID = -53882337589919480L;
    
    private Integer id;
    
    private String cityid;
    
    private String city;
    
    private String provinceid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid;
    }

}