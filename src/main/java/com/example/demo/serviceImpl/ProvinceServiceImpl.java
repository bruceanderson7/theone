package com.example.demo.serviceImpl;

import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;
import com.example.demo.mapper.ProvinceMapper;
import com.example.demo.service.ProvinceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 9:50 上午
 **/
@Service
public class ProvinceServiceImpl implements ProvinceService {
    @Resource
    private ProvinceMapper provinceMapper;

    public List<Provinces> getProvincesList(){
        return provinceMapper.getProvincesList();
    }


}
