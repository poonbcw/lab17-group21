package com.websocket.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration // ประกาศว่า class นี้จะเป็นส่วนหนึ่งของ spring boot ที่เป็น configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    // กำหนด path ที่ client จะเข้ามาที่ server
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws") // เข้า local host ของเรายังไง handshake -> localhost:8080/ws
                .setAllowedOriginPatterns("*") // allow address อะไรบ้าง * คือ allow all
                .withSockJS(); // ช่วยให้ java connect with socket ได้ ไม่สามารถ direct connect ได้แบบ http
    }

    @Override
    // server จะรับผ่านทางไหน broadcast ผ่านทางไหน
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // ระดับของ app อันนี้คือ global
        // .setUserDestinationPrefix() คนต่อคน
        registry.setApplicationDestinationPrefixes("/app") // path ที่ server รับข้อความจาก client -> localhost:8080/app
                .enableSimpleBroker("/topic"); // broadcast ผ่านที่ใด -> subscribe topic
    }
}
