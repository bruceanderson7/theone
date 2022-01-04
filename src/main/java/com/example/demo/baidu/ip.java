package com.example.demo.baidu;

import com.example.demo.util.IpUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 2:48 PM
 **/
@RestController
public class ip {
    @RequestMapping(value = "/getIp", method = RequestMethod.POST)
    public static String getIp(HttpServletRequest request){
        String ip = IpUtil.getIpAddr(request);
        return ip;
    }
}
