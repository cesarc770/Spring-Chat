package com.cesarcaceres.springchat.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Message {
    @JSONField(name = "username")
    private String username;

    @JSONField(name = "msg")
    private String message;

    @JSONField(name = "type")
    private String type;

    @JSONField(name = "onlinecount")
    private String onlineCount;


    public Message() {
    }

    public Message(String username, String message) {
        super();
        this.username = username;
        this.message = message;
        this.type = "SPEAK";
        this.onlineCount = "";
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
        this.onlineCount = onlineCount;
    }
}

