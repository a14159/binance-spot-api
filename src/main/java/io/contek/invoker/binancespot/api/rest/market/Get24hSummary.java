package io.contek.invoker.binancespot.api.rest.market;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._MiniTickerSummary;
import io.contek.invoker.binancespot.api.rest.market.Get24hSummary.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.ONE_REST_REQUEST;

@NotThreadSafe
public final class Get24hSummary extends MarketRestRequest<Response> {

  private static final ImmutableList<TypedPermitRequest> REQUIRED_MAX_QUOTA =
      ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(40));

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

    if (symbol != null)
      builder.add("symbol", symbol);

    builder.add("type", "MINI");

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    if (symbol != null)
      return ONE_REST_REQUEST;

    return REQUIRED_MAX_QUOTA;
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_MiniTickerSummary> {}
}
