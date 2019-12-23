package chapter_10;

import java.util.Optional;

public class OperationsWithOptional {

    public static void main(String[] args) {
        System.out.println(max(Optional.of(3), Optional.of(5)));
        System.out.println(max(Optional.empty(), Optional.of(5)));
    }

    public static final Optional<Integer> max(Optional<Integer> i, Optional<Integer> j) {
        return i.flatMap(a -> j.map(b -> Math.max(a, b)));
    }

}
