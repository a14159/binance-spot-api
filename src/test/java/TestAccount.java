import io.contek.invoker.binancespot.api.ApiFactory;
import io.contek.invoker.binancespot.api.common._AccountBalance;
import io.contek.invoker.binancespot.api.common._CrossCollateral;
import io.contek.invoker.binancespot.api.common._MarginAsset;
import io.contek.invoker.binancespot.api.common._TradeFees;
import io.contek.invoker.binancespot.api.rest.user.*;
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

        System.out.println();
        GetTradeFee.Response tradeFee = api.getTradeFee().setSymbol("AAVEETH").submit();
        System.out.println("Random maker fees: " + tradeFee.get(0).makerCommission);
        System.out.println("Random taker fees: " + tradeFee.get(0).takerCommission);

        System.out.println();
        GetTradeFee.Response tradeFees = api.getTradeFee().submit();
        int cnt = 0;
        for (_TradeFees fees : tradeFees) {
            if (cnt++ > 5)
                break;
            System.out.println();
            System.out.println("Coin: " + fees.symbol);
            System.out.println("Maker fees: " + fees.makerCommission);
            System.out.println("Taker fees: " + fees.takerCommission);
        }

//        System.out.println();
//        GetMarginAccountSummary.Response marginSummary = api.getMarginAccountSummary().submit();
//        System.out.println("Margin account - account status: " + marginSummary.accountStatus);
//        System.out.println("Margin account - total equity: " + marginSummary.accountEquity);

        System.out.println();
        GetMarginAccount.Response marginAccount = api.getMarginAccount().submit();
        System.out.println("Margin account - margin level: " + marginAccount.marginLevel);
        System.out.println("Margin account - total net assets: " + marginAccount.totalNetAssetOfBtc);
        for (_MarginAsset asset : marginAccount.userAssets) {
            if (asset.netAsset.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println();
                System.out.println("Coin: " + asset.asset);
                System.out.println("Net qty: " + asset.netAsset);
            }
        }

        System.out.println();
        GetCrossCollateralWallet.Response crossWallet = api.getCrossCollateralWallet().submit();
        System.out.println("Cross wallet - asset: " + crossWallet.asset);
        System.out.println("Cross wallet - total collateral: " + crossWallet.totalCrossCollateral);
        for (_CrossCollateral asset : crossWallet.crossCollaterals) {
            if (asset.loanAmount.compareTo(BigDecimal.ZERO) > 0) {
                System.out.println();
                System.out.println("Loan coin: " + asset.loanCoin);
                System.out.println("Loan amount: " + asset.loanAmount);
            }
        }
    }
}
