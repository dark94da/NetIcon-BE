package com.neticon.neticon.core.user;

import com.neticon.neticon.common.domain.request.LoginRequest;
import com.neticon.neticon.common.domain.response.BaseResponse;
import com.neticon.neticon.common.domain.response.LoginResponse;
import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.core.BaseCommand;
import org.springframework.stereotype.Component;

@Component
public class LoginCommand extends BaseCommand {
    public BaseResponse<LoginResponse> login(LoginRequest request) {
        return getFailResponse(NetIconErrorCodeEnum.SESSION_EXPIRED);
    }
}
