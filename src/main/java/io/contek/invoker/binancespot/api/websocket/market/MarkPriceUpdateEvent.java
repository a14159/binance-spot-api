package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class MarkPriceUpdateEvent extends WebSocketEventData {

  public String s; // Symbol
  public Double p; // Mark price
  public double r; // Funding rate
  public long T; // Next funding time
}
