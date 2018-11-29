package com.github.ioreskovic;

import java.util.List;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.springframework.integration.channel.FluxMessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Builder
public class CustomWebSocketHandler implements WebSocketHandler {
    @Singular
    @NonNull
    private List<String> requestedSubProtocols;

    @NonNull
    private FluxMessageChannel channel;

    @Override
    public List<String> getSubProtocols() {
        return requestedSubProtocols;
    }

    @Override
    @org.springframework.lang.NonNull
    public Mono<Void> handle(@org.springframework.lang.NonNull WebSocketSession session) {

        final Flux<Message<?>> messageFlux = session.receive()
            .map(wsm -> WssAwarePayload.<WebSocketMessage>builder().payload(wsm).session(session).build())
            .map(rtm -> MessageBuilder.withPayload(rtm).build());

        channel.subscribeTo(messageFlux);

        return Mono.never();
    }
}
