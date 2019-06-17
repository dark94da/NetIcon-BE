package com.neticon.neticon.api.controller;

import com.neticon.neticon.common.domain.request.LoginRequest;
import com.neticon.neticon.common.domain.response.BaseResponse;
import com.neticon.neticon.common.domain.response.LoginResponse;
import com.neticon.neticon.core.user.LoginCommand;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final LoginCommand loginCommand;

    public UserController(LoginCommand loginCommand) {
        this.loginCommand = loginCommand;
    }

    @ResponseBody
    @RequestMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        return loginCommand.login(request);
    }
}
