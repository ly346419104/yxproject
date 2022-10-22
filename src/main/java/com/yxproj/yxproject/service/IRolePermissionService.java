package com.yxproj.yxproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxproj.yxproject.entity.RolePermission;
import com.yxproj.yxproject.vo.RolePermissionVo;

import java.util.Set;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
public interface IRolePermissionService extends IService<RolePermission> {
    String getPermission(Set<String> roleIds);

    boolean insert(RolePermissionVo rolePermissionVo);
}
