import io.contek.invoker.binancespot.api.ApiFactory;
import io.contek.invoker.binancespot.api.common._AccountBalance;
import io.contek.invoker.binancespot.api.rest.user.GetAccount;
import io.contek.invoker.binancespot.api.rest.user.UserRestApi;
import io.contek.invoker.security.ApiKey;

import java.math.BigDecimal;

public class TestAccount {

    public static void main(String[] args) {
        ApiKey key = ApiKey.newBuilder()
                .setId("rEeWct02uEibzseqkbyeUIXM1PzmvAUJaKfH1GdKIBG6dQUWpYApPcA16JgXWN4s")
                .setSecret("n6rn7viEc6Pt1R0wRmP7bY3pZZC1byPzoylnPMG0nGYIi12aGAHk2JYPaI1hv3bz").build();
        UserRestApi api = ApiFactory.getMainNet().rest().user(key);
        GetAccount.Response account = api.getAccount().submit();
        System.out.println("Account type: " + account.accountType);
        System.out.println("Maker commission: " + account.makerCommission);
        System.out.println("Taker commission: " + account.takerCommission);
        for (_AccountBalance b : account.balances) {
            if (b.free.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println();
                System.out.println("Coin: " + b.asset);
                System.out.println("Free: " + b.free);
                System.out.println("Locked: " + b.locked);
            }
        }
    }
}
