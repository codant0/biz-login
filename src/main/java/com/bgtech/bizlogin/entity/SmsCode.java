package com.bgtech.bizlogin.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 14:16
 * 短信验证码实体类
 */
@Data
public class SmsCode {

    private String code;
    private LocalDateTime expireTime;

    /**
     * 设置验证码有效时间（单位为秒）
     */
    public SmsCode(String code, int expireIn) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
    }

    /**
     * 设置验证码到期时间
     */
    public SmsCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime = expireTime;
    }

    /**
     * 验证码是否过期
     * @return
     */
    public boolean isExpire() {
        return LocalDateTime.now().isAfter(expireTime);
    }
}
