package io.contek.invoker.binancespot.api.rest.market;

import io.contek.invoker.binancespot.api.common._ExchangeInfo;
import io.contek.invoker.binancespot.api.rest.market.GetExchangeInfo.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;

@NotThreadSafe
public final class GetExchangeInfo extends MarketRestRequest<Response> {

  private static final List<TypedPermitRequest> REQUIRED_QUOTA =
      List.of(IP_REST_REQUEST_RULE.forPermits(10));

  GetExchangeInfo(IActor actor, RestContext context) {
    super(actor, context);
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/exchangeInfo";
  }

  @Override
  protected RestParams getParams() {
    return RestParams.empty();
  }

  @Override
  protected List<TypedPermitRequest> getRequiredQuotas() {
    return REQUIRED_QUOTA;
  }

  @NotThreadSafe
  public static final class Response extends _ExchangeInfo {};
}
