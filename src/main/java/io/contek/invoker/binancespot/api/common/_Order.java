package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _Order {

  public String symbol;
  public long orderId;
  public long orderListId;
  public String clientOrderId;
  public long transactTime;
  public BigDecimal price;
  public BigDecimal origQty;
  public BigDecimal executedQty;
  public BigDecimal cummulativeQuoteQty;
  public String status;
  public String timeInForce;
  public String type;
  public String side;
  public BigDecimal stopPrice;
  public BigDecimal icebergQty;
  public long time;
  public long updateTime;
  public boolean isWorking;
  public long workingTime;
  public String origClientOrderId;
  public BigDecimal origQuoteOrderQty;
}
