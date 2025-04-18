package io.contek.invoker.binancespot.api.websocket.user;

import io.contek.invoker.binancespot.api.rest.user.spot.UserSpotRestApi;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.websocket.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.ThreadSafe;


@ThreadSafe
public final class SpotUserWebSocketApi extends BaseWebSocketApi {

  private final WebSocketContext context;

  public AccountUpdateChannel accountUpdateChannel;
  public BalanceUpdateChannel balanceUpdateChannel;
  public OrderUpdateChannel orderUpdateChannel;


  public SpotUserWebSocketApi(IActor actor, WebSocketContext context, UserSpotRestApi userSpotRestApi) {
    super(
        actor,
        UserWebSocketParser.getInstance(),
        IWebSocketAuthenticator.noOp(),
        new SpotUserWebSocketLiveKeeper(userSpotRestApi, actor.getClock()));
    this.context = context;
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
    SpotUserWebSocketLiveKeeper liveKeeper = (SpotUserWebSocketLiveKeeper) getLiveKeeper();
    String listenKey = liveKeeper.init();
    return WebSocketCall.fromUrl(context.getBaseUrl() + "/ws/" + listenKey);
  }

  @Override
  protected void checkErrorMessage(AnyWebSocketMessage message) throws WebSocketRuntimeException {}
}
