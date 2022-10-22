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
@TableName("t_permissions")
public class Permissions extends BaseEntity<Permissions> {

    /**
     * 权限名称
     */
    @TableField("permission_name")
    private String permissionName;

    /**
     * 权限
     */
    @TableField("target_name")
    private String targetName;

    /**
     * 注释
     */
    @TableField("display_name")
    private String displayName;

    /**
     * 类型0角色权限1菜单权限
     */
    @TableField("permission_type")
    private Integer permissionType;



}
