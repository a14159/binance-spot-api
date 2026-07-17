package io.contek.invoker.binancespot.api.websocket.user;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.util.Fnv;
import io.contek.invoker.binancespot.api.websocket.common.ServerShutdownEvent;
import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;
import io.contek.invoker.commons.websocket.AnyWebSocketMessage;
import io.contek.invoker.commons.websocket.IWebSocketComponent;
import io.contek.invoker.commons.websocket.WebSocketTextMessageParser;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public final class UserWebSocketParser extends WebSocketTextMessageParser {

  private static final long EVENT_HASH = Fnv.hashCode64("event");

  static UserWebSocketParser getInstance() {
    return UserWebSocketParser.InstanceHolder.INSTANCE;
  }

  @Override
  public void register(IWebSocketComponent component) {}

  @Override
  public AnyWebSocketMessage fromText(String text) {
    Class<? extends WebSocketEventData> eventType = getEventType(text);
    if (eventType == null) {
      return JSON.parseObject(text, SpotUserWebSocketAuthenticator.Response.class);
    }
    return findFieldValue(text, "\"event\"") < 0
        ? JSON.parseObject(text, eventType)
        : readWrappedEvent(text, eventType);
  }

  private WebSocketEventData readWrappedEvent(
      String text, Class<? extends WebSocketEventData> eventType) {
    try (JSONReader reader = JSONReader.of(text)) {
      reader.nextIfObjectStart();
      while (!reader.nextIfObjectEnd()) {
        if (reader.readFieldNameHashCode() == EVENT_HASH) {
          return reader.read(eventType);
        }
        reader.skipValue();
      }
    }
    throw new IllegalStateException(text);
  }

  private Class<? extends WebSocketEventData> getEventType(String text) {
    int valueStart = findFieldValue(text, "\"e\"");
    if (valueStart < 0 || valueStart == text.length() || text.charAt(valueStart) != '"') {
      return null;
    }
    valueStart++;
    if (matches(text, valueStart, UserEventTypeKeys._ACCOUNT_UPDATE)) {
      return AccountUpdateChannel.Data.class;
    }
    if (matches(text, valueStart, UserEventTypeKeys._BALANCE_UPDATE)) {
      return BalanceUpdateChannel.Data.class;
    }
    if (matches(text, valueStart, UserEventTypeKeys._ORDER_UPDATE)) {
      return OrderUpdateChannel.Data.class;
    }
    if (matches(text, valueStart, "serverShutdown")) {
      return ServerShutdownEvent.class;
    }
    return UserControlEvent.class;
  }

  private static int findFieldValue(String text, String field) {
    int index = 0;
    while ((index = text.indexOf(field, index)) >= 0) {
      int valueStart = index + field.length();
      while (valueStart < text.length() && Character.isWhitespace(text.charAt(valueStart))) {
        valueStart++;
      }
      if (valueStart < text.length() && text.charAt(valueStart) == ':') {
        do {
          valueStart++;
        } while (valueStart < text.length() && Character.isWhitespace(text.charAt(valueStart)));
        return valueStart;
      }
      index += field.length();
    }
    return -1;
  }

  private static boolean matches(String text, int offset, String value) {
    int end = offset + value.length();
    return end < text.length()
        && text.charAt(end) == '"'
        && text.regionMatches(offset, value, 0, value.length());
  }

  private UserWebSocketParser() {}

  @Immutable
  private static class InstanceHolder {

    private static final UserWebSocketParser INSTANCE = new UserWebSocketParser();

    private InstanceHolder() {}
  }
}
