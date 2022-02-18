package com.example.demo.controller;

import com.example.demo.entity.Hotel;
import com.example.demo.entity.User;
import com.example.demo.service.MailService;
import com.example.demo.service.UserService;
import com.example.demo.util.DataReturn;
import com.example.demo.util.RedisUtil;
import com.example.demo.util.ResultCode;
import com.example.demo.util.SerialGenerator;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.demo.util.SerialGenerator.generateUUid;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:19 上午
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping("/register")
    public DataReturn<Void> UserRegister(@RequestParam("name") String name,
                                         @RequestParam("password") String password,
                                         @RequestParam("email") String email,
                                         @RequestParam(value = "verifyCode", required = false) String verifyCode,
                                         @RequestParam("phone") String phone,
                                         @RequestParam(value = "gender", required = false) Integer gender,
                                         @RequestParam(value = "imgPath", required = false) String imgPath) {
        if (name == null || name.equals("") || password == null || password.equals("") || email == null || email.equals("")||
                verifyCode == null || verifyCode.equals("") || phone == null || phone.equals("")) {
            return DataReturn.failure(ResultCode.REQUEST_NULL_EXCEPTION);
        } else {
            if (userService.queryByName(name)) {
                return DataReturn.failure("用户名已存在");
            }
            User user = new User();
            String uuid = generateUUid();
            user.setName(name);
            user.setUuid(uuid);
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            user.setPassword(md5Password);
            user.setEmail(email);
            user.setPhone(phone);
            if (gender == null)
                user.setGender(1);
            else
                user.setGender(gender);
            if (imgPath == null || imgPath.equals(""))
                user.setImgPath("default");
            else
                user.setImgPath(imgPath);
            user.setRole(1);
            user.setScout(0);
            switch (userService.verifyEmailAndCode(email, verifyCode)) {
                case -1:
                    return DataReturn.failure(ResultCode.REQUEST_EXPIRED);
                case 0:
                    return DataReturn.failure("验证码错误，请重新输入！");
                case 1:
                    int result = userService.insert(user);
                    //验证码用完要删除
                    redisUtil.del(RedisUtil.REDIS_VERIFY_PREFIX + email);
                    if (result != -1)
                        return DataReturn.success();
            }
        }
        return DataReturn.failure();
    }

    @PostMapping("/login")
    public DataReturn<?> userLogin(@RequestParam("name") String name,
                                   @RequestParam("password") String password) {
        //返回值DataReturn<?>代表无界通配符，即返回数据可以是object可以是string等等
        if (name != null && password != null) {
            //使用MD5加密算法
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            User user = userService.verifyUser(name, md5Password);
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(name,password);
            try{
                subject.login(usernamePasswordToken);
            }catch (UnknownAccountException e){
                return DataReturn.failure("用户名不存在");
            }catch (AuthenticationException e){
                return DataReturn.failure("没有权限");
            }
        }
        return DataReturn.failure(ResultCode.REQUEST_NULL_EXCEPTION);
    }

    @GetMapping("/adminLogin")
    public DataReturn<?> adminLogin(@RequestParam("name") String name,
                                    @RequestParam("password") String password) {
        if (name != null && password != null) {
            //使用MD5加密算法
            String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
            User user = userService.verifyAdmin(name, md5Password);
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getName(),user.getPassword());
            Subject subject = SecurityUtils.getSubject();
            try{
                subject.login(usernamePasswordToken);
            }catch (UnknownAccountException e){
                return DataReturn.failure("用户名不存在");
            }catch (AuthenticationException e){
                return DataReturn.failure("没有权限");
            }
        }
        return DataReturn.failure(ResultCode.REQUEST_NULL_EXCEPTION);
    }

    @GetMapping("/checkName")
    public DataReturn<Void> checkName(@RequestParam("name") String name) {
        if (!userService.queryByName(name)) {
            return DataReturn.success();
        }
        return DataReturn.failure("用户已存在，若忘记密码请申请重置！");
    }

    @GetMapping("/getVerifyCode")
    public DataReturn<?> getVerifyCode(@RequestParam("email") String email) throws Exception {
        if (email != null && !email.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String title = "[验证码]酒店管理系统";
            String emailTemplate = "verifyCode";
            String verifyCode = SerialGenerator.generateVerifyCode();
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("verifyCode", verifyCode);
            Date date = new Date();
            Date expireDate = new Date(date.getTime() + 480000);
            dataMap.put("time", sdf.format(expireDate));
            mailService.sendTemplateMail(email, title, emailTemplate, dataMap);
            return userService.bindEmailAndCode(email, verifyCode);
        }
        return DataReturn.failure(ResultCode.REQUEST_NULL_EXCEPTION);
    }

    @GetMapping("/disableUser")
    public DataReturn<Void> disableUser(@RequestParam("uuid") String uuid,
                                        @RequestParam("sender") String sender) throws Exception {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);

        user.setStatus(0);
        int result = userService.update(user);
        System.out.println(user);
        if (result != -1) {
            String msg = "您的账户已被管理员禁用，请联系后台管理员了解详情！\n";
            String receiver = userService.queryByUUid(uuid).getEmail();
            String title = "账户已被禁用";
            mailService.sendMsgMail(msg, sender, receiver, title);
            return DataReturn.success();
        }
        return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
    }

    @GetMapping("/enableUser")
    public DataReturn<Void> enableUser(@RequestParam("uuid") String uuid,
                                       @RequestParam("sender") String sender) throws Exception {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);

        user.setStatus(1);
        int result = userService.update(user);
        if (result != -1) {
            String msg = "您的账户已被管理员恢复使用，您现在可以登录系统了！\n";
            String receiver = userService.queryByUUid(uuid).getEmail();
            String title = "账户已恢复使用";
            mailService.sendMsgMail(msg, sender, receiver, title);
            return DataReturn.success();
        }
        return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
    }

    @GetMapping("/setAdmin")
    public DataReturn<Void> setAdmin(@RequestParam("uuid") String uuid,
                                     @RequestParam("sender") String sender) {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);

        user.setRole(1);
        int result = userService.update(user);
        System.out.println(user);
        if (result != -1) {
            String msg = "您的账户已被设置为管理员用户，您现在可以登录管理员后台系统了！\n" +
                    "请妥善保管和使用账号，并严格遵循《管理员账户使用规约》。\n" +
                    "如果您进行违规操作，您的账号将会被禁用，谢谢配合！\n";
            String receiver = userService.queryByUUid(uuid).getEmail();
            String title = "账户授权消息";
            mailService.sendMsgMail(msg, sender, receiver, title);
            return DataReturn.success();
        }
        return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
    }

    @GetMapping("/revokeAdmin")
    public DataReturn<Void> revokeAdmin(@RequestParam("uuid") String uuid,
                                        @RequestParam("sender") String sender) {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);

        user.setRole(0);
        int result = userService.update(user);
        System.out.println(user);
        if (result != -1) {
            String msg = "您的账户已被撤销管理员权限，您现在将不能登录管理员后台系统！\n" +
                    "若您对该操作有任何异议，请联系后台管理员询问详情。\n";
            String receiver = userService.queryByUUid(uuid).getEmail();
            String title = "账户授权消息";
            mailService.sendMsgMail(msg, sender, receiver, title);
            return DataReturn.success();
        }
        return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
    }

    @PostMapping("/updateUser")
    public DataReturn<Void> updateUser(@RequestParam(value = "uuid", required = false) String uuid,
                                       @RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "phone", required = false) String phone,
                                       @RequestParam(value = "gender", required = false) Integer gender,
                                       @RequestParam(value = "imgPath", required = false) String imgPath) {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);
        if (userService.queryByName(name) && !user.getName().equals(name)) {
            return DataReturn.failure("用户已存在，换个别的名字吧！");
        } else {
            user.setName(name);
            user.setPhone(phone);
            user.setGender(gender);
            user.setImgPath(imgPath);
            int result = userService.update(user);
            if (result != -1) {
                return DataReturn.success();
            }
            return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
        }
    }

    @PostMapping("/changePwdByOld")
    public DataReturn<Void> changePwdByOld(@RequestParam("uuid") String uuid,
                                           @RequestParam("pwdOld") String pwdOld,
                                           @RequestParam("pwdNew") String pwdNew) {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);
        //使用MD5加密算法
        String md5PwdOld = DigestUtils.md5DigestAsHex(pwdOld.getBytes());
        if (user.getPassword().equals(md5PwdOld)) {
            //使用MD5加密算法
            String md5PwdNew = DigestUtils.md5DigestAsHex(pwdNew.getBytes());
            user.setPassword(md5PwdNew);
            int result = userService.update(user);
            System.out.println(user);
            if (result != -1) {
                String msg = "您的账户: “" + user.getName() + "” 。刚刚通过旧密码修改了新密码。\n" +
                        "若非您本人操作，请登录本网站并使用邮箱修改密码。\n";
                String receiver = userService.queryByUUid(uuid).getEmail();
                String title = "账户安全";
                String sender = "酒店官方";
                mailService.sendMsgMail(msg, sender, receiver, title);
                return DataReturn.success();
            }
            return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
        }
        return DataReturn.failure("旧密码不正确，请检查输入！");
    }

    @PostMapping("/changePwdByEmail")
    public DataReturn<Void> changePwdByEmail(@RequestParam("name") String name,
                                             @RequestParam("email") String email,
                                             @RequestParam("verifyCode") String verifyCode,
                                             @RequestParam("pwdNew") String pwdNew) {
        User user = userService.getUserByName(name);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);
        if (user.getEmail().equals(email)) {
            switch (userService.verifyEmailAndCode(email, verifyCode)) {
                case -1:
                    return DataReturn.failure(ResultCode.REQUEST_EXPIRED);
                case 0:
                    return DataReturn.failure("验证码错误，请重新输入！");
                case 1:
                    //使用MD5加密算法
                    String md5PwdNew = DigestUtils.md5DigestAsHex(pwdNew.getBytes());
                    user.setPassword(md5PwdNew);
                    int result = userService.update(user);
                    redisUtil.del(RedisUtil.REDIS_VERIFY_PREFIX + email);
                    if (result != -1)
                        return DataReturn.success();

                    return DataReturn.failure(ResultCode.USER_UPDATE_EXCEPTION);
            }
        }
        return DataReturn.failure("用户名或邮箱输入错误，请重新检查！");
    }

    @GetMapping("/resetPwd")
    public DataReturn<?> resetPwd(@RequestParam("uuid") String uuid,
                                  @RequestParam("sender") String sender) throws Exception {
        User user = userService.queryByUUid(uuid);
        if (user == null)
            return DataReturn.failure(ResultCode.USER_QUERY_EXCEPTION);
        String newPwd = SerialGenerator.generateVerifyCode();
        //使用MD5加密算法
        String md5PwdNew = DigestUtils.md5DigestAsHex(newPwd.getBytes());
        user.setPassword(md5PwdNew);
        int result = userService.update(user);
        if(result != -1){
            String msg = "您的账户: “" + user.getName() + "” 刚刚被管理员重置了密码。\n" +
                    "重置后的新密码为：\n" + newPwd;
            String receiver = userService.queryByUUid(uuid).getEmail();
            String title = "账户安全";
            mailService.sendMsgMail(msg, sender, receiver, title);
            return DataReturn.success();
        }
        return DataReturn.failure(ResultCode.REQUEST_NULL_EXCEPTION);
    }

    @GetMapping("/userDetail")
    public DataReturn<User> userDetail(@RequestParam("uuid")String uuid){
        User user = userService.queryByUUid(uuid);
        if(user == null)
            return DataReturn.failure(user);

        else
            return DataReturn.success(user);
    }

    @GetMapping("/getUserList")
    public Map<String,User> getUserList(@RequestParam("page") int page, @RequestParam("count") int count){
        if(page < 1 || count < 1)
            return null;
        Map userMap = new HashMap();
        List userList = userService.queryAllByLimit(page,count);
        int totals = 0;
        for (Object user:userList) {
            totals++;
            Integer total = new Integer(totals);
            String userKey =total.toString();
            userMap.put(userKey,user);
        }
        userMap.put("总条数",totals);
        return userMap;
    }

    @GetMapping("/searchByName")
    public DataReturn<Map<String, Hotel>> searchHotel(@RequestParam("name")String name){
        Map userMap = new HashMap();
        if(name == null || name.equals("")) {
            userMap.put("msg","查询条件不能为空");
            return DataReturn.failure(userMap);
        }
        List hotelList = userService.searchByUserName(name);
        int totals = 0;
        for (Object hotel : hotelList) {
            totals++;
            Integer total = new Integer(totals);
            String hotelKey = total.toString();
            userMap.put(hotelKey, hotel);
        }
        userMap.put("总条数", totals);
        return DataReturn.success(userMap);
    }

}

