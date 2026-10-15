package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _AmendedOrder {

  public String symbol;
  public String orderId;
  public long orderListId;
  public String origClientOrderId;
  public String clientOrderId;
  public Double price;
  public Double qty;
  public Double executedQty;
  public Double preventedQty;
  public Double quoteOrderQty;
  public Double cumulativeQuoteQty;
  public String status;
  public String timeInForce;
  public String type;
  public String side;
  public long workingTime;
  public String selfTradePreventionMode;
}
