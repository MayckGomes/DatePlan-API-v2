package com.mayckgomes.dateplan_api.services;

import com.mayckgomes.dateplan_api.exception.custom.token.TokenInBlackListException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisBlackListService {

    private final StringRedisTemplate stringRedisTemplate;

    public RedisBlackListService(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void addAccessToken(String jwtId, String token) {

        stringRedisTemplate.opsForValue().set(jwtId, token, Duration.ofMinutes(15));
    }

    public void addRefreshToken(String jwtId, String token) {
        stringRedisTemplate.opsForValue().set(jwtId, token, Duration.ofDays(7));
    }

    public void verifyIfBlacklisted(String jwtId) {

        if (stringRedisTemplate.opsForValue().get(jwtId) != null){
            throw new TokenInBlackListException();
        }
    }

}
