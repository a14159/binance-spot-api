package io.contek.invoker.binancespot.api.rest.user.spot;

import io.contek.invoker.binancespot.api.common._Order;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.spot.GetAllOrders.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.Objects;

import static io.contek.invoker.commons.rest.RestMethod.GET;

@NotThreadSafe
public final class GetAllOrders extends UserRestRequest<Response> {

  public static final int MAX_LIMIT = 1000;

  private String symbol;
  private Long orderId;
  private Long startTime;
  private Long endTime;
  private Integer limit;

  GetAllOrders(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetAllOrders setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public GetAllOrders setOrderId(@Nullable Long orderId) {
    this.orderId = orderId;
    return this;
  }

  public GetAllOrders setStartTime(@Nullable Long startTime) {
    this.startTime = startTime;
    return this;
  }

  public GetAllOrders setEndTime(@Nullable Long endTime) {
    this.endTime = endTime;
    return this;
  }

  public GetAllOrders setLimit(@Nullable Integer limit) {
    this.limit = limit;
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
    return "/api/v3/allOrders";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(symbol);
    builder.add("symbol", symbol);

    if (orderId != null) {
      builder.add("orderId", orderId);
    }

    if (startTime != null) {
      builder.add("startTime", startTime);
    }

    if (endTime != null) {
      builder.add("endTime", endTime);
    }

    if (limit != null) {
      if (limit > MAX_LIMIT) {
        throw new IllegalArgumentException();
      }
      builder.add("limit", limit);
    }

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_Order> {}
}
