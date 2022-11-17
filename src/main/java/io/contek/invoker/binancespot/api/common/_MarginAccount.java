package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.util.List;

@NotThreadSafe
public class _MarginAccount {

  public boolean borrowEnabled;
  public BigDecimal marginLevel;
  public BigDecimal totalAssetOfBtc;
  public BigDecimal totalLiabilityOfBtc;
  public BigDecimal totalNetAssetOfBtc;
  public boolean tradeEnabled;
  public boolean transferEnabled;
  public List<_MarginAsset> userAssets;
}
