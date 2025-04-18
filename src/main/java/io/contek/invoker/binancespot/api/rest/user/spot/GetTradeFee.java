package io.contek.invoker.binancespot.api.rest.user.spot;

import io.contek.invoker.binancespot.api.common._TradeFees;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.binancespot.api.rest.user.spot.GetTradeFee.Response;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

import static io.contek.invoker.commons.rest.RestMethod.GET;

@NotThreadSafe
public final class GetTradeFee extends UserRestRequest<Response> {

  private String symbol;

  GetTradeFee(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetTradeFee setSymbol(String symbol) {
    this.symbol = symbol;
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
    return "/sapi/v1/asset/tradeFee";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    if (symbol != null)
      builder.add("symbol", symbol);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_TradeFees> {}
}
