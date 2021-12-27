package com.example.demo.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.HotelTemp;
import com.example.demo.entity.User;
import com.example.demo.service.HotelService;
import com.example.demo.service.HotelTempService;
import com.example.demo.util.DataReturn;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.util.SerialGenerator.generateUUid;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/24 9:42 上午
 **/
@RestController
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelTempService hotelTempService;

    @GetMapping("/register")
    public DataReturn<Void> hotelRegister(@RequestParam("hotelName") String hotelName, @RequestParam("province") String province, @RequestParam("name") String name,
                                          @RequestParam("city") String city, @RequestParam("area") String area, @RequestParam(value = "traffic", required = false) String traffic,
                                          @RequestParam(value = "metro", required = false) String metro, @RequestParam(value = "attraction", required = false) String attraction
            , @RequestParam("price") double price, @RequestParam(value = "type") String type, @RequestParam(value = "imgPath", required = false) String imgPath) {
        if (hotelName == "" || hotelName == null || province == "" || province == null || city == "" || city == null || area == ""
                || area == null || price <= 0 || type == null || type == "") {
            return DataReturn.failure(ResultCode.REQUEST_NULL_EXCEPTION);
        } else {
            if (hotelService.queryByName(hotelName))
                return DataReturn.failure("用户名已存在");

            Hotel hotel = new Hotel();
            String uuid = generateUUid();
            hotel.setUuid(uuid);
            hotel.setName(hotelName);
            hotel.setProvince(province);
            hotel.setCity(city);
            hotel.setArea(area);
            hotel.setPrice(price);
            hotel.setType(type);
            hotel.setName(name);
            if (traffic == null || traffic == "")
                hotel.setTraffic("无");
            hotel.setTraffic(traffic);
            if (metro == null || metro == "")
                hotel.setMetro("无");
            hotel.setMetro(metro);
            if (attraction == null || attraction == "")
                hotel.setAttraction("无");
            hotel.setAttraction(attraction);
            if (imgPath == null || imgPath == "")
                hotel.setImgPath("default");
            hotel.setImgPath(imgPath);
            hotel.setStar(0);
            hotel.setStatus(1);    //审核中为1，审核成功为2，审核失败为3

            hotelService.insert(hotel);
            return DataReturn.success();
        }
    }

    @GetMapping("/enableHotel")
    public DataReturn<Void> reviewHotel(@RequestParam("id") long id) {
        Hotel hotel = hotelService.queryById(id);
        HotelTemp hotelTemp = hotelTempService.queryByHotelId(id);
        if(hotelTemp != null) {
            String traffic = hotelTemp.getTraffic();
            String metro = hotelTemp.getMetro();
            String attraction = hotelTemp.getAttraction();
            String type = hotelTemp.getType();
            String imgPath = hotelTemp.getImgpath();

            hotel.setTraffic(traffic);
            hotel.setMetro(metro);
            hotel.setAttraction(attraction);
            hotel.setType(type);
            hotel.setImgPath(imgPath);
            hotelTempService.delete(id);
        }
        if (hotel == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);
        hotel.setStatus(2);
        hotelService.update(hotel);
        return DataReturn.success();
    }

    @GetMapping("/disableHotel")
    public DataReturn<Void> disableHotel(@RequestParam("id") long id) {
        Hotel hotel = hotelService.queryById(id);
        hotelTempService.delete(id);
        if (hotel == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);
        hotel.setStatus(3);
        hotelService.update(hotel);
        return DataReturn.success();
    }

    @GetMapping("/getList")
    public DataReturn<Map<String, Hotel>> getList(@RequestParam("page") int page, @RequestParam("count") int count) {
        if (page < 1 || count < 1)
            return null;
        Map hotelMap = new HashMap();
        List hotelList = hotelService.queryAllByLimit(page, count);
        int totals = 0;
        for (Object hotel : hotelList) {
            totals++;
            Integer total = new Integer(totals);
            String hotelKey = total.toString();
            hotelMap.put(hotelKey, hotel);
        }
        hotelMap.put("总条数", totals);
        return DataReturn.success(hotelMap);
    }

    @GetMapping("/getDetail")
    public DataReturn<Hotel> getDetail(@RequestParam("id") long id) {
        Hotel hotel = hotelService.queryById(id);
        if (hotel == null)
            return DataReturn.failure(hotel);
        else
            return DataReturn.success(hotel);
    }

    /*
        @GetMapping("/search")
        public DataReturn<Map<String,Hotel>> search(){

        }

     */
    @GetMapping("/edit")
    public DataReturn<Void> editHotel(@RequestParam("id") long id, @RequestParam(value = "traffic", required = false) String traffic, @RequestParam(value = "metro", required = false) String metro,
                                      @RequestParam(value = "attraction", required = false) String attraction, @RequestParam(value = "type", required = false) String type,
                                      @RequestParam(value = "imgPath", required = false) String imgPath) {
        Hotel hotel = hotelService.queryById(id);
        HotelTemp hotelTemp = new HotelTemp();
        hotelTemp.setHotelId(id);
        if (hotel != null) {
            if (traffic == "" || traffic == null)
                traffic = hotel.getTraffic();
            hotelTemp.setTraffic(traffic);

            if (metro == "" || metro == null)
                metro = hotel.getMetro();
            hotelTemp.setMetro(metro);

            if (attraction == "" || attraction == null)
                attraction = hotel.getAttraction();
            hotelTemp.setAttraction(attraction);

            if (type == "" || type == null)
                type = hotel.getType();
            hotelTemp.setType(type);

            if (imgPath == "" || imgPath == null)
                imgPath = hotel.getImgPath();
            hotelTemp.setImgpath(imgPath);

            hotelTemp.setStatus(1);
            hotelTempService.insert(hotelTemp);
            return DataReturn.success();
        } else
            return DataReturn.failure();
    }

    @GetMapping("/getReviewList")
    public DataReturn<Map<String, Hotel>> getReviewList(int page, int count) {
        if (page < 1 || count < 1)
            return null;
        Map hotelMap = new HashMap();
        List hotelList = hotelService.queryReviewListByLimit(page, count);
        int totals = 0;
        for (Object hotel : hotelList) {
            totals++;
            Integer total = new Integer(totals);
            String hotelKey = total.toString();
            hotelMap.put(hotelKey, hotel);
        }
        hotelMap.put("总条数", totals);
        return DataReturn.success(hotelMap);
    }
}