package com.example.medic.service.impl;

import com.example.medic.service.RedisBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisBlacklistServiceImpl
        implements RedisBlacklistService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void blacklistToken(

            String token,

            long expiration
    ) {

        redisTemplate.opsForValue().set(
                "blacklist:" + token,
                "revoked",
                expiration,
                TimeUnit.MILLISECONDS
        );
    }

    @Override
    public boolean isBlacklisted(
            String token
    ) {

        return Boolean.TRUE.equals(
                redisTemplate.hasKey(
                        "blacklist:" + token
                )
        );
    }
}