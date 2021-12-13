package com.example.demo.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/12 5:05 下午
 **/
@Getter
@AllArgsConstructor
public enum ResultCode {

    //成功提示码
    SUCCESS(20000, "成功"),

    //自定义失败信息
    FAILURE(50000, "失败"),

    //通用错误码 50001~50099
    PROGRAM_INSIDE_EXCEPTION(50001, "程序内部异常"),
    REQUEST_PARAM_ERROR(50002, "请求参数错误"),
    REQUEST_NULL_EXCEPTION(50003, "缺少请求参数"),
    REQUEST_EXPIRED(50004, "请求已过期"),

    //用户模块错误码 50100~50199
    USER_QUERY_EXCEPTION(50101, "找不到该用户"),
    USER_INSERT_EXCEPTION(50102, "添加用户失败"),
    USER_UPDATE_EXCEPTION(50103, "更新用户数据失败"),
    USER_DELETE_EXCEPTION(50104, "删除用户数据失败"),
    //商品模块错误码 50200~50299
    //订单模块错误码 50300~50399
    //店铺模块错误码，50400~50499
    SHOP_QUERY_EXCEPTION(50401,"找不到该店铺"),
    SHOP_INSERT_EXCEPTION(50402, "添加店铺失败"),
    SHOP_UPDATE_EXCEPTION(50403, "更新店铺数据失败"),
    SHOP_DELETE_EXCEPTION(50404, "删除店铺数据失败"),
    //收货地址模块错误码 50500~50599
    USER_ADDRESS_QUERY_EXCEPTION(50501,"找不到该收货地址"),
    USER_ADDRESS_INSERT_EXCEPTION(50502, "添加收货地址失败"),
    USER_ADDRESS_UPDATE_EXCEPTION(50503, "更新收货地址数据失败"),
    USER_ADDRESS_DELETE_EXCEPTION(50504, "删除收货地址数据失败"),
    ;
    private final Integer code;
    private final String message;

}

