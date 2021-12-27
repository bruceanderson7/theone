package com.example.demo.mapper;

import com.example.demo.entity.Areas;
import com.example.demo.entity.Cities;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 10:53 上午
 **/
public interface AreaMapper {
    List<Areas> getAreasList(String cityId);
}
