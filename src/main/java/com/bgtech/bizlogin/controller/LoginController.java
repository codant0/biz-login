package com.bgtech.bizlogin.controller;

import com.bgtech.bizlogin.entity.PhoneNumReq;
import com.bgtech.bizlogin.entity.SmsCode;
import com.bgtech.bizlogin.entity.SmsCodeVerify;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author HuangJiefeng
 * @date 2020/9/18 0018 16:37
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping("/usernamePassword")
    public void login() {}

    @PostMapping("/mobile")
    public String verifySmsCode(@RequestBody SmsCodeVerify smsCodeVerify) {
        return "登录成功";
    }
}
