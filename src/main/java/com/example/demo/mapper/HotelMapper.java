package com.example.demo.mapper;

import com.example.demo.entity.Hotel;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/24 10:17 上午
 **/
public interface HotelMapper {
    Boolean queryByName(String hotelName);

    Hotel queryEntityByName(String hotelName);

    int insert(Hotel hotel);

    Hotel queryById(long id);

    int update(Hotel hotel);

    Hotel getMyHotel(String name);

    List<Hotel> queryAllByLimit(int page, int count);

    List<Hotel> queryReviewListByLimit(int page, int count);

    List<Hotel> searchByHotelName(String name);

    List<Hotel> screenHotel(String province,String city,String area,String traffic,String metro,String attraction,int star,String type);

    List<Hotel> screenAndSearch(String hotelName,String province,String city,String area,String traffic,String metro,String attraction,int star,String type);
}
