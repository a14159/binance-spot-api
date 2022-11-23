package io.contek.invoker.binancespot.api.websocket.user;


import io.contek.invoker.binancespot.api.websocket.common.WebSocketEventData;
import io.contek.invoker.binancespot.api.websocket.user.constants.UserEventTypeKeys;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;
import javax.annotation.concurrent.ThreadSafe;
import java.math.BigDecimal;
import java.util.List;

@ThreadSafe
public final class AccountUpdateChannel extends UserWebSocketChannel<AccountUpdateChannel.Data> {

    AccountUpdateChannel() {
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
            super(UserEventTypeKeys._ACCOUNT_UPDATE);
        }
    }

    @NotThreadSafe
    public static final class Data extends WebSocketEventData {

        public long u; // Time of last account update
        public List<BalanceUpdate> B; // balance updates
    }

    @NotThreadSafe
    public static final class BalanceUpdate {

        public String a; // asset
        public BigDecimal f; // free
        public BigDecimal l; // Locked
    }
}
