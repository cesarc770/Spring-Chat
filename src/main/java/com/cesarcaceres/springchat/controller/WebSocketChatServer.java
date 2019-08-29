package com.cesarcaceres.springchat.controller;

import com.alibaba.fastjson.JSON;
import com.cesarcaceres.springchat.model.Message;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {
    public static Gson gson = new Gson();
    public static Set<WebSocketChatServer> listeners = new CopyOnWriteArraySet<>();
    private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();

    public static void sendMessageToAll(Message msg) {
        for (Map.Entry<String, Session> session : onlineSessions.entrySet()) {
            try {
                String message = JSON.toJSONString(msg);
                session.getValue().getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private void sendMessage(String message) {
////        try {
////            this.session.getBasicRemote().sendText(message);
////        } catch (IOException e) {
//////            log.error("Caught exception while sending message to Session +
//////            this.session.getId(), e.getMessage(), e);
////        }
////    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        onlineSessions.put(session.getId(), session);
    }

    @OnMessage
    public void onMessage(String JsonStr) {
        System.out.println(JsonStr);
        Message message = JSON.parseObject(JsonStr, Message.class);
        sendMessageToAll(message);
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        onlineSessions.remove(session.getId());
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
