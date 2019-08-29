package com.cesarcaceres.springchat.controller;

import com.alibaba.fastjson.JSON;
import com.cesarcaceres.springchat.model.Message;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    public static void sendMessageToAll(Message msg) {
        for (Map.Entry<String, Session> session : onlineSessions.entrySet()) {
            try {
                Message toSend = new Message(msg.getUsername(), msg.getMessage(), "SPEAK" , Integer.toString(onlineSessions.size()));
                String message = JSON.toJSONString(toSend);
                session.getValue().getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        onlineSessions.put(session.getId(), session);
    }

    @OnMessage
    public void onMessage(String JsonStr) {
        Message message = JSON.parseObject(JsonStr, Message.class);
        sendMessageToAll(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        onlineSessions.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
