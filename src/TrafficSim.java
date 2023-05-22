import java.util.HashMap;
import java.util.Random;

public class TrafficSim {
    private static HashMap<String,Double> basePrices = new HashMap<>();
    private static final int num_users = 40;

    public static void runSim() throws OrderException, InvalidPriceOperation {
        UserManager userManager = UserManager.getInstance();
        ProductManager productManager = ProductManager.getinstance();

        userManager.init(new String[]{"ANN","BOB","CAT","DOG","EGG"});
        productManager.addProduct("WMT");
        productManager.addProduct("TGT");
        productManager.addProduct("AMZN");
        productManager.addProduct("TSLA");

        basePrices.put("WMT",140.98);
        basePrices.put("TGT",174.76);
        basePrices.put("AMZN",102.11);
        basePrices.put("TSLA",196.81);

        for(int i=0;i<num_users;i++) {
            System.out.println(""+ (i+1) +") ");
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
        System.out.println("-------------------------------------------------------------------------------------------\nProductBooks: \n");
        System.out.println(productManager.toString());
        System.out.println("-------------------------------------------------------------------------------------------\nUsers: \n");
        System.out.println(userManager.toString());
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
        return PriceFactory.makePrice((int)Math.floor(priceToTick * 100));
    }

    private static BookSide getRandomBookSide() {
        Random random = new Random();
        return random.nextBoolean() ? BookSide.BUY : BookSide.SELL;
    }
}
