package com.cesarcaceres.springchat.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Message {
    @JSONField(name = "username")
    private String username;

    @JSONField(name = "msg")
    private String message;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "onlineCount")
    private String onlineCount;


    public Message() {}

    public Message(String username, String message) {
        this.username = username;
        this.message = message;
        this.type = "";
        this.onlineCount = "0";
    }

    public Message(String username, String message, String type, String onlineCount) {
        this.username = username;
        this.message = message;
        this.type = type;
        this.onlineCount = onlineCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(String onlineCount) {
        this.onlineCount = Integer.toString(Integer.parseInt(this.getOnlineCount()) + 1);
    }
}

