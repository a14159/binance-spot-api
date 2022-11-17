package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _MarginAsset {

  public String asset;
  public BigDecimal borrowed;
  public BigDecimal free;
  public BigDecimal interest;
  public BigDecimal locked;
  public BigDecimal netAsset;
}
