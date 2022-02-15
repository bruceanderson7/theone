package com.example.demo.serviceImpl;

import com.example.demo.entity.Admin;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.mapper.AdminMapper;
import com.example.demo.service.AdminService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Admin)表服务实现类
 *
 * @author makejava
 * @since 2022-01-05 15:10:28
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminMapper adminMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Admin queryById(Long id) {
        return this.adminMapper.queryById(id);
    }


    @Override
    public List<Admin> queryAllByLimit(int page, int count) {
        int start = page*count-count;
        int end = page*count;
        return adminMapper.queryAllByLimit(start, end);
    }

    /**
     * 新增数据
     *
     * @param admin 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(Admin admin) {
        int i = 0;
        i = adminMapper.insert(admin);
        return i;
    }

    public void deleteById(long id){
        adminMapper.deleteById(id);
    }

    public Admin queryByHotelId(long id){
return null;
    }
}