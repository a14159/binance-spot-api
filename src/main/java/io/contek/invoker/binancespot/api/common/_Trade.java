package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _Trade {

  public String symbol;
  public long id;
  public long orderId;
  public long orderListId;
  public BigDecimal price;
  public BigDecimal qty;
  public BigDecimal quoteQty;
  public BigDecimal commission;
  public String commissionAsset;
  public long time;
  public boolean isBuyer;
  public boolean isMaker;
  public boolean isBestMatch;
}
