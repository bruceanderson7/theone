package com.example.demo.service;

import com.example.demo.entity.Cities;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 10:46 上午
 **/
public interface CityService {
    List<Cities> getCitiesList(String provinceId);
}
