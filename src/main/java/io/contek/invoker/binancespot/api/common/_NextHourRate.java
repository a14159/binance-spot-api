package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.util.List;

@NotThreadSafe
public class _NextHourRate {

  public String asset;
  public BigDecimal nextHourlyInterestRate;
}
