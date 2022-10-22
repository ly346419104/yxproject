package com.yxproj.yxproject.vo;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionVo {
    private String roleId;
    private List<String>permissions;
}
