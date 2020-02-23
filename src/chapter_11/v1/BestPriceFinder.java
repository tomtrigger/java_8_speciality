package chapter_11.v1;

import chapter_11.ExchangeService;
import chapter_11.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author huangxunian
 * @date 2020/2/22 21:36
 */
public class BestPriceFinder {

    private final List<Shop> shops = Arrays.asList(new Shop("BestPrice"),
            new Shop("LetsSaveBig"),
            new Shop("MyFavoriteShop"),
            new Shop("BuyItAll"),
            new Shop("ShopEasy"));

    private final ExecutorService executor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(400), new ThreadPoolExecutor.CallerRunsPolicy());

    public List<String> findPricesSequential(String product) {
        return shops.stream().map(shop -> shop.getName() + " price is " + shop.getPrice(product))
                .collect(Collectors.toList());
    }

    public List<String> findPricesParallel(String product) {
        return shops.parallelStream().map(shop -> shop.getName() + " price is " + shop.getPrice(product)).collect(Collectors.toList());
    }

    public List<String> findPricesFuture(String product) {
        List<CompletableFuture<String>> priceFutures = shops.stream().map(shop -> CompletableFuture.supplyAsync(() -> shop.getName() + "price is " + shop.getPrice(product), executor))
                .collect(Collectors.toList());
        List<String> prices = priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        return prices;
    }

    public List<String> findPricesInUSD(String product) {
        List<CompletableFuture<Double>> priceFutures = new ArrayList<>();
        for (Shop shop : shops) {
            CompletableFuture<Double> futurePriceInUSD = CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor).thenCombine(
                    CompletableFuture.supplyAsync(() -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
                    (price, rate) -> Util.format(price * rate)
            );
            priceFutures.add(futurePriceInUSD);
        }

        List<String> prices = priceFutures.stream().map(CompletableFuture::join).map(price -> "price is " + price).collect(Collectors.toList());
        return prices;
    }

    public List<String> findPricesInUSDJava7(String product) {
        List<Future<Double>> priceFutures = new ArrayList<>();
        for (Shop shop : shops) {
            final Future<Double> futureRate = executor.submit(new Callable<Double>() {
                @Override
                public Double call() {
                    return ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD);
                }
            });
            Future<Double> futurePriceInUSD = executor.submit(new Callable<Double>() {
                @Override
                public Double call() {
                    try {
                        return shop.getPrice(product) * futureRate.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            priceFutures.add(futurePriceInUSD);
        }

        List<String> prices = new ArrayList<>();
        for (Future<Double> priceFuture : priceFutures) {
            try {
                prices.add("price is " + priceFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return prices;
    }

    public List<String> findPricesInUSD2(String product) {
        List<CompletableFuture<String>> priceFutures = new ArrayList<>();
        for (Shop shop : shops) {
            CompletableFuture<String> futurePriceInUSD = CompletableFuture.supplyAsync(() -> shop.getPrice(product))
                    .thenCombine(CompletableFuture.supplyAsync(
                            () -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD)),
                            (price, rate) -> Util.format(price * rate))
                    .thenApply(price -> shop.getName() + " price is " + price);
            priceFutures.add(futurePriceInUSD);
        }
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public List<String> findPricesInUSD3(String product) {
        Stream<CompletableFuture<String>> futureStream = shops.stream()
                .map(shop -> CompletableFuture.supplyAsync(() -> shop.getPrice(product), executor)
                .thenCombine(CompletableFuture.supplyAsync(() -> ExchangeService.getRate(ExchangeService.Money.EUR, ExchangeService.Money.USD), executor),
                        (price, rate) -> Util.format(price * rate))
                .thenApply(price -> shop.getName() + " price is " + price));
        // 我们应该将CompletableFutures收集到一个列表中，以便异步操作在“joined”之前被触发
        List<CompletableFuture<String>> priceFutures = futureStream.collect(Collectors.toList());
        return priceFutures.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

}
