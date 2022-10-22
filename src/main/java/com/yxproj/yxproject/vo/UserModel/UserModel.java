package com.yxproj.yxproject.vo.UserModel;

import lombok.Data;

@Data
public class UserModel {
    private String id;
    private String userName;
    private String name;
    private Integer age;
    private String cardNo;
    private String mobilePhone;
    private Boolean isDeleted;
    private String password;
}
