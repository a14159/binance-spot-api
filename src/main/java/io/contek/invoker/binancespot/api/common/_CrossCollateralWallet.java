package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class _CrossCollateralWallet {

  public Double totalCrossCollateral;
  public Double totalBorrowed;
  public Double totalInterest;
  public Double interestFreeLimit;
  public String asset;
  public List<_CrossCollateral> crossCollaterals;
}
