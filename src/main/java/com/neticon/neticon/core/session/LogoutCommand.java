package com.neticon.neticon.core.session;

import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.core.BaseCommand;
import com.neticon.neticon.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class LogoutCommand extends BaseCommand {
    private final UserService userService;

    public LogoutCommand(UserService userService) {
        this.userService = userService;
    }

    public BaseResponse logout(UserInfoVo userInfoVo) {
        userService.logout(userInfoVo);
        return getSuccessResponse(null);
    }
}
