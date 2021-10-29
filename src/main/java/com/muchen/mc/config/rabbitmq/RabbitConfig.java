/**
 * @Copyright (c) 2018/8/17, Lianjia Group All Rights Reserved.
 */
package com.muchen.mc.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 功能描述:  所有声明的这些queue，exchange，以及绑定关系会在第一次发送消息时建立在rabbitmq中。
 *
 * @date: 2021/7/28
 * @author: lizz
 */
@Configuration
public class RabbitConfig {

    /**
     * 功能描述: 声明交换器
     *
     * @date: 2021/10/29
     * @author: MuChen
     */
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.directExchange("muchen_exchange").durable(true).build();
    }

    /**
     * 功能描述: 声明队列
     *
     * @date: 2021/10/29
     * @author: MuChen
     */
    @Bean
    public Queue queue() {
        return QueueBuilder.durable("muchen_queue").build();
    }

    @Bean
    public Queue queue1() {
        return QueueBuilder.durable("muchen_queue1").build();
    }

    /**
     * 功能描述: 队列绑定交换器
     *
     * @date: 2021/10/29
     * @author: MuChen
     */
    @Bean
    public Binding biding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("muchen").noargs();
    }

    @Bean
    public Binding biding1() {
        return BindingBuilder.bind(queue1()).to(exchange()).with("muchen1").noargs();
    }


    // 声明延迟队列   消息都放在这个队列里面,每个消息都可以设定一个过期时间，过期后会放到delayQ声明的处理过期消息的交换机

    @Bean
    public Exchange delayC() {
        return ExchangeBuilder.directExchange("delayC").durable(true).build();
    }

    @Bean
    public Queue delayQ() {
        return QueueBuilder.durable("delayQ").withArgument("x-dead-letter-exchange", "delayC1")
                .withArgument("x-dead-letter-routing-key", "delay").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(delayQ()).to(delayC()).with("delay").noargs();
    }


    // 下面是专门负责处理过期的消息交换机和队列

    @Bean
    public Exchange delayCC() {
        return ExchangeBuilder.directExchange("delayC1").durable(true).build();
    }

    @Bean
    public Queue delayQQ() {
        return QueueBuilder.durable("delayQQ").build();
    }

    @Bean
    public Binding bin1() {
        return BindingBuilder.bind(delayQQ()).to(delayCC()).with("delay").noargs();
    }


    // 过期队列，放到这个队列下的消息会根据队列的过期时间消费

    /**
     * 过期队列，指拥有固定过期时间的队列，其中的消息，每过2秒过期一次，全部转入到指定的x-dead-letter-xx 参数指定的交换器和路由键的队列中。
     *
     * @return org.springframework.amqp.core.Queue
     * @author wilson wei
     * @date 10:36 2018/8/19
     */
    @Bean
    public Queue delayQueueQueue() {
        return QueueBuilder.durable("overdueQ")
                .withArgument("x-dead-letter-exchange", "muchen_exchange")
                .withArgument("x-dead-letter-routing-key", "muchen")
                .withArgument("x-message-ttl", 2000)
                .build();
    }

    @Bean
    public Binding binding1(){
        return BindingBuilder.bind(delayQueueQueue()).to(exchange()).with("overdue").noargs();
    }


}
