package com.gaobug.demo;

import com.gaobug.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class Demo {
    @Resource
    private RedisUtils redisUtils;

    public void demo() {
        redisUtils.set("key", "value");
    }
}
