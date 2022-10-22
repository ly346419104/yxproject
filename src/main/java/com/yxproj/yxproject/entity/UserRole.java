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
@TableName("t_user_role")
public class UserRole extends BaseEntity<UserRole> {

    @TableField("user_id")
    private String userId;

    @TableField("role_id")
    private String roleId;



}
