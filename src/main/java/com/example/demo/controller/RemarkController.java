package com.example.demo.controller;

import com.example.demo.entity.Order;
import com.example.demo.entity.Remark;
import com.example.demo.entity.Room;
import com.example.demo.service.*;
import com.example.demo.util.DataReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Bruce Shen
 * @DataTime： 2022/1/7 1:46 PM
 **/
@RestController
@RequestMapping("/remark")
public class RemarkController {
    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private RemarkService remarkService;

    @Autowired
    private AdminService adminService;

    /*
    新建评论
     */
    @GetMapping("/createRemark")
    public DataReturn<Void> createRemark(@RequestParam("orderId")long orderId, @RequestParam("star")int star, @RequestParam("text")String text, @RequestParam(value = "imgPath",required = false)String imgPath
            , @RequestParam("anonymity")int anonymity){
        Order order = orderService.queryById(orderId);
        Remark remark = new Remark();
        if(order.getStatus() != 4)   //4代表待评价，5代表已评价
            return DataReturn.failure("您已评价过该订单");
        if(text.length() < 15)
            return DataReturn.failure("评价字数不能少于15字");
        if(anonymity == 1) //1代表匿名评论，2代表实名评论
            remark.setClientName("匿名用户");
        else
            remark.setClientName(order.getClientName());

        remark.setStar(star);
        remark.setImgPath(imgPath);
        remark.setText(text);
        remark.setOrderId(orderId);
        remark.setAnonymity(anonymity);
        remark.setReply("");
        remarkService.insert(remark);
        return DataReturn.success();
    }

    /*
    删除评论
     */
    @GetMapping("/deleteRemark")
    public DataReturn<Void> deleteRemark(@RequestParam("remarkId")long remarkId){
        remarkService.deleteById(remarkId);
        return DataReturn.success();
    }

    /*
    编辑评论
     */
    @GetMapping("/editRemark")
    public DataReturn<Void> editRemark(@RequestParam("remarkId")long remarkId,@RequestParam(value = "text",required = false)String text,@RequestParam(value = "imgPath",required = false)String imgPath,
                                       @RequestParam("star")int star){
        Remark remark = remarkService.queryById(remarkId);
        if(text.length() < 15)
            return DataReturn.failure("评价不能少于15字");
        remark.setImgPath(imgPath);
        remark.setStar(star);
        remarkService.update(remark);
        return DataReturn.success();
    }

    /*
    查看所有评论列表（查看所有、查看好评、查看差评，根据传参决定)
     */
    @GetMapping("/getList")
    public DataReturn<Map<String, Room>> getLeisureList(@RequestParam("page")int page, @RequestParam("count")int count,@RequestParam("type")int type){
        if (page < 1 || count < 1)
            return null;
        Map remarkMap = new HashMap();
        List<Remark> remarkList = new ArrayList<>();
        //1代表好评（评分4或者5的）,2代表差评（评分4以下的），3代表全部
        if(type == 1)
            remarkList = remarkService.queryGoodRemark(page, count);
        if(type == 2)
            remarkList = remarkService.queryBadRemark(page, count);
        if(type == 3)
            remarkList=remarkService.queryAllByLimit(page,count);
        int totals = 0;
        for (Object remark : remarkList) {
            totals++;
            Integer total = new Integer(totals);
            String remarkKey = total.toString();
            remarkMap.put(remarkKey, remark);
        }
        remarkMap.put("总条数", totals);
        return DataReturn.success(remarkMap);
    }

    /*
    酒店管理员回复评论
     */
    @GetMapping("/reply")
    public DataReturn<Void> replyRemark(@RequestParam("remarkId")long remarkId, @RequestParam("reply")String reply){
        if(reply.length() < 15)
            return DataReturn.failure("回复不能少于15字");
        Remark remark = remarkService.queryById(remarkId);
        remark.setReply(reply);
        remarkService.update(remark);
        return DataReturn.success();
    }

    /*
    查看评论具体
     */
    @GetMapping("/detail")
    public DataReturn<Remark> getRemark(@RequestParam("remarkId")long remarkId){
        Remark remark = remarkService.queryById(remarkId);
        return DataReturn.success(remark);
    }



}
