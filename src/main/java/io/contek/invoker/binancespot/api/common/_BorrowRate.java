package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.util.List;

@NotThreadSafe
public class _BorrowRate {

  public int vipLevel;
  public String coin;
  public boolean transferIn;
  public boolean borrowable;
  public BigDecimal dailyInterest;
  public BigDecimal yearlyInterest;
  public BigDecimal borrowLimit;
  public List<String> marginablePairs;
}
