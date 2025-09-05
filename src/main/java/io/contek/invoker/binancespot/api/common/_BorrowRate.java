package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class _BorrowRate {

  public int vipLevel;
  public String coin;
  public boolean transferIn;
  public boolean borrowable;
  public Double dailyInterest;
  public Double yearlyInterest;
  public Double borrowLimit;
  public List<String> marginablePairs;
}
