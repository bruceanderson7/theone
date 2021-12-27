package com.example.demo.service;

import com.example.demo.entity.HotelTemp;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 2:33 下午
 **/
public interface HotelTempService {
    int insert(HotelTemp hotelTemp);
    HotelTemp queryByHotelId(long id);
    int delete(long id);
}
