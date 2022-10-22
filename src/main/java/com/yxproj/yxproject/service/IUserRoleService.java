package com.yxproj.yxproject.service;

import com.yxproj.yxproject.entity.UserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
public interface IUserRoleService extends IService<UserRole> {
    String getRole(String userId);
    Set<String> getRoles(String userId);
}
