package com.bgtech.bizlogin.entity;

import lombok.Data;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 15:08
 */
@Data
public class SmsCodeVerify {

    private String phone;

    private String code;
}
