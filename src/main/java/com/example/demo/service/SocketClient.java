package com.example.demo.service;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class SocketClient {

    @EventListener(ApplicationReadyEvent.class)
    public void ready() {
        System.out.println("App ready");
        MessageListener messageListener = new MessageListener();
        messageListener.connect("ws://127.0.0.1:65432");
    }
}
