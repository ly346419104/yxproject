package com.yxproj.yxproject.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxproj.yxproject.common.contanst.Util.SM3Security;
import com.yxproj.yxproject.entity.User;
import com.yxproj.yxproject.entity.UserRole;
import com.yxproj.yxproject.mapper.UserMapper;
import com.yxproj.yxproject.service.IUserRoleService;
import com.yxproj.yxproject.service.IUserService;
import com.yxproj.yxproject.vo.UserModel.UserModel;
import com.yxproj.yxproject.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ly
 * @since 2022-10-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private IUserRoleService userRoleService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insert(UserVo userVo) {
        String pass = SM3Security.hashToHexString(userVo.getPassword());
        User user = BeanUtil.toBean(userVo, User.class);
        user.setPassword(pass);
        boolean save = super.save(user);
        if (!CollectionUtils.isEmpty(userVo.getRoles())){
            List<UserRole> userRoles = userVo.getRoles().stream().map(per -> {
                UserRole role = new UserRole();
                role.setIsDeleted(false);
                role.setRoleId(per);
                role.setUserId(user.getId());
                return role;
            }).collect(Collectors.toList());
            boolean b = userRoleService.saveBatch(userRoles);
        }
        return save;
    }

    @Override
    public UserModel getUser(String userName) {
        LambdaQueryWrapper<User>lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(User::getUserName,userName);
        lambdaQueryWrapper.eq(User::getIsDeleted,false);
        User one = super.getOne(lambdaQueryWrapper);
        if (one==null){
            return null;
        }else {
            return BeanUtil.toBean(one,UserModel.class);
        }
    }
}
