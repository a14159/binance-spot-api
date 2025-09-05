package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _MarginAccountSummary {

  public Double uniMMR; // Portfolio margin account maintenance margin rate
  public Double accountEquity; // Account equity, unit：USD
  public Double accountMaintMargin; // Portfolio margin account maintenance margin, unit：USD
  public String accountStatus; // Portfolio margin account status:"NORMAL", "MARGIN_CALL", "SUPPLY_MARGIN", "REDUCE_ONLY", "ACTIVE_LIQUIDATION", "FORCE_LIQUIDATION", "BANKRUPTED"
}
