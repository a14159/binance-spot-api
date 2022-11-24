package io.contek.invoker.binancespot.api.websocket.user;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.rest.user.UserRestApi;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.websocket.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.ThreadSafe;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.ONE_WEB_SOCKET_CONNECTION;

@ThreadSafe
public final class MarginUserWebSocketApi extends BaseWebSocketApi {

  private final WebSocketContext context;

  public AccountUpdateChannel accountUpdateChannel;
  public BalanceUpdateChannel balanceUpdateChannel;
  public OrderUpdateChannel orderUpdateChannel;


  public MarginUserWebSocketApi(IActor actor, WebSocketContext context, UserRestApi userRestApi) {
    super(
        actor,
        UserWebSocketParser.getInstance(),
        IWebSocketAuthenticator.noOp(),
        new MarginUserWebSocketLiveKeeper(userRestApi, actor.getClock()));
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
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return ONE_WEB_SOCKET_CONNECTION;
  }

  @Override
  protected void checkErrorMessage(AnyWebSocketMessage message) throws WebSocketRuntimeException {}
}
