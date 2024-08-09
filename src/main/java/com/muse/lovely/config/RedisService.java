package com.muse.lovely.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;

    public void saveItem(String key, String value){
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(10));
    }

    public Object getItem(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
