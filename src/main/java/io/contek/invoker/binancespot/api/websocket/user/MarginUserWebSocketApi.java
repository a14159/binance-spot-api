package io.contek.invoker.binancespot.api.websocket.user;

import io.contek.invoker.binancespot.api.rest.user.margin.UserMarginRestApi;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.websocket.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.ThreadSafe;


@ThreadSafe
public final class MarginUserWebSocketApi extends BaseWebSocketApi {

  private final WebSocketContext context;

  public AccountUpdateChannel accountUpdateChannel;
  public BalanceUpdateChannel balanceUpdateChannel;
  public OrderUpdateChannel orderUpdateChannel;


  public MarginUserWebSocketApi(IActor actor, WebSocketContext context, UserMarginRestApi userMarginRestApi) {
    super(
        actor,
        UserWebSocketParser.getInstance(),
        IWebSocketAuthenticator.noOp(),
        new MarginUserWebSocketLiveKeeper(userMarginRestApi, actor.getClock()));
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
    MarginUserWebSocketLiveKeeper liveKeeper = (MarginUserWebSocketLiveKeeper) getLiveKeeper();
    String listenKey = liveKeeper.init();
    return WebSocketCall.fromUrl(context.getBaseUrl() + "/ws/" + listenKey);
  }

  @Override
  protected void checkErrorMessage(AnyWebSocketMessage message) throws WebSocketRuntimeException {}
}
