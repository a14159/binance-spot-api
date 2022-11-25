import io.contek.invoker.binancespot.api.ApiFactory;
import io.contek.invoker.binancespot.api.websocket.market.IMarketWebSocketApi;
import io.contek.invoker.binancespot.api.websocket.market.TradeEvent;
import io.contek.invoker.commons.websocket.ConsumerState;
import io.contek.invoker.commons.websocket.ISubscribingConsumer;
import io.contek.invoker.commons.websocket.SubscriptionState;

public class TestMarketWS {

    public static void main(String[] args) {
        ISubscribingConsumer<TradeEvent> consumer = new ISubscribingConsumer<TradeEvent>() {
            @Override
            public void onStateChange(SubscriptionState subscriptionState) {
                System.out.println("State changed to: " + subscriptionState);
            }

            @Override
            public void onNext(TradeEvent tradeEvent) {
                System.out.println("Trade: price " + tradeEvent.p + " qty " + tradeEvent.q);
            }

            @Override
            public ConsumerState getState() {
                return ConsumerState.ACTIVE;
            }
        };
        IMarketWebSocketApi api = ApiFactory.getMainNet().ws().market(false);
        api.getTradeChannel("ethusdt").addConsumer(consumer);
    }
}
