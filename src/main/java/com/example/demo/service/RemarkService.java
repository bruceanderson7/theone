package com.example.demo.service;

import com.example.demo.entity.Remark;
import java.util.List;

/**
 * (Remark)表服务接口
 *
 * @author makejava
 * @since 2022-01-07 13:43:59
 */
public interface RemarkService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Remark queryById(long id);

    List<Remark> queryAllByLimit(int page, int count);

    /**
     * 新增数据
     *
     * @param remark 实例对象
     * @return 实例对象
     */
    int insert(Remark remark);

    /**
     * 修改数据
     *
     * @param remark 实例对象
     * @return 实例对象
     */
    void update(Remark remark);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    void deleteById(long id);

    List<Remark> queryGoodRemark(int page, int count);

    List<Remark> queryBadRemark(int page, int count);

    Remark queryByOrderId(long orderId);

}