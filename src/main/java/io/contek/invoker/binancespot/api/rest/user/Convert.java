package io.contek.invoker.binancespot.api.rest.user;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._ConvertResponse;
import io.contek.invoker.binancespot.api.common._Order;
import io.contek.invoker.binancespot.api.rest.user.Convert.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.POST;

@NotThreadSafe
public final class Convert extends UserRestRequest<Response> {

  private String clientTranId;
  private String asset;
  private BigDecimal amount;
  private String targetAsset;

  Convert(IActor actor, RestContext context) {
    super(actor, context);
  }

  public Convert setAsset(String asset) {
    this.asset = asset;
    return this;
  }

  public Convert setClientTranId(String clientId) {
    this.clientTranId = clientId;
    return this;
  }

  public Convert setAmount(BigDecimal amount) {
    this.amount = amount;
    return this;
  }

  public Convert setTargetAsset(String targetAsset) {
    this.targetAsset = targetAsset;
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
    return "/sapi/v1/asset/convert-transfer";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    checkNotNull(asset);
    builder.add("symbol", asset);

    checkNotNull(clientTranId);
    builder.add("clientTranId", clientTranId);

    checkNotNull(amount);
    builder.add("amount", amount.toPlainString());

    checkNotNull(targetAsset);
    builder.add("targetAsset", targetAsset);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(5));
  }

  @NotThreadSafe
  public static final class Response extends _ConvertResponse {}
}
