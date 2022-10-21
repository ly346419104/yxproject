package com.yxproj.yxproject.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author ly
 * @since 2022-10-21
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user")
public class User extends BaseEntity<User> implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableField("delete_time")
    private Date deleteTime;

    /**
     * 账号
     */
    @TableField("user_name")
    private String userName;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 年龄
     */
    @TableField("age")
    private Integer age;

    /**
     * 手机号
     */
    @TableField("mobile_phone")
    private String mobilePhone;

    /**
     * 身份证号
     */
    @TableField("card_no")
    private Integer cardNo;




}
