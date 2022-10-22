package com.yxproj.yxproject.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.common.contanst.aop.InvokeLogIgnore;
import com.yxproj.yxproject.entity.User;
import com.yxproj.yxproject.service.IUserService;
import com.yxproj.yxproject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ly
 * @since 2022-10-21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService iUserService;
    @PostMapping("add")
//    @IgnoreLog
    @InvokeLogIgnore
    public JsonResult add(     @Valid
                                   @RequestBody UserVo userVo) {


        boolean save = iUserService.insert(userVo);
        if (save) {
            return new JsonResult(HttpStatus.OK, "添加成功");
        } else {
            return new JsonResult(HttpStatus.BAD_REQUEST, "添加失败");

        }
    }

    @GetMapping("list")
    public JsonResult list(@RequestParam(value = "name", required = false) String name) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(StringUtils.hasText(name), User::getName, name);
        List<User> list = iUserService.list(lambdaQueryWrapper);
        return new JsonResult(HttpStatus.OK, "查询成功", list);
    }
}
