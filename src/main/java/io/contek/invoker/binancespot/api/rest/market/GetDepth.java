package io.contek.invoker.binancespot.api.rest.market;

import io.contek.invoker.binancespot.api.common._OrderBook;
import io.contek.invoker.binancespot.api.rest.market.GetDepth.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

@NotThreadSafe
public final class GetDepth extends MarketRestRequest<Response> {

  public static final SortedSet<Integer> SUPPORTED_LIMITS =
          new TreeSet<>(Set.of(5, 10, 20, 50, 100, 500, 1000, 5000));

  private String symbol;
  private Integer limit;

  GetDepth(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetDepth setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public GetDepth setLimit(@Nullable Integer limit) {
    this.limit = limit;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/depth";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(symbol);
    builder.add("symbol", symbol);

    if (limit != null) {
      if (!SUPPORTED_LIMITS.contains(limit)) {
        throw new IllegalArgumentException();
      }
      builder.add("limit", limit);
    }

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response extends _OrderBook {}
}
