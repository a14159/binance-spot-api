package io.contek.invoker.binancespot.api.websocket.user;


import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;

@ThreadSafe
public final class OrderUpdateChannel extends UserWebSocketChannel<OrderUpdateChannel.Data> {

    OrderUpdateChannel() {
        super(Id.INSTANCE);
    }

    @Override
    public Class<Data> getMessageType() {
        return Data.class;
    }

    @Immutable
    public static final class Id extends UserWebSocketChannelId<Data> {

        private static final Id INSTANCE = new Id();

        private Id() {
            super(UserEventTypeKeys._ORDER_UPDATE);
        }
    }

    @NotThreadSafe
    public static final class Data extends WebSocketEventData {

        public String s; // symbol
        public String c; // client order id
        public String S; // side
        public String o; // order type
        public String f; // time in force
        public BigDecimal q; // Order quantity
        public BigDecimal p; // Order price
        public BigDecimal P; // stop price
        public int d; // Trailing Delta; This is only visible if the order was a trailing stop order.
        public BigDecimal F; // iceberg qty
        public int g; // order list id
        public String C; // original client order id
        public String x; // Current execution type
        public String X; // current order status
        public String r; // Order reject reason; will be an error code
        public String i; // order id
        public BigDecimal l; // order last filled quantity
        public BigDecimal z; // order filled accumulated quantity
        public BigDecimal L; // Last executed price
        public String N; // commission asset
        public BigDecimal n; // commission amount
        public long T; // order trade time
        public String t; // trade id
        public long I; // ignore

        public boolean w; // is the order in the book?
        public boolean m; // is the trade the maker side?
        public boolean M; // ignore
        public long O; // order creation time
        public BigDecimal Z; // Cumulative quote asset transacted quantity
        public BigDecimal Y; // Last quote asset transacted quantity (i.e. lastPrice * lastQty)
        public BigDecimal Q; // Quote Order Qty
        public long j; // strategy id. This is only visible if the strategyId parameter was provided upon order placement
        public long J; // strategy type. This is only visible if the strategy type parameter was provided upon order placement
    }
}
