package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
public class _Order {

  public String symbol;
  public String orderId;
  public long orderListId;
  public String clientOrderId;
  public long transactTime;
  public Double price;
  public Double origQty;
  public Double executedQty;
  public Double cummulativeQuoteQty;
  public String status;
  public String timeInForce;
  public String type;
  public String side;
  public Double stopPrice;
  public Double icebergQty;
  public long time;
  public long updateTime;
  public boolean isWorking;
  public long workingTime;
  public String origClientOrderId;
  public Double origQuoteOrderQty;
}
