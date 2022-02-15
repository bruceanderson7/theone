package com.example.demo.service;

import com.example.demo.entity.Admin;
import java.util.List;

/**
 * (Admin)表服务接口
 *
 * @author makejava
 * @since 2022-01-05 15:08:30
 */
public interface AdminService {

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