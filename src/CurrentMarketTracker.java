public class CurrentMarketTracker {

    public static CurrentMarketTracker instance = null;

    private CurrentMarketTracker() {}

    public static CurrentMarketTracker getinstance() {
        if(instance == null)
            instance = new CurrentMarketTracker();
        return instance;
    }

    public void updateMarket(String symbol, Price buyPrice, int buyVolume, Price sellPrice, int sellVolume) {

        int marketWidth = 0;
        if (buyPrice != null && sellPrice != null)
            marketWidth = buyPrice.compareTo(sellPrice);

        if(buyPrice == null)
            buyPrice = PriceFactory.makePrice(0);
        if(sellPrice == null)
            sellPrice = PriceFactory.makePrice(0);

        CurrentMarketSide cmsbuySide = new CurrentMarketSide(buyPrice,buyVolume);
        CurrentMarketSide cmssellSide = new CurrentMarketSide(sellPrice,sellVolume);

        System.out.println(buyPrice + " - " + sellPrice + " = " + marketWidth);
        System.out.println("************Current Market************");
        System.out.println("* " + symbol + "    " + cmsbuySide + " - " + cmssellSide + " [" + PriceFactory.makePrice(marketWidth) + "]");
        System.out.println("**************************************");

        CurrentMarketPublisher.getInstance().acceptCurrentMarket(symbol,cmsbuySide,cmssellSide);
    }
}
