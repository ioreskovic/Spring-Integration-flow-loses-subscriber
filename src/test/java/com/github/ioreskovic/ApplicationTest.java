package com.github.ioreskovic;

import java.net.URI;
import java.nio.ByteBuffer;
import java.time.Duration;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTest {
    @LocalServerPort
    private int port;

    @Test
    public void example() throws InterruptedException {
        final URI uri = new DefaultUriBuilderFactory().builder()
            .scheme("ws")
            .host("localhost")
            .port(port)
            .build();

        val client1 = new ReactorNettyWebSocketClient().execute(uri, new ClientHandler(UUID.randomUUID()));
        val client2 = new ReactorNettyWebSocketClient().execute(uri, new ClientHandler(UUID.randomUUID()));

        client1.subscribe();
        client2.subscribe();

        Thread.sleep(10_000);
    }

    private static class ClientHandler implements WebSocketHandler {
        private final UUID uuid;

        private ClientHandler(UUID uuid) {
            this.uuid = uuid;
        }

        @Override
        public Mono<Void> handle(WebSocketSession session) {
            return session.send(Mono.just(message(session, uuid)))
                .then(session.send(
                    session.receive().log()
                        .map(i -> session.binaryMessage(dbf -> dbf.wrap(asBytes(uuid)))))
                );
        }

        private final byte[] asBytes(UUID uuid) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            buffer.putLong(uuid.getMostSignificantBits());
            buffer.putLong(uuid.getLeastSignificantBits());
            return buffer.array();
        }

        private final UUID asUuid(ByteBuffer buffer) {
            return new UUID(buffer.getLong(), buffer.getLong());
        }

        private final WebSocketMessage message(WebSocketSession session, UUID uuid) {
            return session.binaryMessage(dbf -> dbf.wrap(asBytes(uuid)));
        }
    }
}