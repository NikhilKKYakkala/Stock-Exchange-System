import java.util.HashMap;

public class User {

    private final String userId;
    private HashMap<String, OrderDTO> orders;
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
