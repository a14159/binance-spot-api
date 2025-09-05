package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class _MarginAccount {

  public boolean borrowEnabled;
  public Double marginLevel;
  public Double totalAssetOfBtc;
  public Double totalLiabilityOfBtc;
  public Double totalNetAssetOfBtc;
  public boolean tradeEnabled;
  public boolean transferEnabled;
  public List<_MarginAsset> userAssets;
}
