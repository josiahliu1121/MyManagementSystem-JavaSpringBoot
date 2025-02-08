package com.skytakeaway.server.webSocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skytakeaway.pojo.entity.StaticRandomData;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class DataAnalysisWebSocketHandler extends TextWebSocketHandler {

    private Timer timer;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        // Start sending data every 1 second after the client connects
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sendData(session);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 1000);  // 1000 milliseconds = 1 second
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);

        // Stop sending data when the connection is closed
        if (timer != null) {
            timer.cancel();
        }
    }

    private void sendData(WebSocketSession session) throws JsonProcessingException {
        StaticRandomData.changeValue();

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> message = new HashMap<>();
        message.put("onlinePlayerCount", StaticRandomData.onlinePlayerCount);
        message.put("status", "ok");

        String jsonMessage = objectMapper.writeValueAsString(message);

        try {
            // Send JSON data to the client
            session.sendMessage(new TextMessage(jsonMessage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

