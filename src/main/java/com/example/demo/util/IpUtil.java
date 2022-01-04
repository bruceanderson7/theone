package com.example.demo.util;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/4 2:50 PM
 **/
public class IpUtil {
    public static String getIpAddr(HttpServletRequest request){
        String ipAddress = null;
        try{
            ipAddress = request.getHeader("x-forwarder-for");
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("Proxy-Client-IP");
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if(ipAddress.equals("127.0.0.1")){
                    InetAddress inet = null;
                    try{
                        inet = InetAddress.getLocalHost();
                    }catch(UnknownHostException e){
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            if(ipAddress !=null && ipAddress.length() > 15){
                if(ipAddress.indexOf(",") > 0){
                    ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));
                }
            }
        }catch (Exception e){
            ipAddress = "";
        }
        return ipAddress;
    }
}
