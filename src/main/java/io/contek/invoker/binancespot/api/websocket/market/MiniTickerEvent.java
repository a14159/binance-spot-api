package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class MiniTickerEvent extends WebSocketEventData {

  public String s; // Symbol
  public double c; // Close price
  public double o; // Open price
  public double h; // High price
  public double l; // Low price
  public double v; // Total traded base asset volume
  public double q; // Total traded quote asset volume
}
