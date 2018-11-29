# Spring-Integration-flow-loses-subscriber
Demonstrates issue: https://stackoverflow.com/questions/53519580/spring-integration-flow-loses-subscriber

## How to replicate?
#### Prerequisites
* [Google Chrome](https://www.google.com/chrome/)
* [Dark WebSocket Terminal Google Chrome App](https://chrome.google.com/webstore/detail/dark-websocket-terminal/dmogdjmcpfaibncngoolgljgocdabhke?hl=en)

1. Run Application on `host`

On 2 different machines:
1. In Dark WebSocket Terminal, load payload with `/loadbin uuidBytes`
2. Select `RandomUUID.hex` file
3. Connect to application: `/connect ws://<hostIP>:8080`
4. Set it to run for some time: `/interval 200 /b [bin(uuidBytes)]`

## Observations
After some time, an exception will occur consuming bytes from WebSocket message
```log
2018-11-29 14:52:40.943  WARN 10288 --- [ctor-http-nio-3] o.s.i.c.MessagePublishingErrorHandler    : Error message was not delivered.

org.springframework.messaging.MessageHandlingException: error occurred in message handler [_org.springframework.integration.errorLogger.handler]; nested exception is io.netty.util.IllegalReferenceCountException: refCnt: 0
	at org.springframework.integration.support.utils.IntegrationUtils.wrapInHandlingExceptionIfNecessary(IntegrationUtils.java:184) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:175) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.dispatcher.BroadcastingDispatcher.invokeHandler(BroadcastingDispatcher.java:224) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.dispatcher.BroadcastingDispatcher.dispatch(BroadcastingDispatcher.java:180) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.channel.AbstractSubscribableChannel.doSend(AbstractSubscribableChannel.java:73) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:445) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:181) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:160) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:47) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:108) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.integration.channel.MessagePublishingErrorHandler.handleError(MessagePublishingErrorHandler.java:93) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.endpoint.ReactiveStreamsConsumer$1.hookOnNext(ReactiveStreamsConsumer.java:141) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.endpoint.ReactiveStreamsConsumer$1.hookOnNext(ReactiveStreamsConsumer.java:127) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at reactor.core.publisher.BaseSubscriber.onNext(BaseSubscriber.java:158) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxRetry$RetrySubscriber.onNext(FluxRetry.java:79) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:185) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPublish$PublishSubscriber.drain(FluxPublish.java:461) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPublish$PublishSubscriber.onNext(FluxPublish.java:263) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxCreate$IgnoreSink.next(FluxCreate.java:568) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxCreate$SerializedSink.next(FluxCreate.java:146) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at org.springframework.integration.channel.FluxMessageChannel.doSend(FluxMessageChannel.java:65) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:445) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:394) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:181) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:160) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:47) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:108) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.integration.handler.AbstractMessageProducingHandler.sendOutput(AbstractMessageProducingHandler.java:426) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageProducingHandler.produceOutput(AbstractMessageProducingHandler.java:336) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageProducingHandler.sendOutputs(AbstractMessageProducingHandler.java:227) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractReplyProducingMessageHandler.handleMessageInternal(AbstractReplyProducingMessageHandler.java:115) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:158) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageHandler.onNext(AbstractMessageHandler.java:205) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageHandler.onNext(AbstractMessageHandler.java:55) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.endpoint.ReactiveStreamsConsumer$1.hookOnNext(ReactiveStreamsConsumer.java:138) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.endpoint.ReactiveStreamsConsumer$1.hookOnNext(ReactiveStreamsConsumer.java:127) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at reactor.core.publisher.BaseSubscriber.onNext(BaseSubscriber.java:158) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxRetry$RetrySubscriber.onNext(FluxRetry.java:79) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:185) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPublish$PublishSubscriber.drain(FluxPublish.java:461) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPublish$PublishSubscriber.onNext(FluxPublish.java:263) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxCreate$IgnoreSink.next(FluxCreate.java:568) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxCreate$SerializedSink.drainLoop(FluxCreate.java:230) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxCreate$SerializedSink.next(FluxCreate.java:161) ~[reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at org.springframework.integration.channel.FluxMessageChannel.doSend(FluxMessageChannel.java:65) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:445) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:394) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:177) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:185) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.core.publisher.FluxFilter$FilterSubscriber.onNext(FluxFilter.java:97) [reactor-core-3.1.7.RELEASE.jar:3.1.7.RELEASE]
	at reactor.ipc.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:211) [reactor-netty-0.7.7.RELEASE.jar:0.7.7.RELEASE]
	at reactor.ipc.netty.channel.FluxReceive.onInboundNext(FluxReceive.java:326) [reactor-netty-0.7.7.RELEASE.jar:0.7.7.RELEASE]
	at reactor.ipc.netty.channel.ChannelOperations.onInboundNext(ChannelOperations.java:309) [reactor-netty-0.7.7.RELEASE.jar:0.7.7.RELEASE]
	at reactor.ipc.netty.http.server.HttpServerOperations.onInboundNext(HttpServerOperations.java:415) [reactor-netty-0.7.7.RELEASE.jar:0.7.7.RELEASE]
	at reactor.ipc.netty.http.server.HttpServerWSOperations.onInboundNext(HttpServerWSOperations.java:108) [reactor-netty-0.7.7.RELEASE.jar:0.7.7.RELEASE]
	at reactor.ipc.netty.channel.ChannelOperationsHandler.channelRead(ChannelOperationsHandler.java:136) [reactor-netty-0.7.7.RELEASE.jar:0.7.7.RELEASE]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:102) [netty-codec-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:310) [netty-codec-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:284) [netty-codec-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1434) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:965) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:645) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:580) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:497) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:459) [netty-transport-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:884) [netty-common-4.1.24.Final.jar:4.1.24.Final]
	at java.lang.Thread.run(Thread.java:748) [?:1.8.0_161]
Caused by: io.netty.util.IllegalReferenceCountException: refCnt: 0
	at io.netty.buffer.AbstractByteBuf.ensureAccessible(AbstractByteBuf.java:1417) ~[netty-buffer-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.buffer.AbstractByteBuf.checkIndex(AbstractByteBuf.java:1356) ~[netty-buffer-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.buffer.AbstractByteBuf.getInt(AbstractByteBuf.java:417) ~[netty-buffer-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.buffer.ByteBufUtil.hashCode(ByteBufUtil.java:175) ~[netty-buffer-4.1.24.Final.jar:4.1.24.Final]
	at io.netty.buffer.AbstractByteBuf.hashCode(AbstractByteBuf.java:1315) ~[netty-buffer-4.1.24.Final.jar:4.1.24.Final]
	at org.springframework.core.io.buffer.NettyDataBuffer.hashCode(NettyDataBuffer.java:288) ~[spring-core-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at org.springframework.web.reactive.socket.WebSocketMessage.hashCode(WebSocketMessage.java:134) ~[spring-webflux-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at java.lang.Object.toString(Object.java:236) ~[?:1.8.0_161]
	at java.lang.String.valueOf(String.java:2994) ~[?:1.8.0_161]
	at java.lang.StringBuilder.append(StringBuilder.java:131) ~[?:1.8.0_161]
	at com.github.ioreskovic.WssAwarePayload.toString(WssAwarePayload.java:9) ~[classes/:?]
	at java.lang.String.valueOf(String.java:2994) ~[?:1.8.0_161]
	at java.lang.StringBuilder.append(StringBuilder.java:131) ~[?:1.8.0_161]
	at org.springframework.messaging.support.GenericMessage.toString(GenericMessage.java:111) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at java.lang.String.valueOf(String.java:2994) ~[?:1.8.0_161]
	at java.lang.StringBuilder.append(StringBuilder.java:131) ~[?:1.8.0_161]
	at org.springframework.messaging.MessagingException.toString(MessagingException.java:74) ~[spring-messaging-5.0.6.RELEASE.jar:5.0.6.RELEASE]
	at java.lang.String.valueOf(String.java:2994) ~[?:1.8.0_161]
	at java.io.PrintWriter.println(PrintWriter.java:754) ~[?:1.8.0_161]
	at java.lang.Throwable$WrappedPrintWriter.println(Throwable.java:764) ~[?:1.8.0_161]
	at java.lang.Throwable.printStackTrace(Throwable.java:655) ~[?:1.8.0_161]
	at java.lang.Throwable.printStackTrace(Throwable.java:721) ~[?:1.8.0_161]
	at org.springframework.integration.handler.LoggingHandler.printStackTrace(LoggingHandler.java:232) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.LoggingHandler.createLogMessage(LoggingHandler.java:226) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.LoggingHandler.createLogMessage(LoggingHandler.java:213) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.LoggingHandler.handleMessageInternal(LoggingHandler.java:182) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:158) ~[spring-integration-core-5.0.5.RELEASE.jar:5.0.5.RELEASE]
	... 81 more
```

and after that, subscriber detaches:

```log
2018-11-29 14:52:41.106 ERROR 10288 --- [ctor-http-nio-2] o.s.i.h.LoggingHandler                   : org.springframework.messaging.MessageDeliveryException: failed to send Message to channel 'binaryWebSocketMessageChannel'; nested exception is java.lang.IllegalStateException: The [binaryWebSocketMessageChannel] doesn't have subscribers to accept messages, failedMessage=GenericMessage [payload=WssAwarePayload(payload=org.springframework.web.reactive.socket.WebSocketMessage@37a819d1, session=ReactorNettyWebSocketSession[id=27d6095d, uri=http://localhost:8080/]), headers={id=8019b383-de4b-6abd-43a4-256161428f8c, timestamp=1543499561105}]
	at org.springframework.integration.support.utils.IntegrationUtils.wrapInDeliveryExceptionIfNecessary(IntegrationUtils.java:163)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:475)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:394)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:181)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:160)
	at org.springframework.messaging.core.GenericMessagingTemplate.doSend(GenericMessagingTemplate.java:47)
	at org.springframework.messaging.core.AbstractMessageSendingTemplate.send(AbstractMessageSendingTemplate.java:108)
	at org.springframework.integration.handler.AbstractMessageProducingHandler.sendOutput(AbstractMessageProducingHandler.java:426)
	at org.springframework.integration.handler.AbstractMessageProducingHandler.produceOutput(AbstractMessageProducingHandler.java:336)
	at org.springframework.integration.handler.AbstractMessageProducingHandler.sendOutputs(AbstractMessageProducingHandler.java:227)
	at org.springframework.integration.handler.AbstractReplyProducingMessageHandler.handleMessageInternal(AbstractReplyProducingMessageHandler.java:115)
	at org.springframework.integration.handler.AbstractMessageHandler.handleMessage(AbstractMessageHandler.java:158)
	at org.springframework.integration.handler.AbstractMessageHandler.onNext(AbstractMessageHandler.java:205)
	at org.springframework.integration.handler.AbstractMessageHandler.onNext(AbstractMessageHandler.java:55)
	at org.springframework.integration.endpoint.ReactiveStreamsConsumer$1.hookOnNext(ReactiveStreamsConsumer.java:138)
	at org.springframework.integration.endpoint.ReactiveStreamsConsumer$1.hookOnNext(ReactiveStreamsConsumer.java:127)
	at reactor.core.publisher.BaseSubscriber.onNext(BaseSubscriber.java:158)
	at reactor.core.publisher.FluxRetry$RetrySubscriber.onNext(FluxRetry.java:79)
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:185)
	at reactor.core.publisher.FluxPublish$PublishSubscriber.drain(FluxPublish.java:461)
	at reactor.core.publisher.FluxPublish$PublishSubscriber.onNext(FluxPublish.java:263)
	at reactor.core.publisher.FluxCreate$IgnoreSink.next(FluxCreate.java:568)
	at reactor.core.publisher.FluxCreate$SerializedSink.next(FluxCreate.java:146)
	at org.springframework.integration.channel.FluxMessageChannel.doSend(FluxMessageChannel.java:65)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:445)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:394)
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:177)
	at reactor.core.publisher.FluxPeek$PeekSubscriber.onNext(FluxPeek.java:185)
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108)
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108)
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108)
	at reactor.core.publisher.FluxMap$MapSubscriber.onNext(FluxMap.java:108)
	at reactor.core.publisher.FluxFilter$FilterSubscriber.onNext(FluxFilter.java:97)
	at reactor.ipc.netty.channel.FluxReceive.drainReceiver(FluxReceive.java:211)
	at reactor.ipc.netty.channel.FluxReceive.onInboundNext(FluxReceive.java:326)
	at reactor.ipc.netty.channel.ChannelOperations.onInboundNext(ChannelOperations.java:309)
	at reactor.ipc.netty.http.server.HttpServerOperations.onInboundNext(HttpServerOperations.java:415)
	at reactor.ipc.netty.http.server.HttpServerWSOperations.onInboundNext(HttpServerWSOperations.java:108)
	at reactor.ipc.netty.channel.ChannelOperationsHandler.channelRead(ChannelOperationsHandler.java:136)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)
	at io.netty.handler.codec.MessageToMessageDecoder.channelRead(MessageToMessageDecoder.java:102)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)
	at io.netty.handler.codec.ByteToMessageDecoder.fireChannelRead(ByteToMessageDecoder.java:310)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:284)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at io.netty.channel.AbstractChannelHandlerContext.fireChannelRead(AbstractChannelHandlerContext.java:340)
	at io.netty.channel.DefaultChannelPipeline$HeadContext.channelRead(DefaultChannelPipeline.java:1434)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:362)
	at io.netty.channel.AbstractChannelHandlerContext.invokeChannelRead(AbstractChannelHandlerContext.java:348)
	at io.netty.channel.DefaultChannelPipeline.fireChannelRead(DefaultChannelPipeline.java:965)
	at io.netty.channel.nio.AbstractNioByteChannel$NioByteUnsafe.read(AbstractNioByteChannel.java:163)
	at io.netty.channel.nio.NioEventLoop.processSelectedKey(NioEventLoop.java:645)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeysOptimized(NioEventLoop.java:580)
	at io.netty.channel.nio.NioEventLoop.processSelectedKeys(NioEventLoop.java:497)
	at io.netty.channel.nio.NioEventLoop.run(NioEventLoop.java:459)
	at io.netty.util.concurrent.SingleThreadEventExecutor$5.run(SingleThreadEventExecutor.java:884)
	at java.lang.Thread.run(Thread.java:748)
Caused by: java.lang.IllegalStateException: The [binaryWebSocketMessageChannel] doesn't have subscribers to accept messages
	at org.springframework.util.Assert.state(Assert.java:94)
	at org.springframework.integration.channel.FluxMessageChannel.doSend(FluxMessageChannel.java:63)
	at org.springframework.integration.channel.AbstractMessageChannel.send(AbstractMessageChannel.java:445)
	... 60 more
```