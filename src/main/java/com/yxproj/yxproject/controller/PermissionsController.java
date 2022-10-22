package com.yxproj.yxproject.controller;


import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.entity.Permissions;
import com.yxproj.yxproject.service.IPermissionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/permissions")
public class PermissionsController {
    @Autowired
    private IPermissionsService permissionsService;

    @PostMapping("add")
    public JsonResult add(@RequestBody Permissions permissions) {
        boolean save = permissionsService.save(permissions);
        if (save) {
            return new JsonResult(HttpStatus.OK, "success");
        } else {
            return new JsonResult(HttpStatus.BAD_REQUEST, "error");
        }
    }
}
