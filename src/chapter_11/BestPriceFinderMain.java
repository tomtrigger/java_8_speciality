package chapter_11;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author huangxunian
 * @date 2020/2/20 22:10
 */
public class BestPriceFinderMain {

    private static BestPriceFinder bestPriceFinder = new BestPriceFinder();

    public static void main(String[] args) {
        // execute("sequential", () -> bestPriceFinder.findPricesSequential("myPhone27"));
        // execute("parallel", () -> bestPriceFinder.findPricesParallel("myPhone27"));
        //execute("Future", () -> bestPriceFinder.findPricesFuture("myPhone27"));
        bestPriceFinder.printPricesStream("myPhone27");
    }

    private static void execute(String msg, Supplier<List<String>> s) {
        long start = System.nanoTime();
        System.out.println(s.get());
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println(msg + " done in " + duration + "msecs");
    }

}
