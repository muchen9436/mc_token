package com.muchen.mc.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaCheckSafe;
import cn.dev33.satoken.dao.SaTokenDaoRedis;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muchen.mc.entity.TUser;
import com.muchen.mc.service.TUserService;
import com.muchen.mc.utils.MD5Utils;
import com.muchen.mc.utils.StpUserUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 描述:
 *
 * @author MuChen
 * @create 2021-10-21 19:27
 */
@RestController
@RequestMapping("/user/")
public class UserController {
    @Resource
    private TUserService tUserService;

    // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
    @RequestMapping("doLogin")
    public String doLogin(String username, String password) {
        // 此处仅作模拟示例，真实项目需要从数据库中查询数据进行比对
        if("zhang".equals(username) && "123456".equals(password)) {
            StpUtil.login(10001);
            return "登录成功";
        }
        return "登录失败";
    }

    // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
    @RequestMapping("isLogin")
    public String isLogin() {
        return "当前会话是否登录：" + StpUtil.isLogin();
    }


    @RequestMapping("path_test")
    public String path_test() {
        return "hhhh";
    }

    @RequestMapping("getRedis")
    public SaResult getRedis(String token) {
        return SaResult.data(token);
    }

    @RequestMapping("token")
    @SaCheckRole("admin")
    public SaResult tokenTest() {
        return SaResult.data(1);
    }

    @RequestMapping("change")
    @SaCheckRole("admin")
    public SaResult change() {
        StpUtil.switchTo(2);
        Object loginId = StpUtil.getLoginId();
        System.out.println(loginId);
        StpUtil.login(loginId);
        String tokenValueByLoginId = StpUtil.getTokenValueByLoginId(loginId);
        return SaResult.data(tokenValueByLoginId);
    }

    // 二级认证：必须二级认证之后才能进入该方法
    @SaCheckSafe
    @RequestMapping("safeTest")
    public String add() {
        return "用户增加";
    }

    // 二级认证：
    @RequestMapping("safe")
    public String safe(String password) {
        long id = StpUtil.getLoginIdAsLong();
        TUser byId = this.tUserService.getById(id);
        String encrypt = MD5Utils.md5Encode(password);
        if(StrUtil.equals(byId.getPasswd(),encrypt)){
            StpUtil.openSafe(120);
            return "验证成功";
        }
        return "验证失败";
    }

    @RequestMapping("system")
    public SaResult system(String name, String pwd, String device) {

        pwd = MD5Utils.md5Encode(pwd);
        QueryWrapper<TUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TUser::getUsername,name);
        TUser user = this.tUserService.getOne(queryWrapper);
        if (user.getPasswd().equals(pwd)) {
            StpUserUtil.login(user.getId(),device);
            SaTokenInfo tokenInfo = StpUserUtil.getTokenInfo();
            StpUserUtil.getSession().set("muchen",user);
            String loginType = StpUserUtil.getLoginType();
            System.out.println(loginType);
            return SaResult.ok("登录成功").setData(tokenInfo);
        }
        return SaResult.error("账号密码错误");
    }


    @RequestMapping("systemTest")
    @SaCheckRole(value = "admin",type = "user")
    public String system() {
        SaTokenDaoRedis saTokenDaoRedis = new SaTokenDaoRedis();

        return "用户增加";
    }
}
