package com.neticon.neticon.common.domain;

import java.io.Serializable;

public class UserInfo implements Serializable {
    private Long id;

    private String nickname;

    private String identifier;

    private String roomList;

    private String pendingList;

    private Long createdAt;

    private Long updatedAt;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getRoomList() {
        return roomList;
    }

    public void setRoomList(String roomList) {
        this.roomList = roomList;
    }

    public String getPendingList() {
        return pendingList;
    }

    public void setPendingList(String pendingList) {
        this.pendingList = pendingList;
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