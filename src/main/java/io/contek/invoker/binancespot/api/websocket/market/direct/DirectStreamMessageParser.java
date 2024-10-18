package io.contek.invoker.binancespot.api.websocket.market.direct;

import com.alibaba.fastjson2.JSON;
import io.contek.invoker.commons.websocket.AnyWebSocketMessage;
import io.contek.invoker.commons.websocket.IWebSocketComponent;
import io.contek.invoker.commons.websocket.WebSocketTextMessageParser;

import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
final class DirectStreamMessageParser<T extends AnyWebSocketMessage>
    extends WebSocketTextMessageParser {

  private final Class<T> type;

  DirectStreamMessageParser(Class<T> type) {
    this.type = type;
  }

  @Override
  public void register(IWebSocketComponent component) {}

  @Override
  protected AnyWebSocketMessage fromText(String text) {
    return JSON.parseObject(text, type);
  }
}
