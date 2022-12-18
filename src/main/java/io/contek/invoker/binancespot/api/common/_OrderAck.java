package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _OrderAck {

  public String symbol;
  public long orderId;
  public String clientOrderId;
  public boolean isIsolated; // only for margin orders
  public int orderListId; // only for spot orders
  public long transactTime;
}
