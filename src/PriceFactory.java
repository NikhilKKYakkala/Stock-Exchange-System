import java.util.HashMap;
import java.util.Map;

public class PriceFactory {

    private static final Map<Integer,Price> pricevalues = new HashMap<>();
    public static Price makePrice(int price){
        Price cents = null;
        if(!pricevalues.containsKey(price)){
            cents = new Price(price);
            pricevalues.put(price,cents);
            return cents;
        }
        else
            return pricevalues.get(price);
    }

    public static Price makePrice(String stringValueIn){
        int a = 0;
        if (stringValueIn.contains("$"))
            stringValueIn = stringValueIn.replace("$", "");
        if (stringValueIn.contains(","))
            stringValueIn = stringValueIn.replace(",", "");
        if (stringValueIn.contains("-")) {
            stringValueIn = stringValueIn.replace("-", "");
            stringValueIn = '-' + stringValueIn;
        }
        if (stringValueIn.contains(".")) {
            double d = Double.parseDouble(stringValueIn);
            d = Math.floor(d * 100);
            a = (int) d;
        }
        else
            a = Integer.parseInt(stringValueIn);
        return makePrice(a);
    }
}
