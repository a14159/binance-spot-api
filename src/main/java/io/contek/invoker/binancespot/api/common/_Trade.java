package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _Trade {

  public String symbol;
  public String id;
  public String orderId;
  public long orderListId;
  public Double price;
  public Double qty;
  public Double quoteQty;
  public Double commission;
  public String commissionAsset;
  public long time;
  public boolean isBuyer;
  public boolean isMaker;
  public boolean isBestMatch;
  public long traceNano;
}
