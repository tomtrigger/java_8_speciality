package chapter_10;

import java.util.Optional;
import java.util.Properties;

public class ReadPositiveIntParam {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty("a", "5");
        props.setProperty("b", "true");
        props.setProperty("c", "-3");

        System.out.println(readDurationImperative(props, "a"));
        System.out.println(readDurationImperative(props, "b"));
        System.out.println(readDurationImperative(props, "c"));
        System.out.println(readDurationImperative(props, "d"));

        System.out.println(readDurationWithOptional(props, "a"));
        System.out.println(readDurationWithOptional(props, "b"));
        System.out.println(readDurationWithOptional(props, "c"));
        System.out.println(readDurationWithOptional(props, "d"));

    }

    public static int readDurationImperative(Properties properties, String name) {
        String value = properties.getProperty(name);
        if (value != null) {
            try {
                int i = Integer.parseInt(value);
                if (i > 0) {
                    return i;
                }
            } catch (NumberFormatException nfe) {}
        }
        return 0;
    }

    public static int readDurationWithOptional(Properties properties, String name) {
        return Optional.ofNullable(properties.getProperty(name))
                .flatMap(ReadPositiveIntParam::s2i)
                .filter(i -> i > 0).orElse(0);
    }

    public static Optional<Integer> s2i(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException nfe) {
            return Optional.empty();
        }
    }

}
