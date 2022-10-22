package com.yxproj.yxproject.controller;

import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.common.contanst.UncheckToken;
import com.yxproj.yxproject.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private ILoginService iLoginService;

    @GetMapping("login")
    @UncheckToken
    public JsonResult login(@RequestParam(value = "userName") String userName
            , @RequestParam("password") String password) {
        JsonResult login = iLoginService.login(userName, password);
        return login;
    }
}
