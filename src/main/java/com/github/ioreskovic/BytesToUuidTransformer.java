package com.github.ioreskovic;

import io.netty.buffer.ByteBuf;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

@Log4j2
public class BytesToUuidTransformer extends AbstractPayloadTransformer<WssAwarePayload<ByteBuf>, WssAwarePayload<UUID>> {
    @Override
    protected WssAwarePayload<UUID> transformPayload(WssAwarePayload<ByteBuf> x) {
        val bytes = x.getPayload();
        val msb = bytes.readLong();
        val lsb = bytes.readLong();
        val y = x.copyWithPayload(new UUID(msb, lsb));
        log.info("Transformed {} to {}", x, y);
        return y;
    }
}
