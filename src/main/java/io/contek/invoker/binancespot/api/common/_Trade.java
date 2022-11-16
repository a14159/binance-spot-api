package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _Trade {

  public long id;
  public BigDecimal price;
  public BigDecimal qty;
  public long time;
  public boolean isBuyerMaker;
  public boolean isBestMatch;
}
