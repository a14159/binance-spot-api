package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class TickerEvent extends WebSocketEventData {

  public String s; // Symbol
  public double p; // Price change
  public double P; // Price change percent
  public double w; // Weighted average price
  public double c; // Last price
  public double Q; // Last quantity
  public double o; // Open price
  public double h; // High price
  public double l; // Low price
  public double v; // Total traded base asset volume
  public double q; // Total traded quote asset volume
  public long O; // Statistics open time
  public long C; // Statistics close time
  public long F; // First trade ID
  public long L; // Last trade Id
  public int n; // Total number of trades
}
