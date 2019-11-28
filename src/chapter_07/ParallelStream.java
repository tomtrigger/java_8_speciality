package chapter_07;

import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class ParallelStream {



    public static void main(String[] args) {
        System.out.println("iterativeSum:" + excute(ParallelStream::iterativeSum));
        System.out.println("sequentialSum:" + excute(ParallelStream::sequentialSum));
        System.out.println("parallelSum:" + excute(ParallelStream::parallelSum));
        System.out.println("rangedSum:" + excute(ParallelStream::rangedSum));
        System.out.println("parallelRangedSum:" + excute(ParallelStream::parallelRangedSum));
        System.out.println("sideEffectSum:" + excute(ParallelStream::sideEffectSum));
        System.out.println("diseEffectParallelSum:" + excute(ParallelStream::diseEffectParallelSum));
    }

    public static long iterativeSum(long n) {
        long result = 0;
        for (int i = 0; i <= n; i++) {
            result += i;
        }
        return result;
    }

    public static long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).reduce(Long::sum).get();
    }

    public static long parallelSum(long n) {
        return Stream.iterate(1L, i -> i + 1).limit(n).parallel().reduce(Long::sum).get();
    }

    public static long rangedSum(long n) {
        return LongStream.rangeClosed(0, n).reduce(Long::sum).getAsLong();
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(0, n).parallel().reduce(Long::sum).getAsLong();
    }

    public static long sideEffectSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(0, n).forEach(accumulator::add);
        return accumulator.total;
    }

    public static long diseEffectParallelSum(long n) {
        Accumulator accumulator = new Accumulator();
        LongStream.rangeClosed(0, n).parallel().forEach(accumulator::add);
        return accumulator.total;
    }

    public static long excute(Function<Long, Long> func) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {
            long startTime = System.nanoTime();
            Long result = func.apply(50_000_000L);
            long duration = (System.nanoTime() - startTime) / 1_000_000;
            if (duration < fastest) fastest = duration;
            System.out.println("done in " + result);
        }
        return fastest;
    }

    public static class Accumulator {
        public long total = 0;
        public void add(long value) {
            total += value;
        }
    }

}
