package com.example.medic.service;

public interface RedisBlacklistService {

    void blacklistToken(
            String token,
            long expiration
    );

    boolean isBlacklisted(
            String token
    );
}