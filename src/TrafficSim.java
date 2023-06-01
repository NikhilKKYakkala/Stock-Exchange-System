import java.util.HashMap;
import java.util.Random;

public class TrafficSim {
    private static HashMap<String,Double> basePrices = new HashMap<>();

    public static void runSim() throws OrderException, InvalidPriceOperation {
        UserManager userManager = UserManager.getInstance();
        ProductManager productManager = ProductManager.getinstance();

        userManager.init(new String[]{"ANN","BOB","CAT","DOG","EGG"});

        User user1 = UserManager.getInstance().getUser("ANN");
        User user2 = UserManager.getInstance().getUser("BOB");
        User user3 = UserManager.getInstance().getUser("CAT");
        User user4 = UserManager.getInstance().getUser("DOG");
        User user5 = UserManager.getInstance().getUser("EGG");

        productManager.addProduct("WMT");
        productManager.addProduct("TGT");
        productManager.addProduct("AMZN");
        productManager.addProduct("TSLA");

        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("WMT",user1);
        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("TGT",user1);

        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("TGT",user2);
        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("TSLA",user2);

        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("AMZN",user3);
        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("TGT",user3);
        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("WMT",user3);

        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("TSLA",user4);

        CurrentMarketPublisher.getInstance().subscribeCurrentMarket("WMT",user5);

        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("TGT",user2);

        basePrices.put("WMT",140.98);
        basePrices.put("TGT",174.76);
        basePrices.put("AMZN",102.11);
        basePrices.put("TSLA",196.81);

        for(int i=0;i<100;i++) {
            System.out.print((i+1) +") ");
            User user = userManager.getRandomUser();
            if(Math.random() < 0.9) {
                String product = productManager.getRandomProduct();
                BookSide side = getRandomBookSide();
                int orderVol = (int) Math.round((int) (25 + Math.random() * 300)/5.0) *5;
                Price price = getPrice(product,side);
                Order order = new Order(user.getUserId(),product,price,orderVol,side);
                OrderDTO orderDTO = productManager.addOrder(order);
                user.addOrder(orderDTO);
            }
            else {
                if(user.hasOrderWithRemainingQty()){
                    OrderDTO userOrderDTO = user.getOrderWithRemainingQty();
                    if(userOrderDTO != null) {
                        OrderDTO productOrderDTO = productManager.cancel(userOrderDTO);
                        if (productOrderDTO != null)
                            user.addOrder(productOrderDTO);
                    }
                }
            }
        }
        System.out.print("*****Current Markets for User Subscribed Products*****\n");
        System.out.print(user1.getUserId() + ":\n" + user1.getCurrentMarkets() + '\n');
        System.out.print(user2.getUserId() + ":\n" + user2.getCurrentMarkets() + '\n');
        System.out.print(user3.getUserId() + ":\n" + user3.getCurrentMarkets() + '\n');
        System.out.print(user4.getUserId() + ":\n" + user4.getCurrentMarkets() + '\n');
        System.out.print(user5.getUserId() + ":\n" + user5.getCurrentMarkets() + '\n');

        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("WMT",user1);
        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("TGT",user1);

        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("TSLA",user2);

        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("AMZN",user3);
        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("TGT",user3);
        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("WMT",user3);

        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("TSLA",user4);

        CurrentMarketPublisher.getInstance().unSubcribeCurrentMarket("WMT",user5);
    }

    public static Price getPrice(String symbol, BookSide side) {
        double basePrice = basePrices.get(symbol);
        double priceWidth = 0.02;
        double startPrice = 0.01;
        double tickSize = 0.1;
        double gapFromBase = basePrice * priceWidth;
        double priceVariance = gapFromBase * (Math.random());
        double priceToTick;
        double priceToUse;

        if(side == BookSide.BUY)
            priceToUse = (basePrice * (1 - startPrice)) + priceVariance;
        else
            priceToUse = (basePrice * (1 + startPrice)) - priceVariance;

        priceToTick = Math.round(priceToUse * 1/tickSize) / 20.0;
//        return PriceFactory.makePrice((int)priceToTick*100);
        return PriceFactory.makePrice((int)Math.floor(priceToTick * 100));
    }

    private static BookSide getRandomBookSide() {
        Random random = new Random();
        return random.nextBoolean() ? BookSide.BUY : BookSide.SELL;
    }
}
