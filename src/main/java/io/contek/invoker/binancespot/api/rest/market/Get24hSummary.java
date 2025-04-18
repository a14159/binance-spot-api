package io.contek.invoker.binancespot.api.rest.market;

import io.contek.invoker.binancespot.api.common._MiniTickerSummary;
import io.contek.invoker.binancespot.api.rest.market.Get24hSummary.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Objects;


@NotThreadSafe
public final class Get24hSummary extends MarketRestRequest<Response> {

  private String symbol;

  Get24hSummary(IActor actor, RestContext context) {
    super(actor, context);
  }

  public Get24hSummary setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/ticker/24hr";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(symbol);
    builder.add("symbol", symbol);

    builder.add("type", "MINI");

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response extends _MiniTickerSummary {}
}
