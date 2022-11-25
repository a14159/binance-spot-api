package io.contek.invoker.binancespot.api.rest.user.margin;

import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class UserMarginRestApi {

  private final IActor actor;
  private final RestContext context;

  public UserMarginRestApi(IActor actor, RestContext context) {
    this.actor = actor;
    this.context = context;
  }

  public GetCrossCollateralWallet getCrossCollateralWallet() {
    return new GetCrossCollateralWallet(actor, context);
  }

  public GetMarginAccount getMarginAccount() {
    return new GetMarginAccount(actor, context);
  }

  public GetMarginAccountSummary getMarginAccountSummary() {
    return new GetMarginAccountSummary(actor, context);
  }

  public GetUserAssets getUserAssets() {
    return new GetUserAssets(actor, context);
  }

  public GetTradeFee getTradeFee() {
    return new GetTradeFee(actor, context);
  }

  public GetBorrowRate getBorrowRate() {
    return new GetBorrowRate(actor, context);
  }

  public GetAccountTradeList getAccountTradeList() {
    return new GetAccountTradeList(actor, context);
  }

  public GetOpenOrders getOpenOrders() {
    return new GetOpenOrders(actor, context);
  }

  public GetOrder getOrder() {
    return new GetOrder(actor, context);
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

  public PostMarginListenKey postMarginListenKey() {
    return new PostMarginListenKey(actor, context);
  }

  public PutMarginListenKey putMarginListenKey() {
    return new PutMarginListenKey(actor, context);
  }
}
