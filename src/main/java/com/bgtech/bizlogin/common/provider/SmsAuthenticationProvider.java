package com.bgtech.bizlogin.common.provider;

import com.bgtech.bizlogin.common.token.SmsAuthenticationToken;
import com.bgtech.bizlogin.db.module.LoginUser;
import lombok.Data;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 16:44
 */
@Component
@Data
public class SmsAuthenticationProvider implements AuthenticationProvider {

    UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;
        // 此处username是手机号，根据手机号查询用户
        String phone = (String) authentication.getPrincipal();

        // 此处loadUserByUsername 底层实现为loadUserByPhone
        LoginUser user = (LoginUser)userDetailsService.loadUserByUsername(phone);

        if (user == null) {
            throw new InternalAuthenticationServiceException("未找到与该手机对应的用户");
        } else if (!user.isEnabled()) {
            throw new DisabledException("用户已作废");
        }

        /**
         * 认证后principal存储用户信息
         */
        SmsAuthenticationToken authenticationResult = new SmsAuthenticationToken(user.getAuthorities(), user);
        authenticationResult.setDetails(smsAuthenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return SmsAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
