package io.contek.invoker.binancespot.api.rest.user.margin;

import io.contek.invoker.binancespot.api.common._Trade;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.GET;

@NotThreadSafe
public final class GetAccountTradeList extends UserRestRequest<GetAccountTradeList.Response> {

  private static final List<TypedPermitRequest> REQUIRED_QUOTA =
      List.of(IP_REST_REQUEST_RULE.forPermits(10));

  private String symbol;
  private String orderId;
  private long startTime;
  private long endTime;

  GetAccountTradeList(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetAccountTradeList setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public GetAccountTradeList setOrderId(String orderId) {
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
    return " /sapi/v1/margin/myTrades";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(symbol);
    builder.add("symbol", symbol);

    if (orderId == null && (startTime == 0 || endTime == 0)) {
      throw new IllegalArgumentException();
    }
    if (orderId != null)
      builder.add("orderId", orderId);
    if (startTime != 0)
      builder.add("startTime", startTime);
    if (endTime != 0)
      builder.add("endTime", endTime);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected List<TypedPermitRequest> getRequiredQuotas() {
    return REQUIRED_QUOTA;
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_Trade> {}
}
