package io.contek.invoker.binancespot.api.websocket.user.constants;

import javax.annotation.concurrent.Immutable;

@Immutable
public final class UserEventTypeKeys {

  public static final String _listenKeyExpired = "listenKeyExpired";

  public static final String _ACCOUNT_UPDATE = "outboundAccountPosition";

  public static final String _BALANCE_UPDATE = "balanceUpdate";

  public static final String _ORDER_UPDATE = "executionReport";

  private UserEventTypeKeys() {}
}
