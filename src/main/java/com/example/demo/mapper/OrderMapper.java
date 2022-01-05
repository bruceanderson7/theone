package com.example.demo.mapper;

import com.example.demo.entity.Order;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 4:10 PM
 **/
public interface OrderMapper {
    Order queryById(long id);
    List<Order> queryAllByLimit(int page, int count,String hotelName);
    int insert(Order order);
    void update(Order order);
    void deleteById(long id);
    List<Order> queryMyOrderList(long userId,int page,int count);
}
