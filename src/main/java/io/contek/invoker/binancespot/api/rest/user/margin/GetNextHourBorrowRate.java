package io.contek.invoker.binancespot.api.rest.user.margin;

import com.google.common.collect.ImmutableList;
import io.contek.invoker.binancespot.api.common._BorrowRate;
import io.contek.invoker.binancespot.api.common._NextHourRate;
import io.contek.invoker.binancespot.api.rest.user.UserRestRequest;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.ratelimit.TypedPermitRequest;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.rest.RestMethod;
import io.contek.invoker.commons.rest.RestParams;

import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;
import java.util.List;

import static io.contek.invoker.binancespot.api.ApiFactory.RateLimits.IP_REST_REQUEST_RULE;
import static io.contek.invoker.commons.rest.RestMethod.GET;

@NotThreadSafe
public final class GetNextHourBorrowRate extends UserRestRequest<GetNextHourBorrowRate.Response> {

  private List<String> assets;
  private boolean isIsolated = false;

  GetNextHourBorrowRate(IActor actor, RestContext context) {
    super(actor, context);
  }

  public GetNextHourBorrowRate setAssets(@Nullable List<String> assets) {
    this.assets = assets;
    return this;
  }

  public void setIsolated(boolean isolated) {
    isIsolated = isolated;
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
    return "/sapi/v1/margin/next-hourly-interest-rate";
  }

  @Override
  protected RestParams getParams() {
    RestParams.Builder builder = RestParams.newBuilder();

    if (assets != null) {
      String assetsParam = "";
      for (int i = 0; i < assets.size(); i++) {
        assetsParam += assets.get(i);
        if (i != assets.size() - 1)
          assetsParam += ",";
      }
      builder.add("assets", assetsParam);
    }

    builder.add("isIsolated", isIsolated);

    builder.add("timestamp", getMillis());

    return builder.build();
  }

  @Override
  protected ImmutableList<TypedPermitRequest> getRequiredQuotas() {
    return ImmutableList.of(IP_REST_REQUEST_RULE.forPermits(100));
  }

  @NotThreadSafe
  public static final class Response extends ArrayList<_NextHourRate> {}
}
