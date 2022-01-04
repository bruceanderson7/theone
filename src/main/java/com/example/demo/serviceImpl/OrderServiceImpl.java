package com.example.demo.serviceImpl;

import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.service.OrderService;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 4:08 PM
 **/
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    private OrderMapper orderMapper;

    @Autowired
    private RedisUtil redisUtil;

    public List<Order> queryAllByLimit(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return orderMapper.queryAllByLimit(start,end);
    }

    public int insert(Order order){
        int i = 1;
        long id = order.getId();
        redisUtil.set("订单"+id,order);
        i = orderMapper.insert(order);
        return i;
    }

    public void update(Order order){
        long id = order.getId();
        redisUtil.set("订单"+id,order);
        orderMapper.update(order);
    }

    public void deleteById(long id){
        if(redisUtil.hasKey("订单"+id))
            redisUtil.del("订单"+id);
        orderMapper.deleteById(id);
    }

    public Order queryById(long id){
        if(redisUtil.hasKey("订单"+id))
            return (Order)redisUtil.get("订单"+id);
        else
            return orderMapper.queryById(id);
    }

}
