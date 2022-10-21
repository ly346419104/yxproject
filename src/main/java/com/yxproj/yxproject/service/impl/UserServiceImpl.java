package com.yxproj.yxproject.service.impl;

import com.yxproj.yxproject.entity.User;
import com.yxproj.yxproject.mapper.UserMapper;
import com.yxproj.yxproject.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
