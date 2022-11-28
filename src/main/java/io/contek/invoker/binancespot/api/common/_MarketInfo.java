package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.HashMap;
import java.util.List;

@NotThreadSafe
public class _MarketInfo {

  public String symbol;
  public String status;
  public String baseAsset;
  public int baseAssetPrecision;
  public String quoteAsset;
  public int quotePrecision;
  public int quoteAssetPrecision;
  public List<String> orderTypes;
  public boolean icebergAllowed;
  public boolean ocoAllowed;
  public boolean quoteOrderQtyMarketAllowed;
  public boolean allowTrailingStop;
  public boolean cancelReplaceAllowed;
  public boolean isSpotTradingAllowed;
  public boolean isMarginTradingAllowed;
  public List<_Filter> filters;
  public List<String> permissions;

  @NotThreadSafe
  public static class _Filter extends HashMap<String, String> {
    public String getFilterType() {
      return get("filterType");
    }
  }
}
