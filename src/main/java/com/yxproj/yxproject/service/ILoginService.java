package com.yxproj.yxproject.service;

import com.yxproj.yxproject.common.contanst.JsonResult;

public interface ILoginService {
    JsonResult login(String userName, String password);
}
