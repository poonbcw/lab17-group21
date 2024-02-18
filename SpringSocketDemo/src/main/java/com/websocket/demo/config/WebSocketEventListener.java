package com.websocket.demo.config;

import com.websocket.demo.chat.ChatMessage;
import com.websocket.demo.chat.MessageType;
import com.websocket.demo.chat.UserCount;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component // ทำให้ spring boot มองเห็น class นี้
@RequiredArgsConstructor // จะ constructor flied ที่เป็น final ให้(messageTemplate)
// เราไม่สามารถ handle ใน controller ได้เพราะ event มันไม่ใช่การส่ง message เหมือนตอนเข้าที่พิมพ์ชื่อและตอนส่งข้อความ
public class WebSocketEventListener {
    private final SimpMessageSendingOperations messageTemplate; // เพราะเราไม่มี sendTo ให้ใช้
    private final UserCount userCount;
    @EventListener // listen to รับ argument ได้เพียงตัวเดียว
    public void handleWebsocketDisconnect(SessionDisconnectEvent event){  // event has header
//        System.out.println("Received a new web socket connection");

        // ใช้ stomp protocol  // getMessage จะเป็นการเอา message ที่ server ส่ง message เวลาคนออก // wrap เพื่อ get header
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username"); // get return object(name) not String
        if(username != null){
            var chatMessage = ChatMessage.builder()
                    .type(MessageType.LEAVE) // type message
                    .sender(username) // ชื่อคนออก
                    .build(); // สร้าง
            userCount.decrementUsercount();
            messageTemplate.convertAndSend("/topic/public", chatMessage);
            messageTemplate.convertAndSend("/topic/userCount", userCount.getUsercount());
        }
    }
}
