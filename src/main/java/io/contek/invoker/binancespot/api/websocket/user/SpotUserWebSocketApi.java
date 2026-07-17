package io.contek.invoker.binancespot.api.websocket.user;

import io.contek.invoker.binancespot.api.rest.user.spot.UserSpotRestApi;
import io.contek.invoker.binancespot.api.websocket.common.ServerShutdownEvent;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.websocket.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.ThreadSafe;


@ThreadSafe
public final class SpotUserWebSocketApi extends BaseWebSocketApi {

  private static final WebSocketContext MAIN_NET_CONTEXT =
      WebSocketContext.forBaseUrl("wss://ws-api.binance.com:443/ws-api/v3");
  private static final WebSocketContext TEST_NET_CONTEXT =
      WebSocketContext.forBaseUrl("wss://ws-api.testnet.binance.vision/ws-api/v3");

  private final WebSocketContext context;

  public AccountUpdateChannel accountUpdateChannel;
  public BalanceUpdateChannel balanceUpdateChannel;
  public OrderUpdateChannel orderUpdateChannel;


  public SpotUserWebSocketApi(IActor actor, WebSocketContext context) {
    super(
        actor,
        UserWebSocketParser.getInstance(),
        new SpotUserWebSocketAuthenticator(actor.getCredential(), actor.getClock()),
        IWebSocketLiveKeeper.noOp());
    this.context = toWebSocketApiContext(context);
  }

  @Deprecated
  public SpotUserWebSocketApi(
      IActor actor, WebSocketContext context, UserSpotRestApi ignored) {
    this(actor, context);
  }

  public AccountUpdateChannel getAccountUpdateChannel() {
    if (accountUpdateChannel == null) {
      accountUpdateChannel = new AccountUpdateChannel();
      attach(accountUpdateChannel);
    }
    return accountUpdateChannel;
  }

  public BalanceUpdateChannel getBalanceUpdateChannel() {
    if (balanceUpdateChannel == null) {
      balanceUpdateChannel = new BalanceUpdateChannel();
      attach(balanceUpdateChannel);
    }
    return balanceUpdateChannel;
  }

  public OrderUpdateChannel getOrderUpdateChannel() {
    if (orderUpdateChannel == null) {
      orderUpdateChannel = new OrderUpdateChannel();
      attach(orderUpdateChannel);
    }
    return orderUpdateChannel;
  }

  @Override
  protected WebSocketCall createCall(ICredential credential) {
    return WebSocketCall.fromUrl(context.getBaseUrl());
  }

  @Override
  protected void checkErrorMessage(AnyWebSocketMessage message) throws WebSocketRuntimeException {
    if (message instanceof ServerShutdownEvent) {
      throw new WebSocketServerRestartException();
    }
    if (message instanceof UserControlEvent event
        && UserEventTypeKeys._EVENT_STREAM_TERMINATED.equals(event.e)) {
      throw new WebSocketSessionExpiredException();
    }
  }

  private static WebSocketContext toWebSocketApiContext(WebSocketContext context) {
    String baseUrl = context.getBaseUrl();
    if (baseUrl.startsWith("wss://stream.binance.com")) {
      return MAIN_NET_CONTEXT;
    }
    if (baseUrl.equals("wss://testnet.binance.vision")
        || baseUrl.equals("wss://stream.testnet.binance.vision")) {
      return TEST_NET_CONTEXT;
    }
    return context;
  }
}
