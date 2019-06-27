package com.neticon.neticon.repository.redis.impl;

import com.neticon.neticon.common.vo.RedisRoomInfoVo;
import com.neticon.neticon.tools.JsonUtils;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

@Repository
public class RoomInfoRedisRepositoryImpl {
    private final JedisPool jedisPool;

    public RoomInfoRedisRepositoryImpl(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    private static final int PENDING_ROOM_SURVIVING_SECONDS = 60 * 60 * 24;

    private static final String ROOM_INFO_REDIS_PREFIX = "room-info:";

    public boolean isRoomIdUsed(int roomId) {
        return jedisPool.getResource().exists(ROOM_INFO_REDIS_PREFIX + roomId);
    }

    public void insertNewRoomInfo(RedisRoomInfoVo redisRoomInfoVo) {
        String key = ROOM_INFO_REDIS_PREFIX + redisRoomInfoVo.getRoomId();
        jedisPool.getResource().setex(key, PENDING_ROOM_SURVIVING_SECONDS, JsonUtils.toJson(redisRoomInfoVo));
    }

    public RedisRoomInfoVo getRoomInfo(int roomId) {
        String key = ROOM_INFO_REDIS_PREFIX + roomId;
        return JsonUtils.fromJson(jedisPool.getResource().get(key), RedisRoomInfoVo.class);
    }

    public void removeRoomInfo(RedisRoomInfoVo redisRoomInfoVo) {
        String key = ROOM_INFO_REDIS_PREFIX + redisRoomInfoVo.getRoomId();
        jedisPool.getResource().del(key);
    }
}
