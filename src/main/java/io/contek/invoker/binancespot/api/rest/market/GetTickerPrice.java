package io.contek.invoker.binancespot.api.rest.market;

import io.contek.invoker.binancespot.api.common._PriceTicker;
import io.contek.invoker.binancespot.api.rest.market.GetTickerPrice.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;


@NotThreadSafe
public final class GetTickerPrice extends MarketRestRequest<Response> {

  private String symbol;

  GetTickerPrice(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetTickerPrice setSymbol(@Nullable String symbol) {
    this.symbol = symbol;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/ticker/price";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    if (symbol != null) {
      builder.add("symbol", symbol);
    }

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response extends _PriceTicker {}
}
