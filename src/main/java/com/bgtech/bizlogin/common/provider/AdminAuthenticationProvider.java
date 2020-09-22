package com.bgtech.bizlogin.common.provider;

import com.bgtech.bizlogin.db.mapper.UserMapper;
import com.bgtech.bizlogin.db.module.LoginUser;
import com.bgtech.bizlogin.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 15:30
 */
//@Component
public class AdminAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取前端表单中输入后返回的用户名、密码
        String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        LoginUser user = (LoginUser) userDetailsService.loadUserByUsername(userName);

        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        } else if (!user.isEnabled()) {
            throw new DisabledException("用户已作废");
        }
//        SmsCodeAuthenticationToken authenticationResult = new SmsCodeAuthenticationToken(userDetails, userDetails.getAuthorities());
//         需要把未认证中的一些信息copy到已认证的token中
//        authenticationResult.setDetails(token);
//        return authenticationResult;
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
