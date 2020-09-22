package com.bgtech.bizlogin.service.impl;

import com.bgtech.bizlogin.common.util.StringUtil;
import com.bgtech.bizlogin.db.module.LoginUser;
import com.bgtech.bizlogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 11:29
 */
@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LoginUser loginUser = null;

        System.out.println(username);
        if (StringUtil.isPhone(username)) {
            // 手机登录，发送验证码，验证验证码，成功后返回user(?)
            loginUser = userService.getUserByPhone(username);
        } else {
            // 用户名
            loginUser = userService.getUserByName(username);
        }

//        List<SimpleGrantedAuthority>

//        if (StringUtil.isPhone(username)){
//            //手机
//            loginAppUser = userFeignClient.findByMobile(username);
//        }else {
//            // 用户名
//            loginAppUser = userFeignClient.findByUsername(username);
//        }
//
//        if (loginAppUser == null) {
//            throw new UsernameNotFoundException("用户不存在");
//        }else if (StringUtil.isBlank(loginAppUser.getUsername())) {
//            throw new ProviderNotFoundException("系统繁忙中");
//        }
//        else if (!loginAppUser.isEnabled()) {
//            throw new DisabledException("用户已作废");
//        }
        return loginUser;
    }
}
