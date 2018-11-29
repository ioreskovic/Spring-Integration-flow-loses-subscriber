package com.github.ioreskovic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.integration.transformer.AbstractPayloadTransformer;
import org.springframework.web.reactive.socket.WebSocketMessage;

@Log4j2
public class BinaryWsmToBytesTransformer extends AbstractPayloadTransformer<WssAwarePayload<WebSocketMessage>, WssAwarePayload<ByteBuf>> {
    @Override
    protected WssAwarePayload<ByteBuf> transformPayload(WssAwarePayload<WebSocketMessage> x) {
        val bwsm = x.getPayload();
        val y = x.copyWithPayload(Unpooled.copiedBuffer(bwsm.getPayload().asByteBuffer()));
        log.info("Transformed {} to {}", x, y);
        return y;
    }
}
