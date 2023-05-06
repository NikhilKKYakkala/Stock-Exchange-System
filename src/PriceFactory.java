public class PriceFactory {

    public static Price makePrice(int price){
        Price cents = new Price(price);
        return cents;
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
