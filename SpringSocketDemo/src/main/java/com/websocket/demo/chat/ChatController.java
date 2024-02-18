package com.websocket.demo.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations messageTemplate;
    private final UserCount userCount;
    // implement คนเข้ามาก่อน localHost/app/chat.addUser
    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public") // broadcastที่ไหน
    // receive ChatMessage and send // SimpMessageHeaderAccessorช่วยเข้าถึงheader
    public ChatMessage addUser(ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor){
        // get name from chatMessage and store in header table
        // SessionAttributes เป็นของแต่ละ user เราไม่ต้องใช้ map(เดี๋ยวเจอกรณีชื่อซ้ำ)
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        userCount.incrementUsercount();
        messageTemplate.convertAndSend("/topic/userCount", userCount.getUsercount());
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage") // ถ้า client อยากจะส่งข้อความ -> /app/chat.sendMessage
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage){
        return chatMessage;
    }

}
