import java.util.HashMap;
import java.util.Random;

public class ProductManager {
    private HashMap<String,ProductBook> products = new HashMap<>();
    private static ProductManager instance = null;

    private ProductManager() {}

    public static ProductManager getinstance() {
        if(instance == null)
            instance = new ProductManager();
        return instance;
    }

    public void addProduct(String symbol) throws OrderException {
        if(!products.containsKey(symbol)) {
            ProductBook stock = new ProductBook(symbol);
            products.put(symbol,stock);
        }
    }

    public String getRandomProduct() {
        if(!products.isEmpty()) {
            Object[] keyArray = products.keySet().toArray();
            Object key = keyArray[new Random().nextInt(keyArray.length)];
            return (String) key;
        }
        return null;
    }

    public OrderDTO addOrder(Order o) throws OrderException, InvalidPriceOperation {
        if(products.get(o.getProduct()) != null)
            return products.get(o.getProduct()).add(o);
        return null;
    }

    public OrderDTO cancel(OrderDTO o) throws OrderException {
        OrderDTO orderDTO = null;
        if(products.get(o.product) != null)
            orderDTO =  products.get(o.product).cancel(o.side,o.id);
        if(orderDTO == null) {
            System.out.println("Failed to cancel....");
        }
        return orderDTO;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for(ProductBook product : products.values())
            output.append(product.toString()).append('\n');
        return output.toString();
    }
}
