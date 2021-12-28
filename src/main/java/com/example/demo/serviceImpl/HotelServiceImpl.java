package com.example.demo.serviceImpl;

import com.example.demo.entity.Hotel;
import com.example.demo.mapper.HotelMapper;
import com.example.demo.service.HotelService;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/24 10:19 上午
 **/
@Service
public class HotelServiceImpl implements HotelService {
    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private HotelMapper hotelMapper;

    public Boolean queryByName(String hotelName){
        if(hotelName == "" || hotelName == null)
            return true;
        if(hotelMapper.queryByName(hotelName) != null)
            return true;
        else
            return false;
    }

    public int insert(Hotel hotel){
        int i = 0;
        if(hotel == null)
            return i;
        else{
            redisUtil.set(hotel.getUuid(), hotel);
            i = hotelMapper.insert(hotel);
        }
        return i;
    }

    public Hotel queryById(long id){
        if(id < 1)
            return null;
        return hotelMapper.queryById(id);
    }

    public int update(Hotel hotel){
        int i = 0;
        if(hotel != null){
            redisUtil.set(hotel.getUuid(),hotel);
            i = hotelMapper.update(hotel);
        }
        return i;
    }

    public List<Hotel> queryAllByLimit(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return hotelMapper.queryAllByLimit(start,end);
    }

    public List<Hotel> queryReviewListByLimit(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return hotelMapper.queryReviewListByLimit(start, end);
    }

    public List<Hotel> searchByHotelName(String name){
        return hotelMapper.searchByHotelName(name);
    }

    public List<Hotel> screenHotel(String province,String city,String area,String traffic,String metro,String attraction,int star,String type){
        return hotelMapper.screenHotel(province, city, area, traffic, metro, attraction, star, type);
    }
}
