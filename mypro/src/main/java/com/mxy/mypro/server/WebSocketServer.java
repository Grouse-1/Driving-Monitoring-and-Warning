package com.mxy.mypro.server;

import com.mxy.mypro.entity.Rfidtmp;
import com.mxy.mypro.enums.LocationEnum;
import com.mxy.mypro.service.AdminService;
import com.mxy.mypro.service.RfidtmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@ServerEndpoint(value = "/elder")
@Component

public class WebSocketServer {

    private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private ExecutorService executorService = Executors.newSingleThreadExecutor(); // 单线程执行器
    private static RfidtmpService rfidtmpService;
    @Autowired
    public  void setRfidtmpService(RfidtmpService rfidtmpService) {
        WebSocketServer.rfidtmpService = rfidtmpService;
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
        System.out.println("client 发送：" + text);
        System.out.println(session);
        String text1 = text.replace(" ", "");
        String[] parts = text1.split(":");
        if (parts.length > 1) {
            System.out.println("老人定位地点更新");
            if(parts[0].equals(LocationEnum.PARK.getName())) {
                rfidtmpService.updateLocation(parts[1], LocationEnum.PARK.getValue());
            }else if(parts[0].equals(LocationEnum.MARKET.getName())){
                rfidtmpService.updateLocation(parts[1], LocationEnum.MARKET.getValue());
            }else{
                System.out.println("不存在该地点"+parts[0]);
            }
        }else {
            System.out.println("登录老人rfid信息");
            broadcast(text1);
        }
    }
//    private void broadcast(String message) {
//        sessions.forEach(s -> {
//            if (s.isOpen()) {
//                s.getAsyncRemote().sendText(message);
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
