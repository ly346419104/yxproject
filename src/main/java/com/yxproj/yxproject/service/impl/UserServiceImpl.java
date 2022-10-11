package com.yxproj.yxproject.service.impl;

import com.yxproj.yxproject.entity.User;
import com.yxproj.yxproject.mapper.UserMapper;
import com.yxproj.yxproject.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ly
 * @since 2022-10-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
   @Autowired
   private UserMapper userMapper;
    @Override
    public List<User> getAll() {
        List<User> all = userMapper.getAll();
        return all;
    }
}
