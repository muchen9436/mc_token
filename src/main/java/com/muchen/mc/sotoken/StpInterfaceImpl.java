package com.muchen.mc.sotoken;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.muchen.mc.entity.TPermission;
import com.muchen.mc.entity.TRole;
import com.muchen.mc.entity.TRolePermission;
import com.muchen.mc.entity.TUserRole;
import com.muchen.mc.service.TPermissionService;
import com.muchen.mc.service.TRolePermissionService;
import com.muchen.mc.service.TRoleService;
import com.muchen.mc.service.TUserRoleService;
import com.muchen.mc.service.TUserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class  StpInterfaceImpl implements StpInterface {
    @Resource
    private TUserService tUserService;
    @Resource
    private TUserRoleService tUserRoleService;
    @Resource
    private TRoleService roleService;
    @Resource
    private TRolePermissionService tRolePermissionService;
    @Resource
    private TPermissionService permissionService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        QueryWrapper<TUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TUserRole::getUserId,loginId);
        List<TUserRole> roles = tUserRoleService.list(queryWrapper);
        List<Integer> collect = roles.stream().map(e -> e.getTid()).collect(Collectors.toList());

        // 获取用户权限集
        QueryWrapper<TRolePermission> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.in("rid",collect);
        List<TRolePermission> list1 = this.tRolePermissionService.list(queryWrapper2);
        List<Integer> collect1 = list1.stream().map(x -> x.getPid()).collect(Collectors.toList());

        QueryWrapper<TPermission> queryWrapper3 = new QueryWrapper<>();
        queryWrapper2.in("id",collect1);
        List<TPermission> list2 = this.permissionService.list(queryWrapper3);

        List<String> permissionSet = new ArrayList<>();
        for (TPermission p : list2) {
            permissionSet.add(p.getName());
        }
        return permissionSet;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        QueryWrapper<TUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(TUserRole::getUserId,loginId);
        List<TUserRole> roles = tUserRoleService.list(queryWrapper);
        List<Integer> collect = roles.stream().map(e -> e.getTid()).collect(Collectors.toList());

        QueryWrapper<TRole> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.in("id",collect);
        List<TRole> list = this.roleService.list(queryWrapper1);


        // 获取用户角色集

        List<String> roleSet = new ArrayList<>();
        for (TRole r : list) {
            roleSet.add(r.getName());
        }
        return roleSet;
    }
}
