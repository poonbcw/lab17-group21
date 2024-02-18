package com.websocket.demo.chat;

import org.springframework.stereotype.Service;

@Service
public class UserCount {
    private int usercount = 0;

    public void decrementUsercount() {
        usercount--;
    }

    public void incrementUsercount() {
        usercount++;
    }

    public int getUsercount() {
        return usercount;
    }
}
