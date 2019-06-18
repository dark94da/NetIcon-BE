package com.neticon.neticon.core.user;

import com.neticon.neticon.common.domain.UserInfo;
import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.common.request.LoginRequest;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.response.LoginResponse;
import com.neticon.neticon.core.BaseCommand;
import com.neticon.neticon.repository.mybatis.impl.UserInfoRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCommand extends BaseCommand {
    @Autowired
    UserInfoRepositoryImpl userInfoRepository;

    public BaseResponse<LoginResponse> login(LoginRequest request) {
        UserInfo userInfo = userInfoRepository.getUserInfoByNickname(request.getNickname());
        return getFailResponse(NetIconErrorCodeEnum.SESSION_EXPIRED);
    }
}
