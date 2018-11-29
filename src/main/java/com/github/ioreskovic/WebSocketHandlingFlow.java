package com.github.ioreskovic;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.RequestUpgradeStrategy;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.ReactorNettyRequestUpgradeStrategy;

@Configuration
@EnableIntegration
public class WebSocketHandlingFlow {

    @Bean
    public Publisher<Message<Object>> entireFlow() {
        return IntegrationFlows
            .from((MessageChannel) webSocketMessageChannel())
            .filter(binaryWebSocketMessageSelector())
            .channel(binaryWebSocketMessageChannel())
            .transform(binaryWsmToBytesTransformer())
            .channel(rawBytesChannel())
            .transform(bytesToUuidTransformer())
            .channel(uuidChannel())
            .handle(sessionUuidAssociation())
            .channel(anotherUuidChannel())
            .transform(uuidToBytesTransformer())
            .handle(webSocketResponder())
            .toReactivePublisher();
    }

    @Bean
    public BytesToUuidTransformer bytesToUuidTransformer() {
        return new BytesToUuidTransformer();
    }

    @Bean
    public SessionUuidAssociation sessionUuidAssociation() {
        return new SessionUuidAssociation();
    }

    @Bean
    public UuidToBytesTransformer uuidToBytesTransformer() {
        return new UuidToBytesTransformer();
    }

    @Bean
    public WebSocketResponder webSocketResponder() {
        return new WebSocketResponder();
    }

    @Bean
    public BinaryWsmToBytesTransformer binaryWsmToBytesTransformer() {
        return new BinaryWsmToBytesTransformer();
    }

    @Bean
    public FluxMessageChannel webSocketMessageChannel() {
        return MessageChannels.flux("webSocketMessageChannel").get();
    }

    @Bean
    public FluxMessageChannel binaryWebSocketMessageChannel() {
        return MessageChannels.flux("binaryWebSocketMessageChannel").get();
    }

    @Bean
    public FluxMessageChannel rawBytesChannel() {
        return MessageChannels.flux("rawBytesChannel").get();
    }

    @Bean
    public FluxMessageChannel uuidChannel() {
        return MessageChannels.flux("uuidChannel").get();
    }

    @Bean
    public FluxMessageChannel anotherUuidChannel() {
        return MessageChannels.flux("anotherUuidChannel").get();
    }

    @Bean
    public BinaryWebSocketMessageSelector binaryWebSocketMessageSelector() {
        return new BinaryWebSocketMessageSelector();
    }

    @Bean
    public HandlerMapping handlerMapping() {
        final Map<String, WebSocketHandler> handlerMap = new HashMap<>();
        handlerMap.put("", CustomWebSocketHandler.builder()
            .channel(webSocketMessageChannel())
            .requestedSubProtocols(Collections.singleton("abc"))
            .build()
        );

        SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
        mapping.setUrlMap(handlerMap);
        mapping.setOrder(-1);
        return mapping;
    }


    @Bean
    public RequestUpgradeStrategy requestUpgradeStrategy() {
        return new ReactorNettyRequestUpgradeStrategy();
    }

    @Bean
    public WebSocketService webSocketService(RequestUpgradeStrategy upgradeStrategy) {
        return new HandshakeWebSocketService(upgradeStrategy);
    }

    @Bean
    public WebSocketHandlerAdapter handlerAdapter(WebSocketService webSocketService) {
        return new WebSocketHandlerAdapter(webSocketService);
    }
}
