package com.neticon.neticon.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neticon.neticon.common.domain.UserInfo;
import com.neticon.neticon.common.vo.RedisRoomInfoVo;
import com.neticon.neticon.repository.mybatis.impl.UserInfoRepositoryImpl;
import com.neticon.neticon.repository.redis.impl.RoomInfoRedisRepositoryImpl;
import com.neticon.neticon.service.RoomService;
import com.neticon.neticon.tools.JsonUtils;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    private static final int MIN_ROOM_ID = 100000;

    private static final int MAX_ROOM_ID = 999999;

    private final RoomInfoRedisRepositoryImpl roomInfoRedisRepository;

    public RoomServiceImpl(RoomInfoRedisRepositoryImpl roomInfoRedisRepository,
                           UserInfoRepositoryImpl userInfoRepository) {
        this.roomInfoRedisRepository = roomInfoRedisRepository;
        this.userInfoRepository = userInfoRepository;
    }

    private final UserInfoRepositoryImpl userInfoRepository;

    @Override
    public int createNewRoom(String nickname) {
        for (int i = MIN_ROOM_ID; i < MAX_ROOM_ID; i++) {
            if (!roomInfoRedisRepository.isRoomIdUsed(i)) {
                // add new pending room info in redis
                RedisRoomInfoVo redisRoomInfoVo = new RedisRoomInfoVo();
                redisRoomInfoVo.setRoomId(i);
                redisRoomInfoVo.setIsActive(false);
                List<String> members = new LinkedList<>();
                members.add(nickname);
                redisRoomInfoVo.setMembers(members);
                roomInfoRedisRepository.insertNewRoomInfo(redisRoomInfoVo);

                // update userInfo pending room list
                addRoomIdToPendingList(i, nickname);
                return redisRoomInfoVo.getRoomId();
            }
        }
        return 0;
    }

    @Override
    public void deleteRoom(int roomId) {
        if (!roomInfoRedisRepository.isRoomIdUsed(roomId)) {
            return;
        }
        RedisRoomInfoVo roomInfoVo = roomInfoRedisRepository.getRoomInfo(roomId);
        if (roomInfoVo == null) {
            return;
        }
        roomInfoVo.getMembers()
                .forEach(nickname -> {
                    if (roomInfoVo.getIsActive()) {
                        removeRoomIdFromRoomList(roomId, nickname);
                    } else {
                        removeRoomIdFromPendingList(roomId, nickname);
                    }
                });
        roomInfoRedisRepository.removeRoomInfo(roomInfoVo);
    }

    @Override
    public boolean isUserInRoom(String nickname, int roomId) {
        RedisRoomInfoVo redisRoomInfoVo = roomInfoRedisRepository.getRoomInfo(roomId);
        if (redisRoomInfoVo.getMembers() == null) {
            return false;
        }
        return redisRoomInfoVo.getMembers().contains(nickname);
    }

    @Override
    public void joinRoom(int roomId, String nickname) {
        RedisRoomInfoVo redisRoomInfoVo = roomInfoRedisRepository.getRoomInfo(roomId);
        redisRoomInfoVo.getMembers()
                .forEach(name -> {
                    removeRoomIdFromPendingList(roomId, name);
                    addRoomIdToRoomList(roomId, name);
                });
        redisRoomInfoVo.getMembers().add(nickname);
        redisRoomInfoVo.setIsActive(true);
        roomInfoRedisRepository.insertNewRoomInfo(redisRoomInfoVo);
        addRoomIdToRoomList(roomId, nickname);
    }

    @Override
    public boolean isRoomAvailable(int roomId) {
        RedisRoomInfoVo redisRoomInfoVo = roomInfoRedisRepository.getRoomInfo(roomId);
        return redisRoomInfoVo != null && !redisRoomInfoVo.getIsActive();
    }

    private void addRoomIdToPendingList(int roomId, String nickname) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        List<Integer> pendingList = JsonUtils.fromJson(userInfo.getPendingList(),
                new TypeReference<List<Integer>>() {
                });
        if (pendingList == null) {
            pendingList = new LinkedList<>();
        }
        userInfo.setPendingList(JsonUtils.toJson(pendingList.add(roomId)));
        userInfoRepository.updateUserInfo(userInfo);
    }

    private void removeRoomIdFromPendingList(int roomId, String nickname) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        List<Integer> pendingList = JsonUtils.fromJson(userInfo.getPendingList(),
                new TypeReference<List<Integer>>() {
                });
        if (pendingList == null) {
            return;
        }
        userInfo.setPendingList(JsonUtils.toJson(pendingList.remove(roomId)));
        userInfoRepository.updateUserInfo(userInfo);
    }

    private void addRoomIdToRoomList(int roomId, String nickname) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        List<Integer> roomList = JsonUtils.fromJson(userInfo.getRoomList(),
                new TypeReference<List<Integer>>() {
                });
        if (roomList == null) {
            roomList = new LinkedList<>();
        }
        userInfo.setRoomList(JsonUtils.toJson(roomList.add(roomId)));
        userInfoRepository.updateUserInfo(userInfo);
    }

    private void removeRoomIdFromRoomList(int roomId, String nickname) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        List<Integer> roomList = JsonUtils.fromJson(userInfo.getRoomList(),
                new TypeReference<List<Integer>>() {
                });
        if (roomList == null) {
            return;
        }
        userInfo.setRoomList(JsonUtils.toJson(roomList.remove(roomId)));
        userInfoRepository.updateUserInfo(userInfo);
    }
}
