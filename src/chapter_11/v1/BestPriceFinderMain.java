package chapter_11.v1;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author huangxunian
 * @date 2020/2/22 21:35
 */
public class BestPriceFinderMain {

    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
        String product = "myPhone27";
        execute("sequential", () -> bestPriceFinder.findPricesSequential(product));
        // execute("parallel", () -> bestPriceFinder.findPricesParallel(product));
        // execute("composed CompletableFuture", () -> bestPriceFinder.findPricesFuture(product));
        // execute("findPricesInUSDJava7", () -> bestPriceFinder.findPricesInUSDJava7(product));
        // execute("combined USD CompletableFuture", () -> bestPriceFinder.findPricesInUSD(product));
        // execute("combined USD CompletableFuture v2", () -> bestPriceFinder.findPricesInUSD2(product));
        // execute("combined USD CompletableFuture v3", () -> bestPriceFinder.findPricesInUSD3(product));
    }

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + " msecs");
    }

}
