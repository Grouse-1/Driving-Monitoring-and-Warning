package com.mxy.mypro.server;

import com.mxy.mypro.entity.EmotionData;
import com.mxy.mypro.entity.Rfidtmp;
import com.mxy.mypro.enums.LocationEnum;
import com.mxy.mypro.mapper.EmotionMapper;
import com.mxy.mypro.service.RfidtmpService;
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

@ServerEndpoint(value = "/emotion")
@Component
public class rpiWebsocket {
    private static String emotion = "";
    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // 单线程执行器

    private static EmotionMapper emotionMapper;
    @Autowired
    public  void setRfidtmpService(EmotionMapper emotionMapper) {
        rpiWebsocket.emotionMapper = emotionMapper;
    }
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
        LocalDateTime now = LocalDateTime.now();
        String formattedTime = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println(text);
        if(text.equals(":")){
            return;
        }
        String text1 = text.split(":")[0];
        int count = Integer.parseInt(text.split(":")[1]);
        EmotionData emotionData = new EmotionData(emotionMapper.GetElderLocation(), text1, formattedTime,1,count);
        emotionMapper.insert(emotionData);
        System.out.println("client 发送：" + text);
        //System.out.println(session);
        //broadcast(text);
        broadcast(text);
    }
//    private void broadcast(String message) {
//        sessions.forEach(s -> {
//            if (s.isOpen()) {
//                s.getAsyncRemote().sendText("广播消息: " + message);
//            }
//        });
//    }
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
