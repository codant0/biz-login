package com.bgtech.bizlogin.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bgtech.bizlogin.db.module.LoginUser;
import org.springframework.stereotype.Repository;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 9:34
 */
@Repository
public interface UserMapper extends BaseMapper<LoginUser> {

    /**
     * 根据用户名获取用户信息，包括用户角色列表
     * @param username
     * @return
     */
    LoginUser getUserByName(String username);

    /**
     * 根据用户手机号获取用户信息，包括用户角色列表
     * @param phone
     * @return
     */
    LoginUser getUserByPhone(String phone);
}
