package com.muchen.mc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
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
@RequestMapping("/stp1/")
public class StpZhuJieController {
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
    @SaCheckRole("admin123")
    public SaResult role() {
        return SaResult.ok("有角色");
    }
}
