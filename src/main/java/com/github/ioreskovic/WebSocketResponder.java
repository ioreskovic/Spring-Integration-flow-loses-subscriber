package com.github.ioreskovic;

import io.netty.buffer.ByteBuf;
import java.util.Map;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Log4j2
public class WebSocketResponder implements GenericHandler<WssAwarePayload<ByteBuf>> {

    @Override
    public Void handle(WssAwarePayload<ByteBuf> telemetryMessage, Map<String, Object> headers) {
        final WebSocketSession session = telemetryMessage.getSession();

        session.send(Mono.just(telemetryMessage)
            .map(tm -> tm.getPayload().nioBuffer())
            .doOnError(err -> log.error("Failed to extract raw response"))
            .map(bb -> session.binaryMessage(dbf -> dbf.wrap(bb)))
            .doOnError(err -> log.error("Failed to create WebSocketMessage", err))
            .doOnNext(wsm -> log.info("Sent WebSocketMessage {}", wsm)))
            .doOnError(err -> log.error("Failed to respond over WebSocketSession {}", session, err))
            .subscribe();

        return null;
    }
}
