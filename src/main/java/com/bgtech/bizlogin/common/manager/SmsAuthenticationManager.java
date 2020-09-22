package com.bgtech.bizlogin.common.manager;

import com.bgtech.bizlogin.common.provider.SmsAuthenticationProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.util.Objects;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 15:29
 * 认证处理
 */
public class SmsAuthenticationManager implements AuthenticationManager {

    private final SmsAuthenticationProvider smsAuthenticationProvider;

    public SmsAuthenticationManager(SmsAuthenticationProvider smsAuthenticationProvider) {
        this.smsAuthenticationProvider = smsAuthenticationProvider;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Authentication result = smsAuthenticationProvider.authenticate(authentication);
        if (Objects.nonNull(result)) {
            return result;
        }
        throw new ProviderNotFoundException("Authentication failed!");
    }
}
