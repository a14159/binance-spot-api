import io.contek.invoker.binancespot.api.ApiFactory;
import io.contek.invoker.binancespot.api.rest.user.GetBorrowRate;
import io.contek.invoker.binancespot.api.rest.user.UserRestApi;
import io.contek.invoker.security.ApiKey;


public class TestBorrowRates {
    public static void main(String[] args) {
        ApiKey key = ApiKey.newBuilder()
                .setId("rEeWct02uEibzseqkbyeUIXM1PzmvAUJaKfH1GdKIBG6dQUWpYApPcA16JgXWN4s")
                .setSecret("n6rn7viEc6Pt1R0wRmP7bY3pZZC1byPzoylnPMG0nGYIi12aGAHk2JYPaI1hv3bz").build();
        UserRestApi api = ApiFactory.getMainNet().rest().user(key);
        GetBorrowRate.Response borrowRate = api.getBorrowRate().setCoin("BTC").submit();
        System.out.println("Coin: " + borrowRate.get(0).coin);
        System.out.println("Borrowable: " + borrowRate.get(0).borrowable);
        System.out.println("Daily interest: " + borrowRate.get(0).dailyInterest);
        System.out.println("Yearly interest: " + borrowRate.get(0).yearlyInterest);

        for (String pair : borrowRate.get(0).marginablePairs)
            System.out.println("Pair: " + pair);

    }
}
