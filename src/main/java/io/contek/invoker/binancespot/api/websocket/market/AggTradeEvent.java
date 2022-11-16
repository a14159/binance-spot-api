package io.contek.invoker.binancespot.api.websocket.market;

import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;

@NotThreadSafe
public class AggTradeEvent extends WebSocketEventData {

  public String s; // Symbol
  public long a; // Aggregate trade ID
  public BigDecimal p; // Price
  public BigDecimal q; // Quantity
  public long f; // First trade ID
  public long l; // Last trade ID
  public long T; // Trade time
  public boolean m; // Is the buyer the market maker?
}
