package com.bgtech.bizlogin.service;

import com.bgtech.bizlogin.db.module.LoginUser;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 11:28
 */
public interface UserService {

    /**
     * 根据用户名获取系统用户
     */
    LoginUser getUserByName(String username);

    LoginUser getUserByPhone(String phone);

    boolean register(LoginUser user);
}
