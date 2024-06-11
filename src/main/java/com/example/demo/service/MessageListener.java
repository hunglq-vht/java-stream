package com.example.demo.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import javax.annotation.PostConstruct;
import java.net.URI;

@Log4j2
@Component
public class MessageListener implements WebSocketHandler {
    private final WebSocketClient webSocketClient;
    private WebSocketSession session;

    public MessageListener() {
        this.webSocketClient = new StandardWebSocketClient();
    }

    @PostConstruct
    public void init() {
        log.info("init app");
        System.out.println("HELLO APP");
    }

    public void connect(String url) {
        this.webSocketClient.doHandshake(this, String.valueOf(URI.create(url)));
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        this.session = session;
        System.out.println("Connected to server");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage message) {
        System.out.println("Received message from server " + message.getPayload());
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exeption) {
        System.out.println("handleTransportError");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("afterConnectionClosed");
    }

    @Override
    public boolean supportsPartialMessages() {
        return true;
    }
}
