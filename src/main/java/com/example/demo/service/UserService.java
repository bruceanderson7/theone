package com.example.demo.service;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.util.DataReturn;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:21 上午
 **/
public interface UserService {
    Boolean queryByName(String name);

    Integer verifyEmailAndCode(String email, String verifyCode);

    int insert(User user);
    //用户登录验证
    User verifyUser(String name, String password);
    //管理员登录验证
    User verifyAdmin(String name, String password);

    DataReturn<String> bindEmailAndCode(String email, String verifyCode);

    User queryByUUid(String uuid);

    int update(User user);

    User getUserByName(String name);

    List<User> queryAllByLimit(int page, int count);

    List<User> searchByUserName(String name);
}
