package com.example.demo.util;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/12 5:04 下午
 **/
@Data
public class DataReturn<T> implements Serializable {

    private static final long serialVersionUID = -8037171286104362012L;

    private Integer code;

    private String message;

    private T data;

    /**
     * 成功，无数据返回
     */
    public static DataReturn<Void> success() {
        DataReturn<Void> dataReturn = new DataReturn<>();
        dataReturn.setCode(ResultCode.SUCCESS.getCode());
        dataReturn.setMessage(ResultCode.SUCCESS.getMessage());
        return dataReturn;
    }

    /**
     * 成功，有返回数据（泛型V）
     */
    public static <V> DataReturn<V> success(V data) {
        DataReturn<V> dataReturn = new DataReturn<>();
        dataReturn.code = ResultCode.SUCCESS.getCode();
        dataReturn.message = ResultCode.SUCCESS.getMessage();
        dataReturn.data = data;
        return dataReturn;
    }

    /**
     * 失败，直接返回失败
     * (50000, "失败")
     */
    public static DataReturn<Void> failure() {
        DataReturn<Void> dataReturn = new DataReturn<>();
        dataReturn.setCode(ResultCode.FAILURE.getCode());
        dataReturn.setMessage(ResultCode.FAILURE.getMessage());
        return dataReturn;
    }

    public static <T> DataReturn<T> failure(T data){
        DataReturn<T> dataReturn = new DataReturn<>();
        dataReturn.setCode(ResultCode.FAILURE.getCode());
        dataReturn.setMessage(ResultCode.FAILURE.getMessage());
        dataReturn.setData(data);
        return dataReturn;
    }

    /**
     * 失败，返回自定义失败信息
     * (50000, "亲，服务器返回 失败 哦")
     */
    public static DataReturn<Void> failure(String message) {
        DataReturn<Void> dataReturn = new DataReturn<>();
        dataReturn.setCode(ResultCode.FAILURE.getCode());
        dataReturn.setMessage(message);
        return dataReturn;
    }

    /**
     * 失败，返回已定义的状态码
     * (50001, "程序内部异常")
     */
    public static DataReturn<Void> failure(ResultCode resultCode) {
        DataReturn<Void> dataReturn = new DataReturn<>();
        dataReturn.setCode(resultCode.getCode());
        dataReturn.setMessage(resultCode.getMessage());
        return dataReturn;
    }

}

