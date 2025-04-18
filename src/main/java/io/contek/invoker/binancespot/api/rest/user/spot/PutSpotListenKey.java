package io.contek.invoker.binancespot.api.rest.user.spot;

import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.spot.PutSpotListenKey.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Objects;

import static io.contek.invoker.commons.rest.RestMethod.PUT;

@NotThreadSafe
public final class PutSpotListenKey extends UserRestRequest<Response> {

  private String listenKey;

  PutSpotListenKey(IActor actor, RestContext context) {
    super(actor, context);
  }

  public PutSpotListenKey setListenKey(String listenKey) {
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
    return "/api/v3/userDataStream";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    Objects.requireNonNull(listenKey);
    builder.add("listenKey", listenKey);

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response {}
}
