package com.skytakeaway.server.interceptor;
import com.skytakeaway.common.constant.JwtClaimsConstant;
import com.skytakeaway.common.context.BaseContext;
import com.skytakeaway.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Slf4j
public class TokenHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            org.springframework.http.server.ServerHttpRequest request,
            org.springframework.http.server.ServerHttpResponse response,
            org.springframework.web.socket.WebSocketHandler wsHandler,
            Map<String, Object> attributes) throws Exception {

        // Extract token from query parameters (from the URL)
        String token = request.getURI().getQuery().split("=")[1]; // Assumes token is passed like ?token=xxx

        // Validate the token (you can implement your own validation logic here)
        boolean isValid = validateToken(token);

        if (isValid) {
            return true;  // Proceed with the handshake if the token is valid
        } else {
            response.setStatusCode(org.springframework.http.HttpStatus.FORBIDDEN);  // Reject the connection
            return false;
        }
    }

    @Override
    public void afterHandshake(
            org.springframework.http.server.ServerHttpRequest request,
            org.springframework.http.server.ServerHttpResponse response,
            org.springframework.web.socket.WebSocketHandler wsHandler,
            Exception exception) {
        // Optional: handle after handshake (e.g., logging)
    }

    private boolean validateToken(String token) {
        if(!StringUtils.hasLength(token)){
            log.info("token is empty");
            return false;
        }

        try {
            JwtUtils.parseJwt(token);
        }catch (Exception e){
            e.printStackTrace();
            log.info("illegal token");
            return false;
        }

        log.info("token passed");
        return true;
    }
}

