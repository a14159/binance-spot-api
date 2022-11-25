package io.contek.invoker.binancespot.api.rest.user.margin;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._OrderAck;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.margin.PostOrder.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.API_KEY_REST_ORDER_RULE;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.POST;

@NotThreadSafe
public final class PostOrder extends UserRestRequest<Response> {

  private String symbol;
  private String side;
  private String type;
  private String timeInForce;
  private BigDecimal quantity;
  private BigDecimal quoteOrderQty;
  private BigDecimal price;
  private String newClientOrderId;
  private BigDecimal stopPrice;
  private BigDecimal icebergQty;

  PostOrder(IActor actor, RestContext context) {
    super(actor, context);
  }

  public PostOrder setSymbol(String symbol) {
    this.symbol = symbol;
    return this;
  }

  public PostOrder setSide(String side) {
    this.side = side;
    return this;
  }

  public PostOrder setType(String type) {
    this.type = type;
    return this;
  }

  public PostOrder setTimeInForce(@Nullable String timeInForce) {
    this.timeInForce = timeInForce;
    return this;
  }

  public PostOrder setQuantity(@Nullable BigDecimal quantity) {
    this.quantity = quantity;
    return this;
  }

  public PostOrder setQuoteOrderQty(@Nullable BigDecimal quoteOrderQty) {
    this.quoteOrderQty = quoteOrderQty;
    return this;
  }

  public PostOrder setPrice(@Nullable BigDecimal price) {
    this.price = price;
    return this;
  }

  public PostOrder setNewClientOrderId(@Nullable String newClientOrderId) {
    this.newClientOrderId = newClientOrderId;
    return this;
  }

  public PostOrder setStopPrice(@Nullable BigDecimal stopPrice) {
    this.stopPrice = stopPrice;
    return this;
  }

  public PostOrder setIcebergQty(@Nullable BigDecimal icebergQty) {
    this.icebergQty = icebergQty;
    return this;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected RestMethod getMethod() {
    return POST;
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

    checkNotNull(side);
    builder.add("side", side);

    checkNotNull(type);
    builder.add("type", type);

    if (quantity != null) {
      builder.add("quantity", quantity.toPlainString());
    }

    if (quoteOrderQty != null) {
      builder.add("quoteOrderQty", quoteOrderQty.toPlainString());
    }

    if (price != null) {
      builder.add("price", price.toPlainString());
    }

    if (timeInForce != null) {
      builder.add("timeInForce", timeInForce);
    }

    if (newClientOrderId != null) {
      builder.add("newClientOrderId", newClientOrderId);
    }

    if (stopPrice != null) {
      builder.add("stopPrice", stopPrice.toPlainString());
    }

    if (icebergQty != null) {
      builder.add("icebergQty", icebergQty.toPlainString());
    }

    builder.add("newOrderRespType", "ACK");

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(6), API_KEY_REST_ORDER_RULE.forPermits(6));
  }

  @NotThreadSafe
  public static final class Response extends _OrderAck {}
}
