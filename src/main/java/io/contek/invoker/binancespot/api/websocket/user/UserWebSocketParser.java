package io.contek.invoker.binancespot.api.websocket.user;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;
import io.contek.invoker.commons.websocket.IWebSocketComponent;
import io.contek.invoker.commons.websocket.WebSocketTextMessageParser;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class UserWebSocketParser extends WebSocketTextMessageParser {

  private final Gson gson = new Gson();

  static UserWebSocketParser getInstance() {
    return UserWebSocketParser.InstanceHolder.INSTANCE;
  }

  @Override
  public void register(IWebSocketComponent component) {}

  @Override
  public WebSocketEventData fromText(String text) {
    JsonElement json = gson.fromJson(text, JsonElement.class);
    if (!json.isJsonObject()) {
      throw new IllegalArgumentException(text);
    }

    JsonObject obj = json.getAsJsonObject();
    if (obj.has("e")) {
      return toUserData(obj);
    }

    throw new IllegalStateException(text);
  }

  private WebSocketEventData toUserData(JsonObject obj) {
    String eventType = obj.get("e").getAsString();
    return switch (eventType) {
      case UserEventTypeKeys._ACCOUNT_UPDATE -> gson.fromJson(obj, AccountUpdateChannel.Data.class);
      case UserEventTypeKeys._BALANCE_UPDATE -> gson.fromJson(obj, BalanceUpdateChannel.Data.class);
      case UserEventTypeKeys._ORDER_UPDATE -> gson.fromJson(obj, OrderUpdateChannel.Data.class);
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
