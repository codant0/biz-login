package com.bgtech.bizlogin.controller;

import com.bgtech.bizlogin.entity.PhoneNumReq;
import com.bgtech.bizlogin.entity.SmsCode;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author HuangJiefeng
 * @date 2020/9/21 0021 14:25
 */
@RequestMapping("/sms")
@RestController
public class SmsController {

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/getMsg")
    public String createSmsCode(@RequestBody PhoneNumReq phone) {
        // 生成随机6位数字字符，有效期60秒
        SmsCode smsCode = createSMSCode();
        // 生成验证码，存入redis中
        redisTemplate.opsForValue().set("phone-" + phone.getPhone(), smsCode.getCode(), 60, TimeUnit.SECONDS);
        // 输出验证码到控制台代替短信发送服务
        System.out.println("您的登录验证码为：" + smsCode.getCode() + "，有效时间为60秒");
        return smsCode.getCode();
    }

    private SmsCode createSMSCode() {
        String code = RandomStringUtils.randomNumeric(6);
        return new SmsCode(code, 60);
    }
}
