package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _AggTrade {

  public long a; // agg trade id
  public BigDecimal p; // price
  public BigDecimal q; // qty
  public long f; // first trade id
  public long l; // last trade id
  public long T; // timestamp
  public boolean m; // Was the buyer the maker?
  public boolean M; // Was the trade the best price match?
}
