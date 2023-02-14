package com.example.wero.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


// WebSocket Config 파일
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


    // Stomp 는 WebSocket 을 통한 메시징을 위한 간단한 텍스트 지향 프로토콜입니다.
    // 메시지를 보내고 받을 뿐만 아니라 메시지 대기열을 구독 및 구독 취소하기 위한 간단한 API 를 제공합니다.


    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/stomp-end-websocket").setAllowedOriginPatterns("*").withSockJS();
    }

    // SockJS는 WebSockets 를 지원하지 않는 이전 브라우저에 대한 폴백 메커니즘을 제공하여
    // 개발자가 더 광범위한 브라우저에서 WebSockets 와 유사한 API 를 사용할 수 있도록 합니다.

    // registerStompEndpoints() 는 최초의 websocket 을 생성하는 endpoint 를 지정해줍니다.

}