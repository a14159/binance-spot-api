package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _CrossCollateral {

  public String loanCoin;
  public String collateralCoin;
  public Double locked;
  public Double loanAmount;
  public Double currentCollateralRate;
  public Double interestFreeLimitUsed;
  public Double principalForInterest;
  public Double interest;
}
