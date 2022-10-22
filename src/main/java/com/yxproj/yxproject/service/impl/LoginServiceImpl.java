package com.yxproj.yxproject.service.impl;

import com.yxproj.yxproject.common.contanst.JsonResult;
import com.yxproj.yxproject.common.contanst.Util.SM3Security;
import com.yxproj.yxproject.common.contanst.Util.TokenUtil;
import com.yxproj.yxproject.service.ILoginService;
import com.yxproj.yxproject.service.IRolePermissionService;
import com.yxproj.yxproject.service.IUserRoleService;
import com.yxproj.yxproject.service.IUserService;
import com.yxproj.yxproject.vo.LoginModel;
import com.yxproj.yxproject.vo.ResponseResult;
import com.yxproj.yxproject.vo.UserModel.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LoginServiceImpl implements ILoginService {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRolePermissionService rolePermissionService;
    @Autowired
    private IUserRoleService userRoleService;
    @Autowired
    private TokenUtil tokenUtil;

    @Override
    public JsonResult login(String userName, String password) {
        JsonResult jsonResult = new JsonResult(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        UserModel user = userService.getUser(userName);
        if (user == null) {
           return new JsonResult(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        if (SM3Security.hashToHexString(password).equals(user.getPassword())) {
            String userId = user.getId();
            LoginModel loginModel = new LoginModel();
            loginModel.setName(user.getName());
            loginModel.setUserName(user.getUserName());
            loginModel.setUserId(userId);
            //角色
            Set<String> roleIds = userRoleService.getRoles(userId);
            String roleId = userRoleService.getRole(userId);
            //权限
            String permission = rolePermissionService.getPermission(roleIds);
            loginModel.setRoleIds(roleId);
            loginModel.setPermissions(permission);
            ResponseResult responseResult = tokenUtil.getToken(loginModel, userName, "pc", "pc");
            jsonResult=new JsonResult(HttpStatus.OK,"登陆成功",responseResult);
        } else {
            jsonResult = new JsonResult(HttpStatus.BAD_REQUEST, "用户名或密码错误");
        }
        return jsonResult;
    }

}
