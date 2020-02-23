package chapter_11;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

/**
 * @author huangxunian
 * @date 2020/2/20 21:22
 */
public class AsyncShop {

    private final String name;
    private final Random random;

    public AsyncShop(String name) {
        this.name = name;
        random = new Random(name.charAt(0) * name.charAt(1) * name.charAt(2));
    }

    public Future<Double> getPrice(String product) {
        /*CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                double price = calculatePrice(product);
                futurePrice.complete(price);
            } catch (Exception e) {
                futurePrice.completeExceptionally(e);
            }
        }).start();
        return futurePrice;*/
        return CompletableFuture.supplyAsync(() -> calculatePrice(product));
    }

    private double calculatePrice(String product) {
        Util.delay();
        /*if (true) {
            throw new RuntimeException("product not available");
        }*/
        return Util.format(random.nextDouble() * product.charAt(0) + product.charAt(1));
    }


}
