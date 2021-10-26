package com.muchen.mc.service.impl;


import com.muchen.mc.entity.TUser;
import com.muchen.mc.service.MqMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 */
@Service
@Slf4j
public class MqMessageServiceImpl implements MqMessageService {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Override
    public void sendDelayMessage(TUser msg) {
        // 消息发送时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息发送时间为: {}", sdf.format(new Date()));
        // 设置发送时间，开始发送
        try {
            rabbitTemplate.convertAndSend("delay_exchange", "delay", msg,
                    message -> {
                        message.getMessageProperties().setExpiration(String.valueOf(5000));
                        return message;
                    });
        } catch (AmqpException e) {
            log.error("消息发送失败，请检查消息中间件是否正常");
        }
    }

    @Override
    public void sendMessage(TUser msg) {
        // 消息发送时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("消息发送时间为: {}", sdf.format(new Date()));
        // 设置发送时间，开始发送
        try {
            rabbitTemplate.convertAndSend("process_exchange", "delay", msg);
        } catch (AmqpException e) {
            log.error("消息发送失败，请检查消息中间件是否正常");
        }
    }

}




