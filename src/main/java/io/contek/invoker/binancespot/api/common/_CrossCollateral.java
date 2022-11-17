package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _CrossCollateral {

  public String loanCoin;
  public String collateralCoin;
  public BigDecimal locked;
  public BigDecimal loanAmount;
  public BigDecimal currentCollateralRate;
  public BigDecimal interestFreeLimitUsed;
  public BigDecimal principalForInterest;
  public BigDecimal interest;
}
