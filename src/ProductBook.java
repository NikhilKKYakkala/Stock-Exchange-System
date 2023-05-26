import java.util.ArrayList;
import java.util.HashMap;

public class ProductBook {

    private String product;
    ProductBookSide buySide;
    ProductBookSide sellSide;
    String tempside;

    public ProductBook(String product) throws OrderException {
        HashMap<Price, ArrayList<Order>> buymap = new HashMap<>();
        HashMap<Price, ArrayList<Order>> sellmap = new HashMap<>();
        if (product == null || !product.matches("^[A-Za-z0-9.]{1,5}$")) {
            throw new OrderException("Invalid product symbol");
        }
        this.product = product;
        buySide = new ProductBookSide(BookSide.BUY,buymap);
        sellSide = new ProductBookSide(BookSide.SELL,sellmap);
    }

    public OrderDTO add(Order o) throws InvalidPriceOperation, OrderException {
        ProductBookSide bookSide = (o.getSide().equals(BookSide.BUY)) ? buySide : sellSide;
        OrderDTO orderDTO = bookSide.add(o);
        if(bookSide.equals(buySide)) tempside = "BUY";
        else tempside = "SELL";
        System.out.println(String.format("ADD: %s: %s",tempside,o));
        tryTrade();
        return orderDTO;
    }

    public OrderDTO cancel(BookSide side, String orderId) throws OrderException {
        ProductBookSide bookSide = side == BookSide.BUY ? buySide : sellSide;
        OrderDTO orderDTO = bookSide.cancel(orderId);
        if(orderDTO != null) {
            if (bookSide.equals(buySide)) tempside = "BUY";
            else tempside = "SELL";
            System.out.println(String.format("CANCEL: %s Order: %s Cxl Qty: %s", tempside, orderId, orderDTO.cancelledVolume));
        }
        return orderDTO;
    }

    private void tryTrade() throws InvalidPriceOperation, OrderException {
        Price buyPrice = buySide.topOfBookPrice();
        Price sellPrice = sellSide.topOfBookPrice();
        while(buyPrice != null && sellPrice != null && buyPrice.greaterOrEqual(sellPrice)){
            int buyVol = buySide.topOfBookVolume();
            int sellVol = sellSide.topOfBookVolume();
            int tradeVol = Math.min(buyVol,sellVol);
            sellSide.tradeOut(sellPrice,tradeVol);
            buySide.tradeOut(buyPrice,tradeVol);
            buyPrice = buySide.topOfBookPrice();
            sellPrice = sellSide.topOfBookPrice();
        }
    }

    @Override
    public String toString() {
        return String.format("Product: %s\n%s\n%s",product,buySide.toString(),sellSide.toString());
    }
}