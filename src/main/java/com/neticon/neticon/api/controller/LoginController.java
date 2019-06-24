package com.neticon.neticon.api.controller;

import com.neticon.neticon.common.request.LoginRequest;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.response.LoginResponse;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.core.session.LoginCommand;
import com.neticon.neticon.core.session.LogoutCommand;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/session")
public class LoginController {
    private final LogoutCommand logoutCommand;

    private final LoginCommand loginCommand;

    public LoginController(LoginCommand loginCommand, LogoutCommand logoutCommand) {
        this.loginCommand = loginCommand;
        this.logoutCommand = logoutCommand;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return loginCommand.login(request);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public BaseResponse logout(@RequestAttribute("userInfo") UserInfoVo userInfoVo) {
        return logoutCommand.logout(userInfoVo);
    }
}
