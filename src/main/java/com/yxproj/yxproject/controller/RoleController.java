package com.yxproj.yxproject.controller;


import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.entity.Role;
import com.yxproj.yxproject.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ly
 * @since 2022-10-22
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private IRoleService roleService;

    @PostMapping("add")
    public JsonResult add(@RequestBody Role role) {
        boolean save = roleService.save(role);
        if (save) {
            return new JsonResult(HttpStatus.OK, "success");
        } else {
            return new JsonResult(HttpStatus.BAD_REQUEST, "error");
        }
    }
}
