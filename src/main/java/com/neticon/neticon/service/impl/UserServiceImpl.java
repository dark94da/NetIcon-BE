package com.neticon.neticon.service.impl;

import com.neticon.neticon.common.domain.UserInfo;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.repository.mybatis.impl.UserInfoRepositoryImpl;
import com.neticon.neticon.repository.redis.impl.TokenRedisRepositoryImpl;
import com.neticon.neticon.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserServiceImpl implements UserService {
    private static final int TOKEN_EXPIRE_TIME_SEC = 3600 * 24 * 20;

    private static final int TOKEN_LENGTH = 16;

    private final UserInfoRepositoryImpl userInfoRepository;

    private final TokenRedisRepositoryImpl tokenRedisRepository;

    public UserServiceImpl(UserInfoRepositoryImpl userInfoRepository, TokenRedisRepositoryImpl tokenRedisRepository) {
        this.userInfoRepository = userInfoRepository;
        this.tokenRedisRepository = tokenRedisRepository;
    }

    @Override
    public String login(String nickname, String identifier) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        if (userInfo == null) {
            return null;
        }
        if (!userInfo.getIdentifier().equals(identifier)) {
            return null;
        }
        String token = genRandomString();
        tokenRedisRepository.setUserToken(token, nickname, TOKEN_EXPIRE_TIME_SEC);
        return token;
    }

    @Override
    public void logout(UserInfoVo userInfoVo) {
        tokenRedisRepository.nullifyUserToken(userInfoVo.getToken());
    }

    @Override
    public boolean createNewUser(String nickname, String identifier) {
        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(nickname);
        userInfo.setIdentifier(identifier);
        return userInfoRepository.insertUserInfo(userInfo);
    }

    @Override
    public boolean isNicknameAvailable(String nickname) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(nickname);
        return userInfo == null;
    }

    private static String genRandomString() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < TOKEN_LENGTH; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
