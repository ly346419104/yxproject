package com.yxproj.yxproject.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Joiner;
import com.yxproj.yxproject.entity.UserRole;
import com.yxproj.yxproject.mapper.UserRoleMapper;
import com.yxproj.yxproject.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Override
    public Set<String> getRoles(String userId) {
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId, userId).eq(UserRole::getIsDeleted, false);
        List<UserRole> roles = super.list(userRoleLambdaQueryWrapper);
        Set<String> roleIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roleIds = roles.stream().map(role -> {
                return role.getRoleId();
            }).collect(Collectors.toSet());
            return roleIds;
        }else {
            return null;
        }
    }

    @Override
    public String getRole(String userId) {
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = Wrappers.lambdaQuery();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId, userId).eq(UserRole::getIsDeleted, false);
        List<UserRole> roles = super.list(userRoleLambdaQueryWrapper);
        Set<String> roleIds = new HashSet<>();
        if (!CollectionUtils.isEmpty(roles)) {
            roleIds = roles.stream().map(role -> {
                return role.getRoleId();
            }).collect(Collectors.toSet());
        }
        return transIdsToStr(roleIds.toArray());
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
