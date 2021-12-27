package com.example.demo.serviceImpl;

import com.example.demo.entity.Cities;
import com.example.demo.mapper.CityMapper;
import com.example.demo.service.CityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 10:47 上午
 **/
@Service
public class CityServiceImpl implements CityService {
    @Resource
    private CityMapper cityMapper;


    public List<Cities> getCitiesList(String provinceId){
        return cityMapper.getCitiesList(provinceId);
    }
}
