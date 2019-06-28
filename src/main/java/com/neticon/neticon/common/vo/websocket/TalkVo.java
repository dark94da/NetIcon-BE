package com.neticon.neticon.common.vo.websocket;

import com.neticon.neticon.common.vo.websocket.BaseWebSocketVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TalkVo extends BaseWebSocketVo {
    private Integer roomId;
    private String content;
}
