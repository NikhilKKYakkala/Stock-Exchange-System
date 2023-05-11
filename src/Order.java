public class Order {

    private final String user;
    private final String product;
    private final Price price;
    private final BookSide side;
    private final String id;
    private final int originalVolume;
    private int remainingVolume;
    private int cancelledVolume;
    private int filledVolume;

    public Order(String user, String product, Price price, int originalVolume, BookSide side) throws OrderException {

        validateUser(user);
        validateProduct(product);
        validatePrice(price);
        validateOriginalVolume(originalVolume);
        
        this.user = user;
        this.product = product;
        this.price = price;
        this.side = side;
        this.id = generateId();
        this.originalVolume = originalVolume;
        this.remainingVolume = originalVolume;
        this.cancelledVolume = 0;
        this.filledVolume = 0;
    }

    private void validateUser(String user) throws OrderException{
        if (user == null || !user.matches("^[A-Za-z]{3}$")) {
            throw new OrderException("Invalid user code");
        }
    }

    private void validateProduct(String product) throws OrderException{
        if (product == null || !product.matches("^[A-Za-z0-9.]{1,5}$") || (product.contains(".") && !(product.replace(".","").length() >= 1))) { // CHeck this condition
            throw new OrderException("Invalid product symbol");
        }
    }

    private void validatePrice(Price price) throws OrderException{
        if (price == null) {
            throw new OrderException("Price cannot be null");
        }
    }

    private void validateOriginalVolume(int originalVolume) throws OrderException{
        if (originalVolume <= 0 || originalVolume >= 10000) {
            throw new OrderException("Invalid original volume");
        }
    }

    private String generateId() {
        return user + product + price.toString() + System.nanoTime();
    }

    public String getUser() {
        return user;
    }

    public String getProduct() {
        return product;
    }

    public Price getPrice() {
        return price;
    }

    public BookSide getSide() {
        return side;
    }

    public String getId() {
        return id;
    }

    public int getOriginalVolume() {
        return originalVolume;
    }

    public int getRemainingVolume() {
        return remainingVolume;
    }

    public int getCancelledVolume() {
        return cancelledVolume;
    }

    public int getFilledVolume() {
        return filledVolume;
    }

    public void setRemainingVolume(int remainingVolume) throws OrderException{
        if(remainingVolume < 0)
            throw new OrderException("Invalid remaining volume");
        this.remainingVolume = remainingVolume;
    }

    public void setCancelledVolume(int cancelledVolume) throws OrderException{
        if(cancelledVolume < 0)
            throw new OrderException("Invalid cancelled volume");
        this.cancelledVolume = cancelledVolume;
    }

    public void setFilledVolume(int filledVolume) throws OrderException{
        if(filledVolume < 0)
            throw new OrderException("Invalid filled volume");
        this.filledVolume = filledVolume;
    }

    @Override
    public String toString() {
        return String.format("%s order: %s %s at %s, Orig Vol: %d, Rem Vol: %d, Fill Vol: %d, CXL Vol: %d, ID: %s"
                ,user,side,product,price,originalVolume,remainingVolume,filledVolume,cancelledVolume,id);
    }

    public OrderDTO makeTradableDTO() {
        return new OrderDTO(this);
    }
}
