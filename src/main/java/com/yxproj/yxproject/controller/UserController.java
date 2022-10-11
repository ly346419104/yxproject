package com.yxproj.yxproject.controller;


import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.entity.User;
import com.yxproj.yxproject.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ly
 * @since 2022-10-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;

    @GetMapping("getAll")
    public JsonResult getAll() {
        List<User> list = iUserService.getAll();
        return new JsonResult(HttpStatus.OK, "", list);
    }
    @GetMapping("list")
    public JsonResult list() {
        List<User> list = iUserService.list();
        System.out.println("test");
        return new JsonResult(HttpStatus.OK, "", list);
    }
}
