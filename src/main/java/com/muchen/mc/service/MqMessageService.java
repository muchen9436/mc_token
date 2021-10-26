package com.muchen.mc.service;


import com.muchen.mc.entity.TUser;

/**
 *
 */
public interface MqMessageService {

    /**
    * 功能描述:  发送延时消息，每个消息都自己有自己的过期时间
    * @date: 2021/7/28
    * @author: lizz
     * @param msg
    */
    void sendDelayMessage(TUser msg);

    void sendMessage(TUser msg);
}
