package com.yxproj.yxproject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author ly
 * @since 2022-10-11
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("tbl_user")
public class User extends BaseEntity<User> {

    @TableField("userName")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("mobile")
    private String mobile;

}
