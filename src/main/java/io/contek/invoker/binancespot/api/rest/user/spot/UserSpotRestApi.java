package io.contek.invoker.binancespot.api.rest.user.spot;

import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class UserSpotRestApi {

  private final IActor actor;
  private final RestContext context;

  public UserSpotRestApi(IActor actor, RestContext context) {
    this.actor = actor;
    this.context = context;
  }

  public GetAccount getAccount() {
    return new GetAccount(actor, context);
  }

  public GetUserAssets getUserAssets() {
    return new GetUserAssets(actor, context);
  }

  public GetTradeFee getTradeFee() {
    return new GetTradeFee(actor, context);
  }

  public GetAccountTradeList getAccountTradeList() {
    return new GetAccountTradeList(actor, context);
  }

  public PostOrder postOrder() {
    return new PostOrder(actor, context);
  }

  public DeleteOrder deleteOrder() {
    return new DeleteOrder(actor, context);
  }

  public DeleteAllOpenOrders deleteAllOpenOrders() {
    return new DeleteAllOpenOrders(actor, context);
  }

  public GetOrder getOrder() {
    return new GetOrder(actor, context);
  }

  public GetOpenOrders getOpenOrders() {
    return new GetOpenOrders(actor, context);
  }

  public GetAllOrders getAllOrders() {
    return new GetAllOrders(actor, context);
  }

  public Convert convert() {
    return new Convert(actor, context);
  }

  public PostSpotListenKey postSpotListenKey() {
    return new PostSpotListenKey(actor, context);
  }

  public PutSpotListenKey putSpotListenKey() {
    return new PutSpotListenKey(actor, context);
  }
}
