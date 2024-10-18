package io.contek.invoker.binancespot.api.websocket.user;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;
import io.contek.invoker.commons.websocket.IWebSocketComponent;
import io.contek.invoker.commons.websocket.WebSocketTextMessageParser;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class UserWebSocketParser extends WebSocketTextMessageParser {

  static UserWebSocketParser getInstance() {
    return UserWebSocketParser.InstanceHolder.INSTANCE;
  }

  @Override
  public void register(IWebSocketComponent component) {}

  @Override
  public WebSocketEventData fromText(String text) {
    JSONObject json = JSON.parseObject(text);
    if (json.containsKey("e")) {
      return toUserData(json);
    }

    throw new IllegalStateException(text);
  }

  private WebSocketEventData toUserData(JSONObject obj) {
    String eventType = obj.get("e").toString();
    return switch (eventType) {
      case UserEventTypeKeys._ACCOUNT_UPDATE -> obj.toJavaObject(AccountUpdateChannel.Data.class);
      case UserEventTypeKeys._BALANCE_UPDATE -> obj.toJavaObject(BalanceUpdateChannel.Data.class);
      case UserEventTypeKeys._ORDER_UPDATE -> obj.toJavaObject(OrderUpdateChannel.Data.class);
      default -> throw new IllegalStateException("Unrecognized event type: " + eventType);
    };
  }

  private UserWebSocketParser() {}

  @Immutable
  private static class InstanceHolder {

    private static final UserWebSocketParser INSTANCE = new UserWebSocketParser();

    private InstanceHolder() {}
  }
}
