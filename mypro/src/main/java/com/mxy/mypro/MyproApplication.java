package com.mxy.mypro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
@EnableWebSocket
@MapperScan("com.mxy.mypro.mapper")
public class MyproApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyproApplication.class, args);
    }

}
