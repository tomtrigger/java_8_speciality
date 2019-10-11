package chapter_05;

import chapter_04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Reducing {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(3, 4, 5, 1, 2);
        Integer sum = numbers.stream().reduce(0, (a, b) -> a + b);
        System.out.println(sum);

        Integer sum2 = numbers.stream().reduce(0, Integer::max);
        System.out.println(sum2);

        Integer max = numbers.stream().reduce(0, (a, b) -> Integer.max(a, b));
        System.out.println(max);

        Optional<Integer> min = numbers.stream().reduce(Integer::min);
        min.ifPresent(System.out::println);

        Integer calories = Dish.menu.stream()
                .map(Dish::getCalorise)
                .reduce(0, Integer::sum);
        System.out.println("Number of calories:" + calories);
    }

}
