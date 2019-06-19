package com.neticon.neticon.core.user;

import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.common.request.LoginRequest;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.response.LoginResponse;
import com.neticon.neticon.core.BaseCommand;
import com.neticon.neticon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginCommand extends BaseCommand {
    @Autowired
    private UserService userService;

    public BaseResponse<LoginResponse> login(LoginRequest request) {
        String token = userService.login(request.getNickname(), request.getIdentifier());
        if (token == null) {
            return getFailResponse(NetIconErrorCodeEnum.USER_INFO_INVALID);
        }
        LoginResponse response = new LoginResponse();
        response.setToken(token);
        return getSuccessResponse(response);
    }
}
