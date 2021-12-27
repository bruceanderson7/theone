package com.example.demo.serviceImpl;

import com.example.demo.entity.HotelTemp;
import com.example.demo.mapper.HotelTempMapper;
import com.example.demo.service.HotelTempService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 2:34 下午
 **/
@Service
public class HotelTempServiceImpl implements HotelTempService {
    @Resource
    private HotelTempMapper hotelTempMapper;

    public int insert(HotelTemp hotelTemp){
        int i = 0;
        if(hotelTemp == null)
            return i;
        else
            i = hotelTempMapper.insert(hotelTemp);
        return i;
    }

    public HotelTemp queryByHotelId(long id){
        return hotelTempMapper.queryByHotelId(id);
    }

    public int delete(long id){
    int i = hotelTempMapper.delete(id);
    return i;
    }


}
