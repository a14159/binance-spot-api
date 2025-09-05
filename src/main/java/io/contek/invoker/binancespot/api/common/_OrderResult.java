package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _OrderResult {

  public String symbol;
  public String orderId;
  public String clientOrderId;
  public long transactTime;
  public Double price;
  public Double origQty;
  public Double executedQty;
  public Double cummulativeQuoteQty;
  public String status;
  public String timeInForce;
  public String type;
  public boolean isIsolated;
  public String side;
}
