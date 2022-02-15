package com.example.demo.job;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.Order;
import com.example.demo.service.HotelService;
import com.example.demo.service.OrderService;
import com.example.demo.service.RemarkService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 9:36 AM
 **/
public class UpdateStar extends QuartzJobBean implements Job {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private RemarkService remarkService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException{
        List<Hotel> hotelList = hotelService.queryAllByLimit(0,1000);
        for(Hotel hotel : hotelList){
            long sum = 0;
            int count = 0;
            List<Order> orderList = orderService.queryAllByLimit(1,1000,hotel.getHotelName());
            for(Order order : orderList){
                long orderId = order.getId();
                double star = remarkService.queryByOrderId(orderId).getStar();
                sum += star;
                count++;
            }
            double newStar = sum/count;
            hotel.setStar(newStar);
            hotelService.update(hotel);
            System.out.println("更新了一个酒店评分");
        }
    }
}
