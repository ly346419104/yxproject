package com.yxproj.yxproject.controller;


import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.common.contanst.aop.InvokeLogIgnore;
import com.yxproj.yxproject.service.IRolePermissionService;
import com.yxproj.yxproject.vo.RolePermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/rolePermission")
public class RolePermissionController {
    @Autowired
    private IRolePermissionService rolePermissionService;
    @PostMapping("add")
//    @IgnoreLog
    @InvokeLogIgnore
    public JsonResult add(@Valid
                          @RequestBody RolePermissionVo rolePermissionVo) {


        boolean save = rolePermissionService.insert(rolePermissionVo);
        if (save) {
            return new JsonResult(HttpStatus.OK, "添加成功");
        } else {
            return new JsonResult(HttpStatus.BAD_REQUEST, "添加失败");

        }
    }
}
