package io.contek.invoker.binancespot.api.websocket.market.combined;

import io.contek.invoker.commons.websocket.AnyWebSocketMessage;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public final class WebSocketCommandConfirmation extends AnyWebSocketMessage {

  public int id;
}
