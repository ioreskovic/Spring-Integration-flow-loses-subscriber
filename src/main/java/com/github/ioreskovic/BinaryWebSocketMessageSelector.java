package com.github.ioreskovic;

import org.springframework.integration.core.GenericSelector;
import org.springframework.web.reactive.socket.WebSocketMessage;

public class BinaryWebSocketMessageSelector implements GenericSelector<WssAwarePayload<WebSocketMessage>> {
    @Override
    public boolean accept(WssAwarePayload<WebSocketMessage> source) {
        return WebSocketMessage.Type.BINARY.equals(source.getPayload().getType());
    }
}
