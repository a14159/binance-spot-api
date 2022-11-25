package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class _ExchangeInfo {

  public String timezone;
  public long serverTime;
  public List<String> rateLimits;
  public List<String> exchangeFilters;
  public List<_MarketInfo> symbols;
}
