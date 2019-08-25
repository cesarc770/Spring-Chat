package com.cesarcaceres.springchat.controller;

import com.cesarcaceres.springchat.model.Message;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;


@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {
    private Session session;
    public static Gson gson = new Gson();
    public static Set<WebSocketChatServer> listeners = new CopyOnWriteArraySet<>();

    public static void sendMessageToAll(Message msg) {
        for (WebSocketChatServer listener : listeners) {
            listener.sendMessage(gson.toJson(msg));
        }
    }

    private void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
        } catch (IOException e) {
//            log.error("Caught exception while sending message to Session +
//            this.session.getId(), e.getMessage(), e);
        }
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        listeners.add(this);
    }

    @OnMessage
    public void onMessage(String JsonStr) {
        Message message = gson.fromJson(JsonStr, Message.class);
        sendMessageToAll(message);
    }


    @OnClose
    public void onClose(Session session) throws IOException {
        listeners.remove(this);
    }

    /**
     * Print exception.
     */
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
