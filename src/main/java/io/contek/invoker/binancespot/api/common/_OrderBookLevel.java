package io.contek.invoker.binancespot.api.common;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.ArrayList;

@NotThreadSafe
public final class _OrderBookLevel extends ArrayList<Double> {
    public Double getPrice() {
        return get(0);
    }

    public Double getQty() {
        return get(1);
    }
}
