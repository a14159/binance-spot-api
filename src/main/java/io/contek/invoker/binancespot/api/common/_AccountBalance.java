package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _AccountBalance {

  public String asset;
  public BigDecimal free;
  public BigDecimal locked;
}
