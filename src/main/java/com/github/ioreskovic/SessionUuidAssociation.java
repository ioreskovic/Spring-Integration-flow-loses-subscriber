package com.github.ioreskovic;

import java.util.Map;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.integration.handler.GenericHandler;

@Log4j2
public class SessionUuidAssociation implements GenericHandler<WssAwarePayload<UUID>> {
    @Override
    public WssAwarePayload<UUID> handle(WssAwarePayload<UUID> x, Map<String, Object> headers) {
        // pretend we write this somehwere
        log.info("Associated session {} with UUID {}", x.getSession(), x.getPayload());
        return x;
    }
}
