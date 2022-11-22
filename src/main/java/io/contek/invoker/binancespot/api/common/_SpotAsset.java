package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _SpotAsset {

  public String asset;
  public BigDecimal free;
  public BigDecimal locked;
  public BigDecimal freeze;
  public BigDecimal withdrawing;
  public BigDecimal ipoable;
  public BigDecimal btcValuation;
}
