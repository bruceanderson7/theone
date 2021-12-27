package com.example.demo.entity;

import java.io.Serializable;

/**
 * 行政区域县区信息表(Areas)实体类
 *
 * @author makejava
 * @since 2021-12-27 09:35:51
 */
public class Areas implements Serializable {
    private static final long serialVersionUID = -89607635658876750L;
    
    private Integer id;
    
    private String areaid;
    
    private String area;
    
    private String cityid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

}