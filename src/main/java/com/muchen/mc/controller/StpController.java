package com.muchen.mc.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述:
 *
 * @author MuChen
 * @create 2021-10-21 19:43
 */
@RestController
@RequestMapping("/stp/")
public class StpController {
    // 测试是否有某个权限  ---- http://localhost:8081/stp/menu
    @RequestMapping("menu")
    public SaResult menu() {
        if(StpUtil.hasPermission("user-add")){
            return SaResult.ok("有权限");
        }
        return SaResult.error("无权限");
    }

    // 测试是否有某个角色  ---- http://localhost:8081/stp/role
    @RequestMapping("role")
    public SaResult role() {
        if(StpUtil.hasRole("admin")){
            return SaResult.ok("有角色");
        }
        return SaResult.error("无角色");
    }
}
