package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class KlineEvent extends WebSocketEventData {

  public String s; // Symbol
  public Candle k;

  @NotThreadSafe
  public static class Candle {

    public long t; // Kline start time
    public long T; // Kline close time
    public String s; // Symbol
    public String i; // Interval
    public long f; // First trade ID
    public long L; // Last trade ID
    public double o; // Open price
    public double c; // Close price
    public double h; // High price
    public double l; // Low price
    public double v; // Base asset volume
    public int n; // Number of trades
    public boolean x; // Is this kline closed?
    public double q; // Quote asset volume
    public double V; // Taker buy base asset volume
    public double Q; // Taker buy quote asset volume
  }
}
