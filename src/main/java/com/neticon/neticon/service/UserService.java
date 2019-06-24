package com.neticon.neticon.service;

import com.neticon.neticon.common.vo.UserInfoVo;

public interface UserService {
    String login(String nickname, String identifier);

    void logout(UserInfoVo userInfoVo);

    boolean createNewUser(String nickname, String identifier);

    boolean isNicknameAvailable(String nickname);
}
