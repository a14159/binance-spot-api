package io.contek.invoker.binancespot.api.rest.user;

import io.contek.invoker.binancespot.api.rest.RestRequest;
import io.contek.invoker.commons.actor.IActor;
import io.contek.invoker.commons.rest.RestContext;

import javax.annotation.concurrent.NotThreadSafe;
import java.time.Clock;


@NotThreadSafe
public abstract class UserRestRequest<T> extends RestRequest<T> {

  private final Clock clock;

  public UserRestRequest(IActor actor, RestContext context) {
    super(actor, context);
    clock = actor.getClock();
    if (actor.getCredential().isAnonymous()) {
      throw new IllegalArgumentException();
    }
  }

  public long getMillis() {
    return clock.millis();
  }
}
