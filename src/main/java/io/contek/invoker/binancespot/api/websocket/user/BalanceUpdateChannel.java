package io.contek.invoker.binancespot.api.websocket.user;


import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;

@ThreadSafe
public final class BalanceUpdateChannel extends UserWebSocketChannel<BalanceUpdateChannel.Data> {

    BalanceUpdateChannel() {
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
            super(UserEventTypeKeys._BALANCE_UPDATE);
        }
    }

    @NotThreadSafe
    public static final class Data extends WebSocketEventData {

        public String a; // Asset
        public BigDecimal d; // Balance Delta
        public long T; // Clear Time
    }
}
