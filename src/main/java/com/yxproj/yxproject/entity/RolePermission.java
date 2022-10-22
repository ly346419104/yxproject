package com.yxproj.yxproject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_role_permission")
public class RolePermission extends BaseEntity<RolePermission> {

    @TableField("role_id")
    private String roleId;

    @TableField("permission_id")
    private String permissionId;




}
