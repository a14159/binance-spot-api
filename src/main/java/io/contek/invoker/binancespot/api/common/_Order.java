package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _Order implements Cloneable {

  public String symbol;
  public long orderId;
  public long orderListId;
  public String clientOrderId;
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
  public String origClientOrderId;
  public BigDecimal origQuoteOrderQty;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
}
