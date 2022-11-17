package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _TradeFees {

  public String symbol;
  public BigDecimal makerCommission;
  public BigDecimal takerCommission;
}
