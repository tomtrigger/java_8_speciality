package chapter_13;

import java.util.stream.LongStream;

/**
 * @author huangxunian
 * @date 2020/3/3 17:57
 */
public class Recursion {

    public static void main(String[] args) {
        System.out.println(factorialIterative(5));
        System.out.println(factorialRecursive(5L));
        System.out.println(factorialStreams(5L));
        System.out.println(fatorialTailRecursive(5L));
    }

    public static int factorialIterative(int n) {
        int r = 1;
        for (int i = 1; i <= n; i++) {
            r *= i;
        }
        return r;
    }

    public static Long factorialRecursive(Long n) {
        return n == 1 ? 1 : n * factorialRecursive(n - 1);
    }


    public static Long factorialStreams(Long n) {
        return LongStream.rangeClosed(1, n).reduce(1, (a, b) -> a * b);
    }

    public static Long fatorialTailRecursive(Long n) {
        return factorialHelper(1L, n);
    }

    public static Long factorialHelper(Long acc, Long n) {
        return n == 1 ? acc : factorialHelper(acc * n, n - 1);
    }

}
