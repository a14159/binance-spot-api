package io.contek.invoker.binancespot.api.rest.user;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._BookTicker;
import io.contek.invoker.binancespot.api.common._BorrowRate;
import io.contek.invoker.binancespot.api.rest.user.GetBorrowRate.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.ONE_REST_REQUEST;
import static io.contek.invoker.commons.rest.RestMethod.GET;

@NotThreadSafe
public final class GetBorrowRate extends UserRestRequest<Response> {

  private static final ImmutableList<TypedPermitRequest> ALL_SYMBOLS_REQUIRED_QUOTA =
      ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(5));

  private String symbol;

  GetBorrowRate(IActor actor, RestContext context) {
    super(actor, context);
  }

  @Override
  protected RestMethod getMethod() {
    return GET;
  }

  public GetBorrowRate setSymbol(@Nullable String symbol) {
    this.symbol = symbol;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/sapi/v1/margin/crossMarginData";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    if (symbol != null) {
      builder.add("symbol", symbol);
    }

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    if (symbol != null) {
      return ONE_REST_REQUEST;
    }
    return ALL_SYMBOLS_REQUIRED_QUOTA;
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_BorrowRate> {}
}
