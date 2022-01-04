package com.example.demo.util;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.demo.config.okHttpConfig;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 2:59 PM
 **/
public class BaiduAddressUtil {
    @Resource
    private static RestTemplate restTemplate;

    private static final String AK = "7G2rA4Nh8B8lVRjFkoG6OOGbgigZ2aOd";

    public static Object getAddress(String ip) throws URISyntaxException {
        String url = "https://api.map.baidu.com/location/ip?ak="+AK+"&ip="+ip+"&coor=bd09ll";
        ResponseEntity<Map> ForEntity = restTemplate.getForEntity(new URI(url),Map.class);
        Map result = ForEntity.getBody();
        return result;
    }
}
