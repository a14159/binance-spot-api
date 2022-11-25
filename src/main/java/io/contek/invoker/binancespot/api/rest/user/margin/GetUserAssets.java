package io.contek.invoker.binancespot.api.rest.user.margin;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._SpotAsset;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.POST;

@NotThreadSafe
public final class GetUserAssets extends UserRestRequest<GetUserAssets.Response> {

  private static final ImmutableList<TypedPermitRequest> REQUIRED_QUOTA =
      ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(5));

  private String asset;

  GetUserAssets(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetUserAssets setAsset(String asset) {
    this.asset = asset;
    return this;
  }

  @Override
  protected RestMethod getMethod() {
    return POST;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return "/sapi/v3/asset/getUserAsset";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    if (asset != null)
      builder.add("asset", asset);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return REQUIRED_QUOTA;
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_SpotAsset> {}
}
