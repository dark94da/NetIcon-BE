package com.neticon.neticon.core.user;

import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.common.request.CreateUserRequest;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.core.BaseCommand;
import com.neticon.neticon.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserCommand extends BaseCommand {
    private final UserService userService;

    public CreateUserCommand(UserService userService) {
        this.userService = userService;
    }

    public BaseResponse createUser(CreateUserRequest request) {
        if (request.getNickname() == null || request.getIdentifier() == null) {
            return getFailResponse(NetIconErrorCodeEnum.INVALID_PARAMS);
        }
        if (!userService.isNicknameAvailable(request.getNickname())) {
            return getFailResponse(NetIconErrorCodeEnum.UNAVAILABLE_NICKNAME);
        }
        if (userService.createNewUser(request.getNickname(), request.getIdentifier())) {
            return getSuccessResponse(null);
        } else {
            return getFailResponse(NetIconErrorCodeEnum.DB_EXCEPTION);
        }
    }
}
