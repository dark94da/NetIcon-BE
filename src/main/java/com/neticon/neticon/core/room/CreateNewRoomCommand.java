package com.neticon.neticon.core.room;

import com.neticon.neticon.common.enums.NetIconErrorCodeEnum;
import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.response.CreateRoomResponse;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.core.BaseCommand;
import com.neticon.neticon.service.RoomService;
import org.springframework.stereotype.Component;

@Component
public class CreateNewRoomCommand extends BaseCommand {
    private final RoomService roomService;

    public CreateNewRoomCommand(RoomService roomService) {
        this.roomService = roomService;
    }

    public BaseResponse<CreateRoomResponse> createNewRoom(UserInfoVo userInfoVo) {
        int roomId = roomService.createNewRoom(userInfoVo.getName());
        if (roomId == 0) {
            return getFailResponse(NetIconErrorCodeEnum.ROOM_AMOUNT_REACT_LIMIT);
        }
        CreateRoomResponse response = new CreateRoomResponse();
        response.setRoomId(roomId);
        return getSuccessResponse(response);
    }
}
