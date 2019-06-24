package com.neticon.neticon.common.domain;

import java.io.Serializable;

public class RoomInfo implements Serializable {
    private Long id;

    private Integer roomId;

    private String memberList;

    private Long lastTalkAt;

    private Long createdAt;

    private Long updatedAt;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getMemberList() {
        return memberList;
    }

    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    public Long getLastTalkAt() {
        return lastTalkAt;
    }

    public void setLastTalkAt(Long lastTalkAt) {
        this.lastTalkAt = lastTalkAt;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }
}