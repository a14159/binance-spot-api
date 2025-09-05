package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class TradeEvent extends WebSocketEventData {

  public String s; // Symbol
  public long t; // Trade ID
  public Double p; // Price
  public Double q; // Quantity
  public long T; // Trade time
  public boolean m; // Is the buyer the market maker?
  public long traceNano = System.nanoTime();
}
