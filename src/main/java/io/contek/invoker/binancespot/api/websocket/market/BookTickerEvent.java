package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class BookTickerEvent extends WebSocketEventData {

  public long u; // order book updateId
  public String s; // symbol
  public BigDecimal b; // best bid price
  public BigDecimal B; // best bid qty
  public BigDecimal a; // best ask price
  public BigDecimal A; // best ask qty
  public long traceNano = System.nanoTime();
}
