package com.neticon.neticon.service.impl;

import com.neticon.neticon.common.domain.UserInfo;
import com.neticon.neticon.repository.mybatis.impl.UserInfoRepositoryImpl;
import com.neticon.neticon.repository.redis.impl.TokenRedisRepositoryImpl;
import com.neticon.neticon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final int TOKEN_EXPIRE_TIME_SEC = 3600 * 24 * 20;

    @Autowired
    UserInfoRepositoryImpl userInfoRepository;

    @Autowired
    TokenRedisRepositoryImpl tokenRedisRepository;

    @Override
    public String login(String nickname, String identifier) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        if (userInfo == null) {
            return null;
        }
        if (!userInfo.getIdentifier().equals(identifier)) {
            return null;
        }
        String token = "sssss";
        tokenRedisRepository.setUserToken(token, nickname, TOKEN_EXPIRE_TIME_SEC);
        return token;
    }
}
