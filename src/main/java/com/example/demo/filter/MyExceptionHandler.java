package com.example.demo.filter;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/30 10:43 AM
 **/
@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    public String ErrorHandler(AuthorizationException e){
        return "没有通过权限认证";
    }
}
