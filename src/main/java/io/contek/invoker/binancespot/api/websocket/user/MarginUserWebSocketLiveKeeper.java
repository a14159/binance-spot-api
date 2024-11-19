package io.contek.invoker.binancespot.api.websocket.user;

import io.contek.invoker.binancespot.api.rest.user.margin.PostMarginListenKey;
import io.contek.invoker.binancespot.api.rest.user.margin.UserMarginRestApi;
import io.contek.invoker.commons.actor.http.AnyHttpException;
import io.contek.invoker.commons.websocket.AnyWebSocketMessage;
import io.contek.invoker.commons.websocket.IWebSocketLiveKeeper;
import io.contek.invoker.commons.websocket.WebSocketSession;
import io.contek.invoker.commons.websocket.WebSocketSessionInactiveException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.concurrent.Immutable;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;


@Immutable
final class MarginUserWebSocketLiveKeeper implements IWebSocketLiveKeeper {

  private static final Logger log = LogManager.getLogger(MarginUserWebSocketLiveKeeper.class);

  private static final Duration REFRESH_PERIOD = Duration.ofMinutes(30);

  private final UserMarginRestApi userMarginRestApi;
  private final Clock clock;

  private final AtomicReference<State> stateHolder = new AtomicReference<>(null);

  MarginUserWebSocketLiveKeeper(UserMarginRestApi userMarginRestApi, Clock clock) {
    this.userMarginRestApi = userMarginRestApi;
    this.clock = clock;
  }

  @Override
  public void onHeartbeat(WebSocketSession session) throws WebSocketSessionInactiveException {
    synchronized (stateHolder) {
      State state = stateHolder.get();
      if (state == null) {
        return;
      }

      Instant timestamp = clock.instant();
      Instant expire = state.getLastRefreshTimestamp().plus(REFRESH_PERIOD);
      if (timestamp.isBefore(expire)) {
        return;
      }

      try {
        userMarginRestApi.putMarginListenKey().setListenKey(state.getListenKey()).submit();
        stateHolder.set(new State(state.getListenKey(), timestamp));
      } catch (AnyHttpException e) {
        log.warn("Failed to refresh listen key: {} {}", e.getClass().getSimpleName(), e.getMessage());
      }
    }
  }

  @Override
  public void onMessage(AnyWebSocketMessage message, WebSocketSession session) {}

  @Override
  public void afterDisconnect() {
    synchronized (stateHolder) {
      stateHolder.set(null);
    }
  }

  String init() {
    synchronized (stateHolder) {
      Instant timestamp = clock.instant();
      PostMarginListenKey.Response newListenKey = userMarginRestApi.postMarginListenKey().submit();
      String listenKey = newListenKey.listenKey;
      stateHolder.set(new State(listenKey, timestamp));
      return listenKey;
    }
  }

  @Immutable
  private static final class State {

    private final String listenKey;
    private final Instant lastRefreshTimestamp;

    private State(String listenKey, Instant lastRefreshTimestamp) {
      this.listenKey = listenKey;
      this.lastRefreshTimestamp = lastRefreshTimestamp;
    }

    private String getListenKey() {
      return listenKey;
    }

    private Instant getLastRefreshTimestamp() {
      return lastRefreshTimestamp;
    }
  }
}
