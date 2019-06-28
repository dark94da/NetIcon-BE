package com.neticon.neticon.common.vo.websocket;

import com.neticon.neticon.common.vo.websocket.BaseWebSocketVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class HearVo extends BaseWebSocketVo {
    private String content;
}
