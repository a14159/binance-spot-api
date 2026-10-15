package io.contek.invoker.binancespot.api.rest.user.spot;

import io.contek.invoker.binancespot.api.common._AmendedOrder;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.spot.PutOrderAmendKeepPriority.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.Objects;

import static io.contek.invoker.commons.rest.RestMethod.PUT;

/**
 * Reduces the quantity of an open order while keeping its time priority; the price cannot change.
 * Without a new client order ID, the exchange assigns a random one to the amended order.
 */
@NotThreadSafe
public final class PutOrderAmendKeepPriority extends UserRestRequest<Response> {

  private String symbol;
  private Long orderId;
  private String origClientOrderId;
  private String newClientOrderId;
  private String newQty;

  PutOrderAmendKeepPriority(IActor actor, RestContext context) {
    super(actor, context);
  }

  public PutOrderAmendKeepPriority setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public PutOrderAmendKeepPriority setOrderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

  public PutOrderAmendKeepPriority setOrigClientOrderId(String origClientOrderId) {
    this.origClientOrderId = origClientOrderId;
    return this;
  }

  public PutOrderAmendKeepPriority setNewClientOrderId(@Nullable String newClientOrderId) {
    this.newClientOrderId = newClientOrderId;
    return this;
  }

  public PutOrderAmendKeepPriority setNewQty(String newQty) {
    this.newQty = newQty;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected RestMethod getMethod() {
    return PUT;
  }

  @Override
  protected String getEndpointPath() {
    return "/api/v3/order/amend/keepPriority";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(symbol);
    builder.add("symbol", symbol);

    if (orderId == null && origClientOrderId == null) {
      throw new IllegalArgumentException();
    }
    if (orderId != null) {
      builder.add("orderId", orderId);
    }
    if (origClientOrderId != null) {
      builder.add("origClientOrderId", origClientOrderId);
    }
    if (newClientOrderId != null) {
      builder.add("newClientOrderId", newClientOrderId);
    }

    Objects.requireNonNull(newQty);
    builder.add("newQty", newQty);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response {

    public long transactTime;
    public long executionId;
    public _AmendedOrder amendedOrder;
  }
}
