package com.github.ioreskovic;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;
import org.springframework.web.reactive.socket.WebSocketSession;

@Builder
@ToString
@Value
public class WssAwarePayload<T> {
    @lombok.NonNull
    private T payload;

    @lombok.NonNull
    private WebSocketSession session;

    public <R> WssAwarePayload<R> copyWithPayload(R newPayload) {
        return WssAwarePayload.<R>builder()
            .session(session)
            .payload(newPayload)
            .build();
    }
}
