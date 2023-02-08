package com.example.wero.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    // 위에서 확인할 수 있는 setApplicationDestinationPrefixes()는 말그대로 이 어플리케이션의 접두어 정도의 역할을 한다.
    // enableSimpleBroker()는 메모리기반의 Message Broker 를 선언해주는데
    // 파라미터로 넘어간 “/topic” 이 접두어로 붙어있는 클라이언트들에게 메세지를 전달해주는 역할을 한다.



    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/gs-guide-websocket").withSockJS();
    }

}