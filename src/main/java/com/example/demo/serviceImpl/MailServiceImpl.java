package com.example.demo.serviceImpl;

import com.example.demo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Bruce Shen
 * @DataTime： 2021/12/13 10:31 上午
 **/
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendMimeMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(sender);
            helper.setSubject(subject);
            helper.setTo(to);
            helper.setText(content, true);
            mailSender.send(message);
        } catch (MessagingException e) { }
    }

    @Override
    public void sendTemplateMail(String receiver, String title, String emailTemplate, Map<String, Object> dataMap)
            throws Exception {
        Context context = new Context();
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        String templateContent = templateEngine.process(emailTemplate, context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(sender);
        helper.setTo(receiver);
        helper.setSubject(title);
        helper.setText(templateContent, true);
        mailSender.send(message);
    }

    @Override
    public void sendMsgMail(String msg, String operator, String receiver, String title) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String emailTemplate = "msgTemplate";
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("msg", msg);
        dataMap.put("time", sdf.format(new Date()));
        dataMap.put("sender", operator);
        try {
            sendTemplateMail(receiver, title, emailTemplate, dataMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
