package com.skytakeaway.server.config;

import com.skytakeaway.server.interceptor.TokenHandshakeInterceptor;
import com.skytakeaway.server.webSocket.DataAnalysisWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(myWebSocketHandler(), "/ws") // WebSocket URL
                .setAllowedOrigins("*")  // Allow connections from all origins
                .addInterceptors(new TokenHandshakeInterceptor());  // Add the token validation interceptor
    }

    @Bean
    public WebSocketHandler myWebSocketHandler() {
        return new DataAnalysisWebSocketHandler();
    }
}

