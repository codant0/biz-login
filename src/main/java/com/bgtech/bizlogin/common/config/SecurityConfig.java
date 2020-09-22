package com.bgtech.bizlogin.common.config;

import com.bgtech.bizlogin.common.filter.JWTLoginFilter;
import com.bgtech.bizlogin.common.filter.SmsCodeFilter;
import com.bgtech.cloud.web.config.mvc.logger.filter.RepeatedlyReadFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author HuangJiefeng
 * @date 2020/9/16 0016 16:28
 *
 * Spring Security配置
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    SmsCodeFilter smsCodeFilter;

    @Autowired
    SmsAuthenticationConfig smsAuthenticationConfig;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new RepeatedlyReadFilter());
        registration.addUrlPatterns("/*");
        registration.setName("logFilter");
        registration.setOrder(1);
        return registration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http//关闭跨站请求防护
            .cors()
            // 禁用CSRF 开启跨域
            .and().csrf().disable()
                // 允许不登录就可以访问的方法，多个用逗号分隔
                .authorizeRequests()
                .antMatchers("/test/**", "/login/**", "/sms/getMsg")
                .permitAll()
                //其他的需要授权后访问
                .anyRequest().authenticated()

            .and()
                // 添加短信验证码校验过滤器
//                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                // 增加普通登录拦截
                .addFilter(new JWTLoginFilter(authenticationManager()))
                .addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .apply(smsAuthenticationConfig);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/plugins/**", "/favicon.ico");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

}
