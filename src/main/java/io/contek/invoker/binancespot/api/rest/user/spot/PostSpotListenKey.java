package io.contek.invoker.binancespot.api.rest.user.spot;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.spot.PostSpotListenKey.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.*;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.ONE_REST_REQUEST;
import static io.contek.invoker.commons.rest.RestMediaType.FORM;
import static io.contek.invoker.commons.rest.RestMethod.POST;

@ThreadSafe
public final class PostSpotListenKey extends UserRestRequest<Response> {

  private static final String X_MBX_API_KEY = "X-MBX-APIKEY";

  private final RestContext context;

  PostSpotListenKey(IActor actor, RestContext context) {
    super(actor, context);
    this.context = context;
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
    return "/api/v3/userDataStream";
  }

  @Override
  protected RestParams getParams() {
    return RestParams.empty();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return ONE_REST_REQUEST;
  }

  // ugly hack to prevent RestRequest to add a signature to the request
  @Override
  protected RestCall createCall(ICredential credential) {
    RestMediaBody body = FORM.createBody(getParams());
    return RestCall.newBuilder()
            .setUrl(context.getBaseUrl() + getEndpointPath())
            .setMethod(getMethod())
            .setHeaders(buildHeaders(credential))
            .setBody(body)
            .build();
  }

  private ImmutableMap<String, String> buildHeaders(ICredential credential) {
    if (credential.isAnonymous()) {
      return ImmutableMap.of();
    }
    return ImmutableMap.<String, String>builder()
            .put(X_MBX_API_KEY, credential.getApiKeyId())
            .build();
  }
  // end ugly hack

  @NotThreadSafe
  public static final class Response {

    public String listenKey;
  }
}
