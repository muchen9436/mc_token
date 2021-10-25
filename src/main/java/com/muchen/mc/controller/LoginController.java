package com.muchen.mc.controller;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muchen.mc.entity.TUser;
import com.muchen.mc.service.TUserService;
import com.muchen.mc.utils.MD5Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登录测试
 * @author kong
 *
 */
@RestController
@RequestMapping("/acc/")
public class LoginController {
    @Resource
    private TUserService tUserService;

    // 测试登录  ---- http://localhost:8081/acc/doLogin?name=zhang&pwd=123456
    @RequestMapping("doLogin")
    public SaResult doLogin(String name, String pwd, String device) {

        pwd = MD5Utils.md5Encode(pwd);
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TUser::getUsername,name);
        TUser user = this.tUserService.getOne(queryWrapper);
        if (user.getPasswd().equals(pwd)) {
            StpUtil.login(user.getId(),device);
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            return SaResult.ok("登录成功").setData(tokenInfo);
        }
        return SaResult.error("账号密码错误");
    }

    // 查询登录状态  ---- http://localhost:8081/acc/isLogin
    @RequestMapping("isLogin")
    public SaResult isLogin() {
        return SaResult.ok("是否登录：" + StpUtil.isLogin());
    }

    // 查询 Token 信息  ---- http://localhost:8081/acc/tokenInfo
    @RequestMapping("tokenInfo")
    public SaResult tokenInfo() {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    // 测试注销  ---- http://localhost:8081/acc/logout
    @RequestMapping("logout")
    public SaResult logout() {
        StpUtil.logout();
        return SaResult.ok();
    }

    @RequestMapping("session")
    public SaResult session() {
        Object user = StpUtil.getSession().get("muchen");
        return SaResult.ok().setData(user);
    }
}
