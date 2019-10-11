package chapter_05;

import chapter_04.Dish;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Filtering {

    public static void main(String[] args) {

        // Filtering with predicate
        List<Dish> vegetarianMenu = Dish.menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        vegetarianMenu.forEach(System.out::println);

        //Filtering unique elements
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        numbers.stream().filter(i -> i % 2 == 0).distinct().forEach(System.out::println);

        // Truncating a stream
        List<Dish> dishesLimit3 = Dish.menu.stream().filter(dish -> dish.getCalorise() > 300).limit(3).collect(Collectors.toList());;
        dishesLimit3.forEach(System.out::println);

        // Skipping elements
        List<Dish> dishesSkip2 = Dish.menu.stream().filter(dish -> dish.getCalorise() > 300).skip(2).collect(Collectors.toList());;
        dishesSkip2.forEach(System.out::println);

    }

}
