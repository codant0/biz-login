package com.bgtech.bizlogin.common.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author HuangJiefeng
 * @date 2020/9/21 0021 15:58
 */
@Component
public class RedisUtil {

    @Autowired
    RedisTemplate redisTemplate;

    public Object getRedisStr(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
