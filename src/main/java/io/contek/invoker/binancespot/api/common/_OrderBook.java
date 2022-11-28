package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

@NotThreadSafe
public class _OrderBook {

    public Long lastUpdateId;

    public List<_OrderBookLevel> bids;
    public List<_OrderBookLevel> asks;
}
