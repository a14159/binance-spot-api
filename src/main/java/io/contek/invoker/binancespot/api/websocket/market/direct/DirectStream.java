package io.contek.invoker.binancespot.api.websocket.market.direct;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.websocket.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
abstract class DirectStream<Data extends WebSocketEventData> extends BaseWebSocketApi {

  private final MarketWebSocketDirectChannelId<Data> id;
  private final WebSocketContext context;
  private final MarketWebSocketDirectChannel<Data> channel;

  private final AtomicBoolean attached = new AtomicBoolean(false);

  DirectStream(MarketWebSocketDirectChannelId<Data> id, IActor actor, WebSocketContext context) {
    super(
        actor,
        new DirectStreamMessageParser<>(id.getType()),
        IWebSocketAuthenticator.noOp(),
        IWebSocketLiveKeeper.noOp());
    this.id = id;
    this.context = context;
    channel = new MarketWebSocketDirectChannel<>(id);
  }

  @Override
  protected WebSocketCall createCall(ICredential credential) {
    return WebSocketCall.fromUrl(context.getBaseUrl() + "/ws/" + id.getValue());
  }

  @Override
  protected void checkErrorMessage(AnyWebSocketMessage message) throws WebSocketRuntimeException {}

  MarketWebSocketDirectChannel<Data> getChannel() {
    if (!attached.get()) {
      attach(channel);
    }
    return channel;
  }
}
