package com.neticon.neticon.repository.redis.impl;

import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

@Repository
public class TokenRedisRepositoryImpl {
    private static final String USER_INFO_REDIS_PREFIX = "usr-info:";

    private final JedisPool jedisPool;

    public TokenRedisRepositoryImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public String getUserNickname(String token) {
        String key = USER_INFO_REDIS_PREFIX + token;
        return jedisPool.getResource().get(key);
    }

    public void setUserToken(String token, String username, int expireTime) {
        String key = USER_INFO_REDIS_PREFIX + token;
        jedisPool.getResource().setex(key, expireTime, username);
    }

    public void nullifyUserToken(String token) {
        String key = USER_INFO_REDIS_PREFIX + token;
        jedisPool.getResource().del(key);
    }
}
