package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _OrderAck implements Cloneable {

  public String symbol;
  public long orderId;
  public String clientOrderId;
  public long transactTime;
  public boolean isIsolated;
}
