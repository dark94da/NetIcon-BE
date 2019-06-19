package com.neticon.neticon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class JedisPoolFactory {

    //自动注入redis配置属性文件
    @Autowired
    private RedisProperties properties;

    @Bean
    public JedisPool getJedisPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(1000);
        config.setMaxWaitMillis(1000);
        return new JedisPool(config, properties.getHost(), properties.getPort(), 100);
    }
}
