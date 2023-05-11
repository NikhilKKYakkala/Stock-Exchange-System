public class OrderDTO {
    public final String user;
    public final String product;
    public final Price price;
    public final BookSide side;
    public final String id;
    public final int originalVolume;
    public final int remainingVolume;
    public final int cancelledVolume;
    public final int filledVolume;

    public OrderDTO(Order order) {
        this.user = order.getUser();
        this.product = order.getProduct();
        this.price = order.getPrice();
        this.side = order.getSide();
        this.id = order.getId();
        this.originalVolume = order.getOriginalVolume();
        this.remainingVolume = order.getRemainingVolume();
        this.cancelledVolume = order.getCancelledVolume();
        this.filledVolume = order.getFilledVolume();
    }

    @Override
    public String toString() {
        return String.format("%s order: %s %s at %s, Orig Vol: %d, Rem Vol: %d, Fill Vol: %d, CXL Vol: %d, ID: %s"
                ,user,side,product,price,originalVolume,remainingVolume,filledVolume,cancelledVolume,id);
    }
}
