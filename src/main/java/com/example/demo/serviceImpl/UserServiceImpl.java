package com.example.demo.serviceImpl;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import com.example.demo.util.DataReturn;
import com.example.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:22 上午
 **/
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private RedisUtil redisUtil;

    @Resource
    private UserMapper userMapper;

    public Integer verifyEmailAndCode(String email, String verifyCode) {
        //读取邮箱对应的验证码，如果和输入的相同返回1，不相同返回0，如果获取不到则为过期-1
        String trueVerifyCode = (String) redisUtil.get(RedisUtil.REDIS_VERIFY_PREFIX + email);
        if (trueVerifyCode != null){
            if (trueVerifyCode.equals(verifyCode)){
                return 1;   //验证成功
            }
            return 0;   //验证失败
        }
        return -1;  //验证过期
    }

    public int insert(User user){
        int i = 0;
        if(user == null)
            return 0;
        else{
            redisUtil.set(user.getUuid(),user);
            i = userMapper.insert(user);
        }
        return i;
    }

    public Boolean queryByName(String name){
        if(name == "" || name == null)
            return true;
        if(userMapper.queryByName(name) != null)
            return true;
        else
            return false;
    }

    public User verifyUser(String name, String password) {
        if(!redisUtil.hasKey(name)){
            return userMapper.verifyUser(name, password);
        } else {
            User user = (User) redisUtil.get(name);
            if (user.getStatus() == 1 && user.getPassword().equals(password)){
                return (User) redisUtil.get(name);
            }
        }
        return null;
    }

    @Override
    public User verifyAdmin(String name, String password) {
        if(!redisUtil.hasKey(name)){
            return userMapper.verifyAdmin(name, password);
        } else {
            User user = (User) redisUtil.get(name);
            if (user.getStatus() == 1 && user.getRole() == 1 && user.getPassword().equals(password)){
                return (User) redisUtil.get(name);
            }
        }
        return null;
    }

    public DataReturn<String> bindEmailAndCode(String email, String verifyCode) {
        //写入redis中
        redisUtil.set(RedisUtil.REDIS_VERIFY_PREFIX + email, verifyCode);
        //设置验证码过期时间
        redisUtil.expire(RedisUtil.REDIS_VERIFY_PREFIX + email, RedisUtil.REDIS_VERIFY_EXPIRE);
        return DataReturn.success("验证码已发送，8分钟内有效，请注意查收! ");
    }

    public User queryByUUid(String uuid) {
        if(uuid.equals(""))
            return null;
        return userMapper.queryByUUid(uuid);
    }

    public int update(User user){
        int i = -1;
        if(user != null){
            redisUtil.set(user.getUuid(),user);
            i = userMapper.update(user);
        }
        return i;
    }

    public User getUserByName(String name){
        //Redis中不存在则去数据库查询
        if(!redisUtil.hasKey(name)){
            return userMapper.getUserByName(name);
        }
        //否则说明redis中存在，返回user
        return (User) redisUtil.get(name);
    }

    public List<User> queryAllByLimit(int page, int count){
        int start = page*count-count;
        int end = page*count;
        return userMapper.queryAllByLimit(start,end);
    }
}
