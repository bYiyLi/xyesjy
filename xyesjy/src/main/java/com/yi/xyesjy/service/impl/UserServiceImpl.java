package com.yi.xyesjy.service.impl;

import com.yi.xyesjy.entity.User;
import com.yi.xyesjy.mapper.UserMapper;
import com.yi.xyesjy.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author byh
 * @since 2020-08-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
