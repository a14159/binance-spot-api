package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.math.BigDecimal;
import java.util.ArrayList;

@NotThreadSafe
public final class _OrderBookLevel extends ArrayList<BigDecimal> {
    public BigDecimal getPrice() {
        return get(0);
    }

    public BigDecimal getQty() {
        return get(1);
    }
}
