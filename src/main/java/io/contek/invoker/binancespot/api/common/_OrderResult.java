package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class _OrderResult {

  public String symbol;
  public long orderId;
  public String clientOrderId;
  public long transactTime;
  public BigDecimal price;
  public BigDecimal origQty;
  public BigDecimal executedQty;
  public BigDecimal cummulativeQuoteQty;
  public String status;
  public String timeInForce;
  public String type;
  public boolean isIsolated;
  public String side;
}
