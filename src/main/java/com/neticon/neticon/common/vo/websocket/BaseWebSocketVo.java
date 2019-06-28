package com.neticon.neticon.common.vo.websocket;

import lombok.Data;

@Data
public abstract class BaseWebSocketVo {
    private String action;
    private String operator;
    private Integer createdAt;
}
