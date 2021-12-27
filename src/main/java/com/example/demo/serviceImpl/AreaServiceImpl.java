package com.example.demo.serviceImpl;

import com.example.demo.entity.Areas;
import com.example.demo.mapper.AreaMapper;
import com.example.demo.service.AreaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 10:55 上午
 **/
@Service
public class AreaServiceImpl implements AreaService {
    @Resource
    private AreaMapper areaMapper;

    public List<Areas> getAreasList(String cityId){
        return areaMapper.getAreasList(cityId);
    }

}
