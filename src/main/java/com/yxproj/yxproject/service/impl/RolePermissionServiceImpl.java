package com.yxproj.yxproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Joiner;
import com.yxproj.yxproject.entity.RolePermission;
import com.yxproj.yxproject.mapper.RolePermissionMapper;
import com.yxproj.yxproject.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxproj.yxproject.vo.RolePermissionVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {
    @Override
    public boolean insert(RolePermissionVo rolePermissionVo) {
//        User user = BeanUtil.toBean(rolePermissionVo, User.class);
//        boolean save = super.save(user);
            List<RolePermission> userRoles = rolePermissionVo.getPermissions().stream().map(per -> {
                RolePermission role = new RolePermission();
                role.setIsDeleted(false);
                role.setRoleId(rolePermissionVo.getRoleId());
                role.setPermissionId(per);
                return role;
            }).collect(Collectors.toList());
            boolean b = super.saveBatch(userRoles);
            return b;
    }

    @Override
    public String getPermission(Set<String> roleIds) {
        LambdaQueryWrapper<RolePermission> rolePermissionLambdaQueryWrapper = Wrappers.lambdaQuery();
        rolePermissionLambdaQueryWrapper.in(RolePermission::getRoleId, roleIds).eq(RolePermission::getIsDeleted, false);
        Set<String> permissions = CollectionUtils.isEmpty(super.list(rolePermissionLambdaQueryWrapper)) ? null : super.list(rolePermissionLambdaQueryWrapper).stream().map(per -> {
            return per.getPermissionId();
        }).collect(Collectors.toSet());
        String permission = transIdsToStr(permissions.toArray());
        return permission;
    }
    private String transIdsToStr(Object[] objects) {
        if (objects == null || objects.length < 0) {
            return "";
        }
        Joiner joiner = Joiner.on(";");
        String join = joiner.join(objects);
        return join;
    }
}
