package chapter_08;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FactoryMain {

    public static void main(String[] args) {
        Produt p1 = ProductFactory.createProduct("loan");

        Supplier<Produt> loanSupplier = Loan::new;
        Produt p2 = loanSupplier.get();

        Produt p3 = ProductFactory.createPoductLambda("loan");
    }

    private static class ProductFactory {

        public static Produt createProduct(String name) {
            switch (name) {
                case "loan": return new Loan();
                case "stock": return new Stock();
                case "bond": return new Bond();
                default: throw new RuntimeException("No such product " + name);
            }
        }

        public static Produt createPoductLambda(String name) {
            Supplier<Produt> p = map.get(name);
            if (p != null) return p.get();
            throw new RuntimeException("No such product " + name);
        }

    }

    private static interface Produt {}
    private static class Loan implements Produt {}
    private static class Stock implements Produt {}
    private static class Bond implements Produt {}

    private final static Map<String, Supplier<Produt>> map = new HashMap<>();

    static {
        map.put("loan", Loan::new);
        map.put("stock", Stock::new);
        map.put("bond", Bond::new);
    }

}
