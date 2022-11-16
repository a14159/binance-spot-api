package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class _Account {

  public int makerCommission;
  public int takerCommission;
  public int buyerCommission;
  public int sellerCommission;
  public boolean canTrade;
  public boolean canWithdraw;
  public boolean canDeposit;
  public long updateTime;
  public String accountType;
  public List<_AccountBalance> balances;
  public List<String> permissions;
}
