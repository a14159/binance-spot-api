package io.contek.invoker.binancespot.api.websocket.user;

import io.contek.invoker.commons.websocket.AnyWebSocketMessage;
import io.contek.invoker.commons.websocket.IWebSocketAuthenticator;
import io.contek.invoker.commons.websocket.WebSocketAuthenticationException;
import io.contek.invoker.commons.websocket.WebSocketSession;
import io.contek.invoker.security.ICredential;

import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import java.time.Clock;
import java.util.concurrent.atomic.AtomicBoolean;

@ThreadSafe
final class SpotUserWebSocketAuthenticator implements IWebSocketAuthenticator {

  private static final int REQUEST_ID = 1;
  private static final String SUBSCRIBE = "userDataStream.subscribe.signature";

  private final ICredential credential;
  private final Clock clock;

  private final AtomicBoolean pending = new AtomicBoolean();
  private final AtomicBoolean subscribed = new AtomicBoolean();

  SpotUserWebSocketAuthenticator(ICredential credential, Clock clock) {
    this.credential = credential;
    this.clock = clock;
  }

  @Override
  public void handshake(WebSocketSession session) {
    if (credential.isAnonymous()) {
      throw new WebSocketAuthenticationException("Spot User Data Stream requires an API key");
    }

    Request.Params params = new Request.Params();
    params.apiKey = credential.getApiKeyId();
    params.timestamp = clock.millis();
    params.signature =
        credential.sign("apiKey=" + params.apiKey + "&timestamp=" + params.timestamp);

    Request request = new Request();
    request.id = REQUEST_ID;
    request.method = SUBSCRIBE;
    request.params = params;

    pending.set(true);
    session.send(request);
  }

  @Override
  public boolean isPending() {
    return pending.get();
  }

  @Override
  public boolean isCompleted() {
    return subscribed.get();
  }

  @Override
  public void onMessage(AnyWebSocketMessage message, WebSocketSession session) {
    if (!(message instanceof Response response) || response.id != REQUEST_ID) {
      return;
    }

    pending.set(false);
    if (response.status != 200 || response.error != null || response.result == null) {
      String error =
          response.error == null
              ? "Unexpected subscription response status: " + response.status
              : response.error.code + ": " + response.error.msg;
      throw new WebSocketAuthenticationException(error);
    }
    subscribed.set(true);
  }

  @Override
  public void afterDisconnect() {
    pending.set(false);
    subscribed.set(false);
  }

  @NotThreadSafe
  static final class Request extends AnyWebSocketMessage {

    public int id;
    public String method;
    public Params params;

    @NotThreadSafe
    static final class Params {

      public String apiKey;
      public long timestamp;
      public String signature;
    }
  }

  @NotThreadSafe
  static final class Response extends AnyWebSocketMessage {

    public int id;
    public int status;
    public Result result;
    public Error error;

    @NotThreadSafe
    static final class Result {

      public int subscriptionId;
    }

    @NotThreadSafe
    static final class Error {

      public int code;
      public String msg;
    }
  }
}
