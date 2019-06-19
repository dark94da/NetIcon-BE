package com.neticon.neticon.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NetIconErrorCodeEnum {
    SESSION_EXPIRED(401, "会话已过期"),
    USER_INFO_INVALID(402, "用户名或密码错误");

    private Integer code;
    private String msg;
}
