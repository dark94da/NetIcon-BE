package com.neticon.neticon.service.impl;

import com.neticon.neticon.repository.redis.impl.TokenRedisRepositoryImpl;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.List;
import java.util.Map;

@Service
public class WebSocketInterceptor implements HandshakeInterceptor {
    private static final String TOKEN_CARRIER_HEADER_NAME = "Sec-WebSocket-Protocol";

    private final TokenRedisRepositoryImpl tokenRedisRepository;

    public WebSocketInterceptor(TokenRedisRepositoryImpl tokenRedisRepository) {
        this.tokenRedisRepository = tokenRedisRepository;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        List<String> token = request.getHeaders().get(TOKEN_CARRIER_HEADER_NAME);
        if (token == null || token.get(0) == null) {
            return false;
        }
        String nickname = tokenRedisRepository.getUserNickname(token.get(0));
        if (nickname == null) {
            return false;
        }
        attributes.put("nickname", nickname);
        response.getHeaders().set(TOKEN_CARRIER_HEADER_NAME, token.get(0));
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
