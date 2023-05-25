import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class ProductBookSide {

    private BookSide side;
    final HashMap<Price, ArrayList<Order>> bookEntries;

    public ProductBookSide(BookSide side, HashMap<Price, ArrayList<Order>> bookEntries) {
        if (side == null) {
            throw new IllegalArgumentException("BookSide cannot be null");
        }
        this.side = side;
        this.bookEntries = bookEntries;
    }

    public OrderDTO add(Order o){
        ArrayList<Order> list = new ArrayList<>();
        list.add(o);
        if(!bookEntries.containsKey(o.getPrice()))
            bookEntries.put(o.getPrice(),list);
        else
            bookEntries.get(o.getPrice()).add(o);
        return new OrderDTO(o);
    }

    public OrderDTO cancel(String orderID) throws OrderException {
        for(ArrayList<Order> orders : bookEntries.values()) {
            for(Order order : orders) {
                if(order.getId().equals(orderID)) {
                    orders.remove(order);
                    order.setCancelledVolume(order.getRemainingVolume());
                    order.setRemainingVolume(0);
                    if(orders.isEmpty())
                        bookEntries.remove(order.getPrice());
                    return new OrderDTO(order);
                }
            }
        }
        return null;
    }

    public Price topOfBookPrice(){
        Price price = null;
        if(this.side.equals(BookSide.BUY)) {
            if(!bookEntries.isEmpty())
                price = Collections.max(bookEntries.keySet());
        }
        else if(this.side.equals(BookSide.SELL)){
            if(!bookEntries.isEmpty())
                price = Collections.min(bookEntries.keySet());
        }
        return price;
    }

    public int topOfBookVolume(){
        Price price = topOfBookPrice();
        int sum=0;
        for(Order orders : bookEntries.get(price)){
            sum += orders.getRemainingVolume();
        }
        return sum;
    }

    public void tradeOut(Price price, int vol) throws OrderException {
        int remainingVol = vol;
        StringBuilder output = new StringBuilder();
        ArrayList<Order> orders = bookEntries.get(price);
        while(remainingVol > 0) {
            if(orders.isEmpty()) {
                bookEntries.remove(price);
                return ;
            }
            else {
                Order first_order = orders.get(0);
                if (first_order.getRemainingVolume() <= remainingVol) {
                    orders.remove(first_order);
                    first_order.setFilledVolume(first_order.getFilledVolume() + first_order.getRemainingVolume());
                    output.append(String.format("   FILL: (%s %s) ",side,first_order.getRemainingVolume()));
                    first_order.setRemainingVolume(0);
                    System.out.println(output.append(first_order));
                    remainingVol -= first_order.getRemainingVolume();
                    output.delete(0,output.length());
                    OrderDTO orderDTO = first_order.makeTradableDTO();
                    UserManager.getInstance().addToUser(first_order.getUser(), orderDTO);

                } else {
                    first_order.setFilledVolume(first_order.getFilledVolume() + remainingVol);
                    first_order.setRemainingVolume(first_order.getRemainingVolume() - remainingVol);
                    remainingVol = 0;
                    System.out.println(output.append(String.format("   PARTIAL FILL: (%s %s) %s" ,side,first_order.getFilledVolume(),first_order)));
                    OrderDTO orderDTO = first_order.makeTradableDTO();
                    UserManager.getInstance().addToUser(first_order.getUser(), orderDTO);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("Side: "+side);

        if(side.equals(BookSide.BUY)){
            if(bookEntries.isEmpty())
                output.append("\n<Empty>");
            else {
                for (Price price : bookEntries.keySet()) {
                    output.append("\n   Price: " + price.toString());
                    for (Order orders : bookEntries.get(price))
                        output.append("\n       " + orders.toString());
                }
            }
        }
        else{
            TreeMap<Price, ArrayList<Order>> sortedlist = new TreeMap<>();
            sortedlist.putAll(bookEntries);
            if(bookEntries.isEmpty())
                output.append("\n<Empty>");
            else {
                for (Price price : sortedlist.keySet()) {
                    output.append("\n   Price: " + price.toString());
                    for (Order orders : bookEntries.get(price))
                        output.append("\n       " + orders.toString());
                }
            }
        }
        return output.toString();
    }
}