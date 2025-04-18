package io.contek.invoker.binancespot.api.rest.user.spot;

import io.contek.invoker.binancespot.api.common._SpotAsset;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.spot.GetUserAssets.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

import static io.contek.invoker.commons.rest.RestMethod.POST;

@NotThreadSafe
public final class GetUserAssets extends UserRestRequest<Response> {

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

  @NotThreadSafe
  public static final class Response extends ArrayList<_SpotAsset> {}
}
