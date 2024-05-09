package com.mxy.mypro.server;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint(value = "/plant")
@Component
public class plantWebsocket {
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        System.out.println("连接成功");
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("连接关闭");
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public void onMsg(String text, Session session) throws IOException {
        System.out.println("client 发送：" + text);
        System.out.println(session);
        broadcast(text);
    }
    private void broadcast(String message) {
        sessions.forEach(s -> {
            if (s.isOpen()) {
                s.getAsyncRemote().sendText(message);
            }
        });
    }
}
