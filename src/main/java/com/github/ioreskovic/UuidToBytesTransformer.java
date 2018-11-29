package com.github.ioreskovic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.integration.transformer.AbstractPayloadTransformer;

@Log4j2
public class UuidToBytesTransformer extends AbstractPayloadTransformer<WssAwarePayload<UUID>, WssAwarePayload<ByteBuf>> {
    @Override
    protected WssAwarePayload<ByteBuf> transformPayload(WssAwarePayload<UUID> x) {
        val uuid = x.getPayload();
        val bytes = Unpooled.buffer(16);
        bytes.writeLong(uuid.getMostSignificantBits());
        bytes.writeLong(uuid.getLeastSignificantBits());
        val y = x.copyWithPayload(bytes);
        log.info("Transformed {} to {}", x, y);
        return y;
    }
}
