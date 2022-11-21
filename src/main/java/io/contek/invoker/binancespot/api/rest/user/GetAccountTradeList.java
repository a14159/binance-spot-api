package io.contek.invoker.binancespot.api.rest.user;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._Trade;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.GET;

@NotThreadSafe
public final class GetAccountTradeList extends UserRestRequest<GetAccountTradeList.Response> {

  private static final ImmutableList<TypedPermitRequest> REQUIRED_QUOTA =
      ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(10));

  private String symbol;
  private long orderId;
  private long startTime;
  private long endTime;

  GetAccountTradeList(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetAccountTradeList setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public GetAccountTradeList setOrderId(long orderId) {
    this.orderId = orderId;
    return this;
  }

  public GetAccountTradeList setStartTime(long startTime) {
    this.startTime = startTime;
    return this;
  }

  public GetAccountTradeList setEndTime(long endTime) {
    this.endTime = endTime;
    return this;
  }

  @Override
  protected RestMethod getMethod() {
    return GET;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/myTrades";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    checkNotNull(symbol);
    builder.add("symbol", symbol);

    checkArgument(orderId != 0 || (startTime != 0 && endTime != 0));
    if (orderId != 0)
      builder.add("orderId", orderId);
    if (startTime != 0)
      builder.add("startTime", startTime);
    if (endTime != 0)
      builder.add("endTime", endTime);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return REQUIRED_QUOTA;
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_Trade> {}
}
