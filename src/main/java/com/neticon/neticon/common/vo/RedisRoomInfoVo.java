package com.neticon.neticon.common.vo;

import lombok.Data;

import java.util.List;

@Data
public class RedisRoomInfoVo {
    private Integer roomId;
    private List<String> members;
    private Boolean isActive;
}
