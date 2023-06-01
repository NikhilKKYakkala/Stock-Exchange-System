import java.text.NumberFormat;

public class Price implements Comparable<Price>{
    private final int price;
    public Price(int price) {
        this.price = price;
    }

    public boolean isNegative(){
        return price < 0;
    }

    public Price add(Price p) throws InvalidPriceOperation {
        if(p == null)
            throw new InvalidPriceOperation("Invalid");
        return new Price(price + p.price);
    }

    public Price subtract(Price p) throws InvalidPriceOperation {
        if(p == null)
            throw new InvalidPriceOperation("Invalid");
        return new Price(price - p.price);
    }

    public Price multiply(int n){
        return new Price(price * n);
    }

    public boolean greaterOrEqual(Price p) throws InvalidPriceOperation {
        if(p == null)
            throw new InvalidPriceOperation("Invalid");
        return price >= p.price;
    }

    public boolean lessOrEqual(Price p) throws InvalidPriceOperation {
        if(p == null)
            throw new InvalidPriceOperation("Invalid");
        return price <= p.price;
    }

    public boolean greaterThan(Price p) throws InvalidPriceOperation {
        if(p == null)
            throw new InvalidPriceOperation("Invalid");
        return price > p.price;
    }

    public boolean lessThan(Price p) throws InvalidPriceOperation {
        if(p == null)
            throw new InvalidPriceOperation("Invalid");
        return price < p.price;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compareTo(Price p) {
        if(p == null)
            System.out.println("Price Object passed to compare cannot be null");
        System.out.println(this.price + "-----" + p.price);
        return Math.abs(this.price - p.price);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance();
        if(price < 10)
                return "$" + nf.format(price / 100) + ".0" + Math.abs(price % 100);
        else {
            if (Math.abs(price % 100) < 10)
                return "$" + nf.format(price / 100) + '.' + Math.abs(price % 100) + '0';
            else
                return "$" + nf.format(price / 100) + '.' + Math.abs(price % 100);
        }
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
