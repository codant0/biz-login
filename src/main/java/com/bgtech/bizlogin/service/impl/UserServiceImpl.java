package com.bgtech.bizlogin.service.impl;

import com.bgtech.bizlogin.db.mapper.UserMapper;
import com.bgtech.bizlogin.db.module.LoginUser;
import com.bgtech.bizlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 11:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public LoginUser getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public LoginUser getUserByPhone(String phone) {
        return userMapper.getUserByPhone(phone);
    }

    @Override
    public boolean register(LoginUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userMapper.insert(user) > 0;
    }
}
