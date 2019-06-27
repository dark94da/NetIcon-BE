package com.neticon.neticon.api.controller;

import com.neticon.neticon.common.response.BaseResponse;
import com.neticon.neticon.common.response.CreateRoomResponse;
import com.neticon.neticon.common.vo.UserInfoVo;
import com.neticon.neticon.core.room.CreateNewRoomCommand;
import com.neticon.neticon.core.room.JoinRoomCommand;
import com.neticon.neticon.core.room.LeaveRoomCommand;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/room")
public class RoomController {
    private final CreateNewRoomCommand createNewRoomCommand;

    private final LeaveRoomCommand leaveRoomCommand;

    private final JoinRoomCommand joinRoomCommand;

    public RoomController(CreateNewRoomCommand createNewRoomCommand, LeaveRoomCommand leaveRoomCommand, JoinRoomCommand joinRoomCommand) {
        this.createNewRoomCommand = createNewRoomCommand;
        this.leaveRoomCommand = leaveRoomCommand;
        this.joinRoomCommand = joinRoomCommand;
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public BaseResponse<CreateRoomResponse> createNewRoom(@RequestAttribute("userInfo") UserInfoVo userInfoVo) {
        return createNewRoomCommand.createNewRoom(userInfoVo);
    }

    @ResponseBody
    @RequestMapping(value = "/{roomId}", method = RequestMethod.PUT)
    public BaseResponse joinRoom(@PathVariable Integer roomId, @RequestAttribute("userInfo") UserInfoVo userInfoVo) {
        return joinRoomCommand.joinRoom(userInfoVo, roomId);
    }

    @ResponseBody
    @RequestMapping(value = "/{roomId}", method = RequestMethod.DELETE)
    public BaseResponse leaveRoom(@PathVariable Integer roomId, @RequestAttribute("userInfo") UserInfoVo userInfoVo) {
        return leaveRoomCommand.leaveRoom(userInfoVo, roomId);
    }
}
