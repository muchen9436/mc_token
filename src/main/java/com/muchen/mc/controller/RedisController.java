package com.muchen.mc.controller;

import cn.dev33.satoken.dao.SaTokenDaoRedis;
import cn.dev33.satoken.util.SaResult;
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
@RequestMapping("/redis/")
public class RedisController {
    @Resource
    SaTokenDaoRedis saTokenDaoRedis;

    @RequestMapping("set")
    public SaResult set(String key, String value) {
        saTokenDaoRedis.set(key,value,-1);
        return SaResult.ok();
    }

    @RequestMapping("get")
    public SaResult get(String key) {
        String s = saTokenDaoRedis.get(key);
        return SaResult.ok().set("value",s);
    }
}
