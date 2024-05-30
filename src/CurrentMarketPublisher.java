import java.util.ArrayList;
import java.util.HashMap;

public class CurrentMarketPublisher {

    private HashMap<String, ArrayList<CurrentMarketObserver>> filters = new HashMap<>();

    private static CurrentMarketPublisher instance = null;

    public static CurrentMarketPublisher getInstance() {
        if(instance == null)
            instance = new CurrentMarketPublisher();
        return instance;
    }

    public void subscribeCurrentMarket(String symbol, CurrentMarketObserver cmo) {
        if(!filters.containsKey(symbol)) {
            filters.put(symbol,new ArrayList<CurrentMarketObserver>());
            filters.get(symbol).add(cmo);
        }
        else
            filters.get(symbol).add(cmo);
    }

    public void unSubcribeCurrentMarket(String symbol, CurrentMarketObserver cmo) {
        if(!filters.containsKey(symbol))
            return;
        filters.get(symbol).remove(cmo);
    }

    public void acceptCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) {
        if(!filters.containsKey(symbol))
            return;

        ArrayList<CurrentMarketObserver> list = filters.get(symbol);

        for(CurrentMarketObserver cmo : list) {
            cmo.updateCurrentMarket(symbol,buySide,sellSide);
        }
    }
}
