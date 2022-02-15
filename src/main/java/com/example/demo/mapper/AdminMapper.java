package com.example.demo.mapper;

import com.example.demo.entity.Admin;

import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/5 3:10 PM
 **/
public interface AdminMapper {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Admin queryById(Long id);

    List<Admin> queryAllByLimit(int page, int count);

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    int insert(Admin admin);


    void deleteById(long id);
    Admin queryByHotelId(long id);

}
