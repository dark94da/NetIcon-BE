package com.neticon.neticon.common.vo.websocket;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatusVo extends BaseWebSocketVo {
    private List<Integer> onlineRoomList;
}
