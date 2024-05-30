import java.util.HashMap;

public class User implements CurrentMarketObserver {

    private final String userId;
    private HashMap<String, OrderDTO> orders;
    private HashMap<String, CurrentMarketSide[]> currentMarkets = new HashMap<>();

    public User(String userId) throws OrderException {
        this.userId = setUserId(userId);
        this.orders = new HashMap<>();
    }

    public String getUserId() {
        return userId;
    }

    private String setUserId(String userId) throws OrderException {
        if (userId == null || !userId.matches("^[A-Za-z]{3}$")) {
            throw new OrderException("Invalid user code");
        }
        return userId;
    }

    public void addOrder(OrderDTO o) throws OrderException {
        if(o != null)
            orders.put(o.id, o);
        else
            throw new OrderException("User not found");
    }

    public boolean hasOrderWithRemainingQty() {
        for(OrderDTO order : orders.values())
            if(order.remainingVolume > 0)
                return true;
        return false;
    }

    public OrderDTO getOrderWithRemainingQty() {
        for(OrderDTO order : orders.values())
            if(order.remainingVolume > 0)
                return order;
        return null;
    }

    public void updateCurrentMarket(String symbol, CurrentMarketSide buySide, CurrentMarketSide sellSide) {
        CurrentMarketSide[] cmsArray = {buySide,sellSide};
        currentMarkets.put(symbol,cmsArray);
    }

    public String getCurrentMarkets() {
        StringBuilder output = new StringBuilder();
        for (String symbol : currentMarkets.keySet())
            output.append(symbol + "    " + currentMarkets.get(symbol)[0].toString() + " - " + currentMarkets.get(symbol)[1].toString()).append('\n');
        return output.toString();
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("User Id: "+getUserId()).append('\n');
        for(OrderDTO order : orders.values())
            output.append("Product: " + order.product + ", Price: " + order.price + ", OriginalVolume: " + order.originalVolume + ", RemainingVolume: " + order.remainingVolume +
                    ", CancelledVolume: " + order.cancelledVolume + ", FilledVolume: " + order.filledVolume + ", User: " + order.user + ", Side: " + order.side + ", Id: " + order.id).append('\n');
        return output.toString();
    }
}
