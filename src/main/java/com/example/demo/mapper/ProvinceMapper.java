package com.example.demo.mapper;

import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 9:49 上午
 **/
public interface ProvinceMapper {
    List<Provinces> getProvincesList();
}
