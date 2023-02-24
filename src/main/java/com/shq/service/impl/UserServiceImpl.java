package com.shq.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shq.entity.User;
import com.shq.mapper.UserMapper;
import com.shq.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
