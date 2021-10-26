package com.muchen.mc.controller;

import cn.dev33.satoken.util.SaResult;
import com.muchen.mc.entity.TUser;
import com.muchen.mc.service.MqMessageService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述:
 * \
 *
 * @author MuChen
 * @create 2021-10-26 10:54
 */
@RestController
@RequestMapping("/rabbitmq/")
public class RabbitMQController {
    @Resource
    private MqMessageService mqMessageService;

    @RequestMapping("send")
    public SaResult send(@RequestBody TUser user) {
        mqMessageService.sendMessage(user);

        return SaResult.ok();
    }

    @RequestMapping("sendDelay")
    public SaResult sendDelay(@RequestBody TUser user) {
        mqMessageService.sendDelayMessage(user);
        return SaResult.ok();
    }
}
