package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.util.List;

@NotThreadSafe
public class _CrossCollateralWallet {

  public BigDecimal totalCrossCollateral;
  public BigDecimal totalBorrowed;
  public BigDecimal totalInterest;
  public BigDecimal interestFreeLimit;
  public String asset;
  public List<_CrossCollateral> crossCollaterals;
}
