package chapter_05;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Laziness {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        numbers.stream().filter(n -> {
            System.out.println("filterng " + n);
            return n % 2 == 0;
        }).map(n -> {
            System.out.println("mapping " + n);
            return n * n;
        }).limit(2).forEach(System.out::println);
    }

}
