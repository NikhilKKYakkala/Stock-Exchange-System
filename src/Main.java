public class Main {
    public static void main(String[] args) throws InvalidPriceOperation {
        Price a = PriceFactory.makePrice(2348);
//        Price a = PriceFactory.makePrice(324);
//        Price a = PriceFactory.makePrice(-675);
//        Price a = PriceFactory.makePrice(986);
//        Price b = PriceFactory.makePrice("$-.89");
//        Price b = PriceFactory.makePrice("-0.75");
//        Price b = PriceFactory.makePrice("$12345.67");
//        Price b = PriceFactory.makePrice("$-1,234,567.89");
        Price b = PriceFactory.makePrice("-2349.25");
//        Price b = PriceFactory.makePrice("-2400.563");
//        boolean neg = a.isNegative();
//        System.out.println(neg);
//        Price add = a.add(null);
//        System.out.println(add.price);
//        Price sub = a.subtract(b);
//        System.out.println(sub.price);
//        Price mul = a.multiply(2);
//        System.out.println(mul.price);
//        boolean g = a.greaterOrEqual(b);
//        System.out.println(g);
//        boolean l = a.lessOrEqual(b);
//        System.out.println(l);
//        boolean gt = a.greaterThan(b);
//        System.out.println(gt);
//        boolean lt = a.lessThan(b);
//        System.out.println(lt);
//        boolean e = a.equals(b);
//        System.out.println(e);
//        int com = a.compareTo(b);
//        System.out.println(com);
        String temp = a.toString();
        System.out.println(temp);
//        int hash = a.hashCode();
//        System.out.println(hash);
        System.out.println(a);
        System.out.println(b);
    }
}