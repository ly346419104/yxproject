package com.yxproj.yxproject.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * project name: reloadview
 *
 * @Author: why
 * Date: 2020/4/17 11:00
 * @Version:
 * @Description:
 */
@Setter
@Getter
public class ResponseResult {
    private String access_token;
//刷新token
    private String refresh_token;
//过期时间
    private String expire_time;
    private String token_type;
    private LoginModel accountUser;
}
