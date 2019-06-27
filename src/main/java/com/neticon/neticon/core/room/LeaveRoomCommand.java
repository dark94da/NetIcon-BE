package com.neticon.neticon.core.room;

import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.core.BaseCommand;
import com.neticon.neticon.service.RoomService;
import org.springframework.stereotype.Component;

@Component
public class LeaveRoomCommand extends BaseCommand {
    private final RoomService roomService;

    public LeaveRoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    public BaseResponse leaveRoom(UserInfoVo userInfoVo, Integer roomId) {
        if (roomId == null || !roomService.isUserInRoom(userInfoVo.getName(), roomId)) {
            return getFailResponse(NetIconErrorCodeEnum.INVALID_PARAMS);
        }
        roomService.deleteRoom(roomId);
        return getSuccessResponse(null);
    }
}
