package com.bgtech.bizlogin.common.filter;

import com.bgtech.bizlogin.db.module.LoginUser;
import com.bgtech.bizlogin.entity.SmsCodeVerify;
import com.bgtech.cloud.common.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 16:48
 * 验证验证码正确与否过滤器
 */
@Component
public class SmsCodeFilter extends OncePerRequestFilter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse resp, FilterChain filterChain) throws ServletException, IOException {
        // 当请求路径为"/login/mobile"，且登录方式为post时，该过滤器生效
        if (StringUtils.equalsIgnoreCase("/login/mobile", req.getRequestURI())
                && StringUtils.equalsIgnoreCase(req.getMethod(), "post")) {
            try {
                validateCode(req);
            } catch (AuthenticationException e) {
            }
        }
        filterChain.doFilter(req, resp);
    }

    private void validateCode(HttpServletRequest req) throws ServletRequestBindingException, IOException {
        SmsCodeVerify phoneVerify = new ObjectMapper().readValue(req.getInputStream(), SmsCodeVerify.class);

        String phone = phoneVerify.getPhone();
        String smsCode = phoneVerify.getCode();

        String code = (String) redisTemplate.opsForValue().get("phone-" + phone);

        if (StringUtils.isBlank(smsCode)) {
            throw new ServletRequestBindingException("验证码不能为空");
        }

        if (code == null) {
            throw new ServletRequestBindingException("验证码不存在或已过期，请重新发送");
        }

        if (!StringUtils.equalsIgnoreCase(code, smsCode)) {
            throw new ServletRequestBindingException("验证码不正确");
        }

    }

}
