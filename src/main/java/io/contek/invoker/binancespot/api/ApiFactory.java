package io.contek.invoker.binancespot.api;

import io.contek.invoker.binancespot.api.rest.market.MarketRestApi;
import io.contek.invoker.binancespot.api.rest.user.margin.UserMarginRestApi;
import io.contek.invoker.binancespot.api.rest.user.spot.UserSpotRestApi;
import io.contek.invoker.binancespot.api.websocket.market.IMarketWebSocketApi;
import io.contek.invoker.binancespot.api.websocket.market.combined.MarketCombinedWebSocketApi;
import io.contek.invoker.binancespot.api.websocket.market.direct.MarketDirectWebSocketApi;
import io.contek.invoker.binancespot.api.websocket.user.MarginUserWebSocketApi;
import io.contek.invoker.binancespot.api.websocket.user.SpotUserWebSocketApi;
import io.contek.invoker.commons.ApiContext;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.actor.IActorFactory;
import io.contek.invoker.commons.actor.SimpleActorFactory;
import io.contek.invoker.commons.actor.http.SimpleHttpClientFactory;
import io.contek.invoker.commons.rest.RestContext;
import io.contek.invoker.commons.websocket.WebSocketContext;
import io.contek.invoker.security.ApiKey;
import io.contek.invoker.security.SimpleCredentialFactory;

import javax.annotation.concurrent.ThreadSafe;

import static io.contek.invoker.security.SecretKeyAlgorithm.HMAC_SHA256;
import static is.fm.util.BaseEncoding.base16;

@ThreadSafe
public final class ApiFactory {

  public static final ApiContext MAIN_NET_CONTEXT =
      ApiContext.newBuilder()
          .setRestContext(RestContext.forBaseUrl("https://api.binance.com"))
          .setWebSocketContext(WebSocketContext.forBaseUrl("wss://stream.binance.com"))
          .build();

  public static final ApiContext TEST_NET_CONTEXT =
      ApiContext.newBuilder()
          .setRestContext(RestContext.forBaseUrl("https://testnet.binance.vision"))
          .setWebSocketContext(WebSocketContext.forBaseUrl("wss://testnet.binance.vision"))
          .build();

  private final ApiContext context;
  private final IActorFactory actorFactory;

  private ApiFactory(ApiContext context, IActorFactory actorFactory) {
    this.context = context;
    this.actorFactory = actorFactory;
  }

  public static ApiFactory getMainNet() {
    return fromContext(MAIN_NET_CONTEXT);
  }

  public static ApiFactory getTestNet() {
    return fromContext(TEST_NET_CONTEXT);
  }

  public static ApiFactory fromContext(ApiContext context) {
    return new ApiFactory(context, createActorFactory());
  }

  public SelectingRestApi rest() {
    return new SelectingRestApi();
  }

  public SelectingWebSocketApi ws() {
    return new SelectingWebSocketApi();
  }

  private static SimpleActorFactory createActorFactory() {
    return SimpleActorFactory.newBuilder()
        .setCredentialFactory(createCredentialFactory())
        .setHttpClientFactory(SimpleHttpClientFactory.getInstance())
        .build();
  }

  private static SimpleCredentialFactory createCredentialFactory() {
    return SimpleCredentialFactory.newBuilder()
        .setAlgorithm(HMAC_SHA256)
        .setEncoding(base16().lowerCase())
        .build();
  }

  @ThreadSafe
  public final class SelectingRestApi {

    private SelectingRestApi() {}

    public MarketRestApi market() {
      RestContext restContext = context.getRestContext();
      IActor actor = actorFactory.create(null, restContext);
      return new MarketRestApi(actor, restContext);
    }

    public UserSpotRestApi userSpot(ApiKey apiKey) {
      RestContext restContext = context.getRestContext();
      IActor actor = actorFactory.create(apiKey, restContext);
      return new UserSpotRestApi(actor, restContext);
    }

    public UserMarginRestApi userMargin(ApiKey apiKey) {
      RestContext restContext = context.getRestContext();
      IActor actor = actorFactory.create(apiKey, restContext);
      return new UserMarginRestApi(actor, restContext);
    }
  }

  @ThreadSafe
  public final class SelectingWebSocketApi {

    private SelectingWebSocketApi() {}

    public IMarketWebSocketApi market(boolean direct) {
      WebSocketContext wsContext = context.getWebSocketContext();
      IActor actor = actorFactory.create(null, wsContext);
      return direct
          ? new MarketDirectWebSocketApi(actor, wsContext)
          : new MarketCombinedWebSocketApi(actor, wsContext);
    }

    public SpotUserWebSocketApi userSpot(ApiKey apiKey) {
      WebSocketContext wsContext = context.getWebSocketContext();
      IActor actor = actorFactory.create(apiKey, wsContext);
      RestContext restContext = context.getRestContext();
      return new SpotUserWebSocketApi(
          actor, context.getWebSocketContext(), new UserSpotRestApi(actor, restContext));
    }

    public MarginUserWebSocketApi userMargin(ApiKey apiKey) {
      WebSocketContext wsContext = context.getWebSocketContext();
      IActor actor = actorFactory.create(apiKey, wsContext);
      RestContext restContext = context.getRestContext();
      return new MarginUserWebSocketApi(
              actor, context.getWebSocketContext(), new UserMarginRestApi(actor, restContext));
    }
  }
}
