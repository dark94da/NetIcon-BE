package com.neticon.neticon.api.controller;

import com.neticon.neticon.common.request.CreateUserRequest;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.core.user.CreateUserCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private final CreateUserCommand createUserCommand;

    public UserController(CreateUserCommand createUserCommand) {
        this.createUserCommand = createUserCommand;
    }

    @ResponseBody
    @RequestMapping(value = "/{nickname}", method = RequestMethod.POST)
    public BaseResponse createUser(@PathVariable String nickname, @RequestBody CreateUserRequest request) {
        request.setNickname(nickname);
        return createUserCommand.createUser(request);
    }
}
