package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.example.demo.util.DataReturn;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:21 上午
 **/
public interface UserMapper {
    Boolean queryByName(String name);

    Integer verifyEmailAndCode(String email, String verifyCode);

    int insert(User user);

    User verifyUser(String name, String password);

    User verifyAdmin(String name, String password);

    DataReturn<String> bindEmailAndCode(String email, String verifyCode);

    int update(User user);

    User queryByUUid(String uuid);

    User getUserByName(String name);

    List<User> queryAllByLimit(int page, int count);
}
