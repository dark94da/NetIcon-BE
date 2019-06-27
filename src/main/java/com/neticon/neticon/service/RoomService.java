package com.neticon.neticon.service;

public interface RoomService {
    int createNewRoom(String nickname);

    void deleteRoom(int roomId);

    boolean isUserInRoom(String nickname, int roomId);

    void joinRoom(int roomId, String nickname);

    boolean isRoomAvailable(int roomId);
}
