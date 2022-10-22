package com.yxproj.yxproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yxproj.yxproject.entity.User;
import com.yxproj.yxproject.vo.UserModel.UserModel;
import com.yxproj.yxproject.vo.UserVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ly
 * @since 2022-10-21
 */
public interface IUserService extends IService<User> {
    UserModel getUser(String userName);

    boolean insert(UserVo user);
}
