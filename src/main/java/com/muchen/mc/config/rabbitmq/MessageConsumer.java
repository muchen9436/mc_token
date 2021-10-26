package com.muchen.mc.config.rabbitmq;

import com.muchen.mc.entity.TUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


/**
* 功能描述: 留言消费
* @date: 2021/7/28
* @author: lizz
*/
@Component
@RabbitListener(queues = "process_queue")
@Slf4j
public class MessageConsumer {

    @RabbitHandler
    public void messageConsumer(TUser message){
        // 接收到处理队列中的消息的，就是指定时间过期的消息
        // 这里处理每一条消息中的留言编号，去查询对应的留言状态，如果处于未回复状态，就结束用户的留言
        try {
            log.info("消息为: {}", message.toString());
        } catch (Exception e) {
            log.error("留言消息解析异常，请检查消息格式是否正确");
        }
    }
}
