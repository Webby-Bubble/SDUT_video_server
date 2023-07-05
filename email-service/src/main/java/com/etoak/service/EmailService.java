package com.etoak.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  /** 邮件发送对象 */
  @Autowired
  JavaMailSenderImpl mailSender;

  @RabbitListener(queues = "email_queue")
  public void consume(String msg) {
    System.out.println(msg);

    JSONObject jsonObject = JSONObject.parseObject(msg);

    // 封装邮件消息
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setSubject(jsonObject.getString("subject"));
    // 发件人
    mail.setFrom("714780629@qq.com");
    // 收件人
    mail.setTo(jsonObject.getString("receiver"));
    // 邮件内容
    mail.setText(jsonObject.getString("content"));

    // 下发邮件
    mailSender.send(mail);
    System.out.println("邮件发送结束！");
  }

}
