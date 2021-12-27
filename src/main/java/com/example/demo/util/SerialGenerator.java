package com.example.demo.util;

import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/12 5:08 下午
 **/
@Component
public class SerialGenerator {

//    @Resource
//    private UidGenerator defaultUidGenerator;

//    public void serialGenerate() {
//        // Generate UID
//        long uid = cachedUidGenerator.getUID();
//
//        // Parse UID into [Timestamp, WorkerId, Sequence]
//        // {"UID":"450795408770","timestamp":"2019-02-20 14:55:39","workerId":"27","sequence":"2"}
//        System.out.println(cachedUidGenerator.parseUID(uid));
//
//    }

    //生成6位随机数字
    public static String generateUUid(){
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    public static String generateVerifyCode(){
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

}

