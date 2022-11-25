package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _MiniTickerSummary {

  public String symbol;
  public BigDecimal openPrice;
  public BigDecimal highPrice;
  public BigDecimal lowPrice;
  public BigDecimal lastPrice;
  public BigDecimal volume;
  public BigDecimal quoteVolume;
  public long openTime;
  public long closeTime;
  public long firstId;
  public long lastId;
  public long count;
}
