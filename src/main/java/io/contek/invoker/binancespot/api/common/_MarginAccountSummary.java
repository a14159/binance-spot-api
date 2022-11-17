package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _MarginAccountSummary {

  public BigDecimal uniMMR; // Portfolio margin account maintenance margin rate
  public BigDecimal accountEquity; // Account equity, unit：USD
  public BigDecimal accountMaintMargin; // Portfolio margin account maintenance margin, unit：USD
  public String accountStatus; // Portfolio margin account status:"NORMAL", "MARGIN_CALL", "SUPPLY_MARGIN", "REDUCE_ONLY", "ACTIVE_LIQUIDATION", "FORCE_LIQUIDATION", "BANKRUPTED"
}
