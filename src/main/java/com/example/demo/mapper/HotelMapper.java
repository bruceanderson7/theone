package com.example.demo.mapper;

import com.example.demo.entity.Hotel;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/24 10:17 上午
 **/
public interface HotelMapper {
    Boolean queryByName(String hotelName);

    int insert(Hotel hotel);

    Hotel queryById(long id);

    int update(Hotel hotel);

    List<Hotel> queryAllByLimit(int page, int count);

}
