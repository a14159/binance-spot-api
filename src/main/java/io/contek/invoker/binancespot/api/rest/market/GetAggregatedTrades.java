package io.contek.invoker.binancespot.api.rest.market;

import io.contek.invoker.binancespot.api.common._AggTrade;
import io.contek.invoker.binancespot.api.rest.market.GetAggregatedTrades.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.Objects;


@NotThreadSafe
public final class GetAggregatedTrades extends MarketRestRequest<Response> {


  private String symbol;

  private long startTime;
  private long endTime;

  GetAggregatedTrades(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetAggregatedTrades setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * Timestamp in ms to get aggregate trades from INCLUSIVE.
   */
  public GetAggregatedTrades setStartTime(long startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Timestamp in ms to get aggregate trades until INCLUSIVE.
   */
  public GetAggregatedTrades setEndTime(long endTime) {
    this.endTime = endTime;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/aggTrades";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(symbol);
    builder.add("symbol", symbol);

    if (startTime != 0)
      builder.add("startTime", startTime);

    if (endTime != 0)
      builder.add("endTime", endTime);

    builder.add("limit", "1000"); // default 500, 1000 max

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_AggTrade> {}
}
