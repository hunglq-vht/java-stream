package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component
public class SocketClient {
    MessageListener messageListener = new MessageListener();

    public SocketClient() {
        messageListener.connect("ws://127.0.0.1:65432");
    }
}
