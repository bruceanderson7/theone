package com.example.demo.service;

import com.example.demo.entity.Areas;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 10:54 上午
 **/
public interface AreaService {
    List<Areas> getAreasList(String cityId);
}
