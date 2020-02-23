package chapter_11.v1;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author huangxunian
 * @date 2020/2/22 21:19
 */
public class ShopMain {

    public static void main(String[] args) {
        Shop shop = new Shop("BestShop");
        long start = System.nanoTime();
        Future<Double> future = shop.getPriceAsync("my favorite product");
        long invocationTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Invocation returned after " + invocationTime + " msecs");
        doSomethingElse();
        Double price = null;
        try {
            price = future.get();
            System.out.printf("Price is %.2f%n", price);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        long retrievalTime = ((System.nanoTime() - start) / 1_000_000);
        System.out.println("Price returned after " + retrievalTime + "msecs");
    }

    private static void doSomethingElse() {
        System.out.println("Doing something else...");
    }

}
