package com.yxproj.yxproject.service;

import com.yxproj.yxproject.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ly
 * @since 2022-10-11
 */
public interface IUserService extends IService<User> {

    List<User> getAll();

}
