package com.yxproj.yxproject.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserVo  {
//    private static final long serialVersionUID = 1L;

    private String name;
    @NotBlank(message = "账号不能为空")
    private String userName;
    @NotBlank(message = "密码不能为空")
    private String password;
    private Integer age;
    @NotBlank(message = "身份证号不能为空")
    private String cardNo;
    @NotBlank(message = "手机号不能为空")
    private String mobilePhone;
}
