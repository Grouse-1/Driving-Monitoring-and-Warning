package com.mxy.mypro.server;

import com.mxy.mypro.entity.EmotionData;
import com.mxy.mypro.mapper.EmotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/shop")
@Component
public class shopWebsocket {
    private static String emotion = "";
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // 单线程执行器

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
        String text1 = text.replace(" ", "");
        System.out.println("client 发送：" + text1);
        broadcast(text1);
    }
    private void broadcast(String message) {
        sessions.forEach(session -> {
            if (session.isOpen()) {
                executorService.submit(() -> {
                    try {
                        session.getBasicRemote().sendText(message);
                    } catch (IOException e) {
                        System.err.println("Failed to send message: " + e.getMessage());
                    }
                });
            }
        });
    }
}
