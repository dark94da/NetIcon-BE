package com.neticon.neticon.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NetIconErrorCodeEnum {
    SESSION_EXPIRED(401, "会话已过期"),
    USER_INFO_INVALID(402, "用户名或密码错误"),
    INVALID_TOKEN(403, "无效的用户凭证"),
    INVALID_PARAMS(404, "错误的请求参数"),
    UNAVAILABLE_NICKNAME(405, "用户名不可用"),
    DB_EXCEPTION(406, "数据库错误"),
    ROOM_AMOUNT_REACT_LIMIT(407, "无可用房间号");

    private Integer code;
    private String msg;
}
