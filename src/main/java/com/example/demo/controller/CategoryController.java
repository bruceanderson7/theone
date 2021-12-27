package com.example.demo.controller;

import com.example.demo.entity.Areas;
import com.example.demo.entity.Cities;
import com.example.demo.entity.Provinces;
import com.example.demo.service.AreaService;
import com.example.demo.service.CityService;
import com.example.demo.service.ProvinceService;
import com.example.demo.util.DataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/27 9:36 上午
 **/
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ProvinceService provinceService;

    @Autowired
    private CityService cityService;

    @Autowired
    private AreaService areaService;

    @GetMapping("/getProvinces")
    public DataReturn<List<Provinces>> getProvinces(){
        List provincesList = provinceService.getProvincesList();
        return DataReturn.success(provincesList);
    }

    @GetMapping("/getCities")
    public DataReturn<List<Cities>> getCities(@RequestParam("provinceId") String provinceId){
        List citiesList = cityService.getCitiesList(provinceId);
        return DataReturn.success(citiesList);
    }

    @GetMapping("/getAreas")
    public DataReturn<List<Areas>> getAreas(@RequestParam("cityId")String cityId){
        List areasList = areaService.getAreasList(cityId);
        return DataReturn.success(areasList);
    }
}
