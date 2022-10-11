package com.yxproj.yxproject.mapper;

import com.yxproj.yxproject.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ly
 * @since 2022-10-11
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
List<User>getAll();
}
