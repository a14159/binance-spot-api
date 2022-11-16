package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class MarkPriceUpdateEvent extends WebSocketEventData {

  public String s; // Symbol
  public BigDecimal p; // Mark price
  public double r; // Funding rate
  public long T; // Next funding time
}
