package io.contek.invoker.binancespot.api.websocket.market.combined;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.google.common.base.Splitter;
import io.contek.invoker.binancespot.api.websocket.market.BookTickerEvent;
import io.contek.invoker.commons.websocket.AnyWebSocketMessage;
import io.contek.invoker.commons.websocket.IWebSocketComponent;
import io.contek.invoker.commons.websocket.WebSocketTextMessageParser;

import javax.annotation.concurrent.Immutable;
import java.util.List;

import static io.contek.invoker.binancespot.api.websocket.common.constants.WebSocketChannelKeys.*;

@Immutable
final class MarketCombinedMessageParser extends WebSocketTextMessageParser {

  static MarketCombinedMessageParser getInstance() {
    return MarketCombinedMessageParser.InstanceHolder.INSTANCE;
  }

  @Override
  public void register(IWebSocketComponent component) {}

  @Override
  protected AnyWebSocketMessage fromText(String text) {
    JSONObject json = JSON.parseObject(text);
    if (json.containsKey("id")) {
      return toRequestConfirmation(json);
    }
    if (json.containsKey("stream")) {
      return toStreamData(json);
    }
    return toBookTicker(json);
  }

  private AnyWebSocketMessage toRequestConfirmation(JSONObject obj) {
    return obj.toJavaObject(WebSocketCommandConfirmation.class);
  }

  private AnyWebSocketMessage toStreamData(JSONObject obj) {
    String stream = obj.get("stream").toString();
    List<String> parts = Splitter.on('@').splitToList(stream);
    if (parts.size() < 2) {
      throw new IllegalArgumentException(stream);
    }
    String type = parts.get(1);
    return switch (type) {
      case _bookTicker -> obj.toJavaObject(BookTickerChannel.Message.class);
      case _trade -> obj.toJavaObject(TradeChannel.Message.class);
      case _aggTrade -> obj.toJavaObject(AggTradeChannel.Message.class);
      case _depth -> obj.toJavaObject(DepthDiffChannel.Message.class);
      case _depth5, _depth10, _depth20 -> obj.toJavaObject(DepthPartialChannel.Message.class);
      default -> throw new IllegalStateException();
    };
  }

  private AnyWebSocketMessage toBookTicker(JSONObject obj) {
    return obj.toJavaObject(BookTickerEvent.class);
  }

  private MarketCombinedMessageParser() {}

  @Immutable
  private static class InstanceHolder {

    private static final MarketCombinedMessageParser INSTANCE = new MarketCombinedMessageParser();

    private InstanceHolder() {}
  }
}
