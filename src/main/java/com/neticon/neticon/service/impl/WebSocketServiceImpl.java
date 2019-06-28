package com.neticon.neticon.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neticon.neticon.common.domain.UserInfo;
import com.neticon.neticon.common.enums.WebSocketActionEnum;
import com.neticon.neticon.common.vo.websocket.BaseWebSocketVo;
import com.neticon.neticon.common.vo.websocket.HearVo;
import com.neticon.neticon.common.vo.RedisRoomInfoVo;
import com.neticon.neticon.common.vo.websocket.OfflineVo;
import com.neticon.neticon.common.vo.websocket.OnlineVo;
import com.neticon.neticon.common.vo.websocket.StatusVo;
import com.neticon.neticon.common.vo.websocket.TalkVo;
import com.neticon.neticon.repository.mybatis.impl.UserInfoRepositoryImpl;
import com.neticon.neticon.repository.redis.impl.RoomInfoRedisRepositoryImpl;
import com.neticon.neticon.service.WebSocketService;
import com.neticon.neticon.tools.DateTimeUtils;
import com.neticon.neticon.tools.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class WebSocketServiceImpl implements WebSocketHandler, WebSocketService {
    private static final String SYSTEM_OPERATOR = "system";

    private Map<String, WebSocketSession> sessionMap;

    @Autowired
    private RoomInfoRedisRepositoryImpl roomInfoRedisRepository;

    @Autowired
    private UserInfoRepositoryImpl userInfoRepository;

    @PostConstruct
    public void init() {
        sessionMap = new HashMap<>();
    }

    @PreDestroy
    public void cleanup() {
        for (WebSocketSession session : sessionMap.values()) {
            try {
                session.close();
            } catch (IOException ignored) {
            }
        }
    }

    @Override
    public void addNewSession() {

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String nickname = (String) session.getAttributes().get("nickname");
        sessionMap.put(nickname, session);
        System.out.println("user coming: " + nickname);
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        List<Integer> roomList = JsonUtils.fromJson(userInfo.getRoomList(),
                new TypeReference<List<Integer>>() {
                });
        if (roomList == null) {
            return;
        }

        // broadcast online message
        OnlineVo onlineVo = new OnlineVo();
        onlineVo.setAction(WebSocketActionEnum.ONLINE.getName());
        onlineVo.setCreatedAt(DateTimeUtils.getTimestampInSec());
        onlineVo.setOperator(nickname);
        roomList
                .stream()
                .map(roomId -> roomInfoRedisRepository.getRoomInfo(roomId))
                .map(RedisRoomInfoVo::getMembers)
                .flatMap(Collection::parallelStream)
                .distinct()
                .filter(name -> !name.equals(nickname))
                .forEach(receiver -> trySendMsgToPerson(receiver, onlineVo));

        // pushing online room list
        List<Integer> onlineRoomList = roomList
                .stream()
                .filter(roomId -> {
                    RedisRoomInfoVo redisRoomInfoVo = roomInfoRedisRepository.getRoomInfo(roomId);
                    return redisRoomInfoVo
                            .getMembers()
                            .stream()
                            .filter(name -> !name.equals(nickname))
                            .anyMatch(name -> sessionMap.containsKey(name));
                })
                .collect(Collectors.toList());
        StatusVo statusVo = new StatusVo();
        statusVo.setAction(WebSocketActionEnum.STATUS.getName());
        statusVo.setOperator(SYSTEM_OPERATOR);
        statusVo.setCreatedAt(DateTimeUtils.getTimestampInSec());
        statusVo.setOnlineRoomList(onlineRoomList);
        trySendMsgToPerson(nickname, statusVo);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String operator = (String) session.getAttributes().get("nickname");
        String content = (String) message.getPayload();
        BaseWebSocketVo vo = JsonUtils.fromJson(content, BaseWebSocketVo.class);
        if (vo == null || vo.getAction() == null) {
            return;
        }
        vo.setOperator(operator);
        if (vo.getAction().equals(WebSocketActionEnum.TALK.getName())) {
            talkToRoom((TalkVo) vo);
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        String nickname = (String) session.getAttributes().get("nickname");
        sessionMap.remove(nickname);
        System.out.println("user leaving: " + nickname);
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        List<Integer> roomList = JsonUtils.fromJson(userInfo.getRoomList(),
                new TypeReference<List<Integer>>() {
                });
        if (roomList == null) {
            return;
        }
        OfflineVo offlineVo = new OfflineVo();
        offlineVo.setAction(WebSocketActionEnum.OFFLINE.getName());
        offlineVo.setOperator(nickname);
        offlineVo.setCreatedAt(DateTimeUtils.getTimestampInSec());
        roomList
                .stream()
                .map(roomId -> roomInfoRedisRepository.getRoomInfo(roomId))
                .map(RedisRoomInfoVo::getMembers)
                .flatMap(Collection::parallelStream)
                .distinct()
                .filter(name -> !name.equals(nickname))
                .forEach(receiver -> trySendMsgToPerson(receiver, offlineVo));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private void talkToRoom(TalkVo talkVo) {
        if (talkVo.getRoomId() == null) {
            return;
        }
        RedisRoomInfoVo redisRoomInfoVo = roomInfoRedisRepository.getRoomInfo(talkVo.getRoomId());
        if (redisRoomInfoVo == null || !redisRoomInfoVo.getIsActive()) {
            return;
        }
        List<String> members = redisRoomInfoVo.getMembers();
        if (!members.contains(talkVo.getOperator())) {
            return;
        }
        HearVo hearVo = new HearVo();
        hearVo.setAction(WebSocketActionEnum.HEAR.getName());
        hearVo.setOperator(talkVo.getOperator());
        hearVo.setCreatedAt(talkVo.getCreatedAt());
        hearVo.setContent(talkVo.getContent());
        members
                .forEach(nickname -> {
                    if (!nickname.equals(talkVo.getOperator())) {
                        trySendMsgToPerson(nickname, hearVo);
                    }
                });
    }

    private void trySendMsgToPerson(String receiver, BaseWebSocketVo vo) {
        WebSocketSession session = sessionMap.get(receiver);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage(JsonUtils.toJson(vo)));
            } catch (IOException e) {
                System.out.println("send msg fail: " + JsonUtils.toJson(vo));
            }
        }
    }
}
