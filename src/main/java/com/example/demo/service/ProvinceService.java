package com.example.demo.service;

import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 9:50 上午
 **/
public interface ProvinceService {
    List<Provinces> getProvincesList();

}
