package chapter_06;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

public class Summarizing {

    public static void main(String[] args) {
        System.out.println("Nr. of dishes: " + howManyDishes());
        System.out.println("The most caloric dish is: " + findMostCaloricDish());
        System.out.println("The most caloric dish is: " + findMostCaloricDishUsingCompator());
        System.out.println("Total calories in menu: " + calculateToCalories());
        System.out.println("Average calories in menu:" + calculateAverageCalories());
        System.out.println("Menu statistics: " + calculateMenuStatistics());
        System.out.println("Short menu: " + getShortMenu());
        System.out.println("Short menu comma separated: " + getShortMenuCommaSearated());
    }

    public static long howManyDishes() {
        return Dish.menu.stream().collect(Collectors.counting());
    }

    public static Dish findMostCaloricDish() {
        return Dish.menu.stream().collect(Collectors.reducing(
                (d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).get();
    }

    public static Dish findMostCaloricDishUsingCompator() {
        Comparator<Dish> dishCaloriesComparator = Comparator.comparingInt(Dish::getCalories);
        BinaryOperator<Dish> moreCaloricOf = BinaryOperator.maxBy(dishCaloriesComparator);
        return Dish.menu.stream().collect(Collectors.reducing(moreCaloricOf)).get();
    }

    public static int calculateToCalories() {
        return Dish.menu.stream().collect(Collectors.summingInt((Dish d1) -> d1.getCalories()));
    }

    public static double calculateAverageCalories() {
        return Dish.menu.stream().collect(Collectors.averagingDouble(Dish::getCalories));
    }

    public static IntSummaryStatistics calculateMenuStatistics() {
        return Dish.menu.stream().collect(Collectors.summarizingInt(Dish::getCalories));
    }

    public static String getShortMenu() {
        return Dish.menu.stream().map(Dish::getName).collect(Collectors.joining());
    }

    public static String getShortMenuCommaSearated() {
        return Dish.menu.stream().map(Dish::getName).collect(Collectors.joining(", "));
    }

}
