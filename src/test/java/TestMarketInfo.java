import io.contek.invoker.binancespot.api.ApiFactory;
import io.contek.invoker.binancespot.api.common._ExchangeInfo;
import io.contek.invoker.binancespot.api.common._MarketInfo;
import io.contek.invoker.binancespot.api.rest.market.MarketRestApi;

public class TestMarketInfo {
    public static void main(String[] args) {
        MarketRestApi restAPI = ApiFactory.getMainNet().rest().market();
        _ExchangeInfo result = restAPI.getExchangeInfo().submit();
        for (_MarketInfo mi : result.symbols) {
            if (mi.baseAssetPrecision != 8 || mi.quoteAssetPrecision != 8) {
                System.out.println(mi.symbol);
                System.out.println("base asset precision: " + mi.baseAssetPrecision);
                System.out.println("quote asset precision: " + mi.quoteAssetPrecision);
                System.out.println("quote precision: " + mi.quotePrecision);
            }
        }
    }
}
