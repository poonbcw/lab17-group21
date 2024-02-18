package com.websocket.demo.chat;


import lombok.Builder;
import lombok.Getter;

@Getter // can get everything from this class (flied)
@Builder // build constructor
public class ChatMessage {
    private String content;
    private String timestamp;
    private String sender;
    private MessageType type;
}
