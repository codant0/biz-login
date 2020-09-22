package com.bgtech.bizlogin.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author HuangJiefeng
 * @date 2020/9/19 0019 15:42
 */
//@Component
public class JwtContainer {

    public static String TOKEN_SECRET;

    public static String TOKEN_HEADER;

    public static String TOKEN_PREFIX;

    @Value("${jwt.secret}")
    public static void setTokenSecret(String tokenSecret) {
        TOKEN_SECRET = tokenSecret;
    }

    @Value("${jwt.header}")
    public static void setTokenHeader(String tokenHeader) {
        TOKEN_HEADER = tokenHeader;
    }

    @Value("${jwt.prefix}")
    public static void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }
}
