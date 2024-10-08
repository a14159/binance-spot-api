package io.contek.invoker.binancespot.api.rest.user.margin;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._Order;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.margin.DeleteOrder.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.DELETE;

@NotThreadSafe
public final class DeleteOrder extends UserRestRequest<Response> {

  private String symbol;
  private Long orderId;
  private String origClientOrderId;
  private String newClientOrderId;

  DeleteOrder(IActor actor, RestContext context) {
    super(actor, context);
  }

  public DeleteOrder setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public DeleteOrder setOrderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  public DeleteOrder setOrigClientOrderId(String origClientOrderId) {
    this.origClientOrderId = origClientOrderId;
    return this;
  }

  public DeleteOrder setNewClientOrderId(String newClientOrderId) {
    this.newClientOrderId = newClientOrderId;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected RestMethod getMethod() {
    return DELETE;
  }

  @Override
  protected String getEndpointPath() {
    return "/sapi/v1/margin/order";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    checkNotNull(symbol);
    builder.add("symbol", symbol);

    checkArgument(orderId != null || origClientOrderId != null);
    if (orderId != null) {
      builder.add("orderId", orderId);
    }
    if (origClientOrderId != null) {
      builder.add("origClientOrderId", origClientOrderId);
    }
    if (newClientOrderId != null) {
      builder.add("newClientOrderId", newClientOrderId);
    }

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(10));
  }

  @NotThreadSafe
  public static final class Response extends _Order {}
}
