package io.contek.invoker.binancespot.api.websocket.user;

import io.contek.invoker.binancespot.api.rest.user.PostMarginListenKey;
import io.contek.invoker.binancespot.api.rest.user.PostSpotListenKey;
import io.contek.invoker.binancespot.api.rest.user.UserRestApi;
import io.contek.invoker.commons.actor.http.HttpConnectionException;
import io.contek.invoker.commons.actor.http.HttpInterruptedException;
import io.contek.invoker.commons.websocket.AnyWebSocketMessage;
import io.contek.invoker.commons.websocket.IWebSocketLiveKeeper;
import io.contek.invoker.commons.websocket.WebSocketSession;
import io.contek.invoker.commons.websocket.WebSocketSessionInactiveException;
import org.slf4j.Logger;

import javax.annotation.concurrent.Immutable;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicReference;

import static org.slf4j.LoggerFactory.getLogger;

@Immutable
final class MarginUserWebSocketLiveKeeper implements IWebSocketLiveKeeper {

  private static final Logger log = getLogger(MarginUserWebSocketLiveKeeper.class);

  private static final Duration REFRESH_PERIOD = Duration.ofMinutes(10);

  private final UserRestApi userRestApi;
  private final Clock clock;

  private final AtomicReference<State> stateHolder = new AtomicReference<>(null);

  MarginUserWebSocketLiveKeeper(UserRestApi userRestApi, Clock clock) {
    this.userRestApi = userRestApi;
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
        userRestApi.putMarginListenKey().setListenKey(state.getListenKey()).submit();
        stateHolder.set(new State(state.getListenKey(), timestamp));
      } catch (HttpConnectionException | HttpInterruptedException e) {
        log.warn("Failed to refresh listen key.", e);
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
      PostMarginListenKey.Response newListenKey = userRestApi.postMarginListenKey().submit();
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
