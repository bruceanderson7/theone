package com.example.demo.serviceImpl;

import com.example.demo.entity.Order;
import com.example.demo.entity.Remark;
import com.example.demo.mapper.RemarkMapper;
import com.example.demo.service.RemarkService;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/7 1:48 PM
 **/
@Service
public class RemarkServiceImpl implements RemarkService {
    @Resource
    private RemarkMapper remarkMapper;

    @Autowired
    private RedisUtil redisUtil;

    public List<Remark> queryAllByLimit(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return remarkMapper.queryAllByLimit(start,end);
    }

    public List<Remark> queryGoodRemark(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return remarkMapper.queryGoodRemark(start, end);
    }

    public List<Remark> queryBadRemark(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return remarkMapper.queryBadRemark(start, end);
    }

    public int insert(Remark remark){
        int i = 1;
        long id = remark.getId();
        redisUtil.set("评价"+id,remark);
        i = remarkMapper.insert(remark);
        return i;
    }

    public void update(Remark remark){
        long id = remark.getId();
        redisUtil.set("评价"+id,remark);
        remarkMapper.update(remark);
    }

    public void deleteById(long id){
        if(redisUtil.hasKey("评价"+id))
            redisUtil.del("评价"+id);
        remarkMapper.deleteById(id);
    }

    public Remark queryById(long id){
        if(redisUtil.hasKey("评价"+id))
            return (Remark)redisUtil.get("评价"+id);
        else
            return remarkMapper.queryById(id);
    }

    public Remark queryByOrderId(long orderId){
        return remarkMapper.queryByOrderId(orderId);
    }
}
