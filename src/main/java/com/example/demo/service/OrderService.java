package com.example.demo.service;

import com.example.demo.entity.Order;
import java.util.List;

/**
 * (Order)表服务接口
 *
 * @author makejava
 * @since 2022-01-04 11:46:04
 */
public interface OrderService {
    Order queryById(long id);
    List<Order> queryAllByLimit(int page, int count, String hotelName);
    int insert(Order order);
    void update(Order order);
    void deleteById(long id);
    List<Order> queryMyOrderList(long userId,int page,int count);

}