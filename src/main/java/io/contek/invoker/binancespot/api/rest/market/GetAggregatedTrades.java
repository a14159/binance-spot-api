package io.contek.invoker.binancespot.api.rest.market;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._AggTrade;
import io.contek.invoker.binancespot.api.rest.market.GetAggregatedTrades.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.ONE_REST_REQUEST;

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
   * @param startTime
   * @return
   */
  public GetAggregatedTrades setStartTime(long startTime) {
    this.startTime = startTime;
    return this;
  }

  /**
   * Timestamp in ms to get aggregate trades until INCLUSIVE.
   * @param endTime
   * @return
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

    checkNotNull(symbol);
    builder.add("symbol", symbol);

    if (startTime != 0)
      builder.add("startTime", startTime);

    if (endTime != 0)
      builder.add("endTime", endTime);

    builder.add("limit", "1000"); // default 500, 1000 max

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {

    return ONE_REST_REQUEST;
  }

  @NotThreadSafe
  public static final class Response extends _AggTrade {}
}
