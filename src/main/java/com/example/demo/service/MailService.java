package com.example.demo.service;

import java.util.Map;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:30 上午
 **/
public interface MailService {
    /**
     * @param receiver 收件人
     * @param title 标题
     * @param content 内容
     * @Description 发送多媒体类型的邮件
     */
    void sendMimeMail(String receiver, String title, String content);

    /**
     * @param receiver 收件人
     * @param title 标题
     * @param emailTemplate 模板内容
     * @param dataMap 模板内的参数
     * @Description 发送thymeleaf模板的邮件
     */
    void sendTemplateMail(String receiver, String title, String emailTemplate, Map<String, Object> dataMap)
            throws Exception;

    /**
     * @param msg 消息
     * @param sender 发送人
     * @Description 消息推送邮件封装
     */
    void sendMsgMail(String msg, String sender, String receiver, String title);

}
