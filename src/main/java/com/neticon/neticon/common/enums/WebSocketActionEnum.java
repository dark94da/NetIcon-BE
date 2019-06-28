package com.neticon.neticon.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebSocketActionEnum {
    TALK("talk", "发送聊天内容"),
    HEAR("hear", "收到聊天内容"),
    ONLINE("online", "用户上线"),
    OFFLINE("offline", "用户下线"),
    STATUS("status", "当前用户状态");

    private String name;
    private String desc;
}
