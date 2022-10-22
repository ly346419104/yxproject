package com.yxproj.yxproject.vo;

import lombok.Data;

@Data
public class LoginModel {
    private String userId;
    private String userName;
    private String name;
    private String permissions;
    private String roleIds;
    private String accessToken;
    private String refreshToken;
}
