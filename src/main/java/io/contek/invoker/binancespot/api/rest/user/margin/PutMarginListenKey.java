package io.contek.invoker.binancespot.api.rest.user.margin;

import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.margin.PutMarginListenKey.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;
import java.util.Objects;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.ONE_REST_REQUEST;
import static io.contek.invoker.commons.rest.RestMethod.PUT;

@NotThreadSafe
public final class PutMarginListenKey extends UserRestRequest<Response> {

  private String listenKey;

  PutMarginListenKey(IActor actor, RestContext context) {
    super(actor, context);
  }

  public PutMarginListenKey setListenKey(String listenKey) {
    this.listenKey = listenKey;
    return this;
  }

  @Override
  protected RestMethod getMethod() {
    return PUT;
  }

  @Override
  protected Class<Response> getResponseType() {
    return Response.class;
  }

  @Override
  protected String getEndpointPath() {
    return " /sapi/v1/userDataStream";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(listenKey);
    builder.add("listenKey", listenKey);

    return builder.build();
  }

  @Override
  protected List<TypedPermitRequest> getRequiredQuotas() {
    return ONE_REST_REQUEST;
  }

  @NotThreadSafe
  public static final class Response {}
}
