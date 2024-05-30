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
        int cents = 0;
        if (stringValueIn.contains("$"))
            stringValueIn = stringValueIn.replace("$", "");
        if (stringValueIn.contains(","))
            stringValueIn = stringValueIn.replace(",", "");
        if (stringValueIn.contains("-")) {
            stringValueIn = stringValueIn.replace("-", "");
            stringValueIn = '-' + stringValueIn;
        }
        if (stringValueIn.contains(".")) {
            if(stringValueIn.split("\\.")[1].length() < 2)
                stringValueIn = stringValueIn.split("\\.")[0] + stringValueIn.split("\\.")[1] + '0';
            else
                stringValueIn = stringValueIn.split("\\.")[0] + stringValueIn.split("\\.")[1].substring(0, 2);
        }
        cents = Integer.parseInt(stringValueIn);
        return makePrice(cents);
    }
}
