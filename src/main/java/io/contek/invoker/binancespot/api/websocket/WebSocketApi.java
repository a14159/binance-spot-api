package io.contek.invoker.binancespot.api.websocket;

import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.websocket.*;

import javax.annotation.concurrent.ThreadSafe;
import java.util.List;

@ThreadSafe
public abstract class WebSocketApi extends BaseWebSocketApi {

  protected WebSocketApi(IActor actor, IWebSocketMessageParser parser) {
    super(actor, parser, IWebSocketAuthenticator.noOp(), IWebSocketLiveKeeper.noOp());
  }

  @Override
  protected final List<TypedPermitRequest> getRequiredQuotas() {
    return List.of();
  }

  @Override
  protected final void checkErrorMessage(AnyWebSocketMessage message)
      throws WebSocketRuntimeException {}
}
