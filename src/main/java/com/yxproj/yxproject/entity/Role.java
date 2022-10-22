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
@TableName("t_role")
public class Role extends BaseEntity<Role> {

    /**
     * 角色名称
     */
    @TableField("role_name")
    private String roleName;




}
