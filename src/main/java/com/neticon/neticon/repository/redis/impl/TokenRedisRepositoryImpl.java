package com.neticon.neticon.repository.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

@Repository
public class TokenRedisRepositoryImpl {
    private static final String USER_INFO_REDIS_PREFIX = "usr-info:";

    @Autowired
    private JedisPool jedisPool;

    public String getUserNickname(String token) {
        String key = USER_INFO_REDIS_PREFIX + token;
        return jedisPool.getResource().get(key);
    }

    public void setUserToken(String token, String username, int expireTime) {
        String key = USER_INFO_REDIS_PREFIX + token;
        jedisPool.getResource().setex(key, expireTime, username);
    }
}
