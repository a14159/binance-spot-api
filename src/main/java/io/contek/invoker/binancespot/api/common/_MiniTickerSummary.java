package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _MiniTickerSummary {

  public String symbol;
  public Double openPrice;
  public Double highPrice;
  public Double lowPrice;
  public Double lastPrice;
  public Double volume;
  public Double quoteVolume;
  public long openTime;
  public long closeTime;
  public long firstId;
  public long lastId;
  public long count;
}
