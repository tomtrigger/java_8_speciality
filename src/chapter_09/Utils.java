package chapter_09;

import java.util.List;

public class Utils {

    public static void paint(List<Resizable> list) {
        list.forEach(resizable -> {
            resizable.setAbsoluteSize(42, 42);});
    }

}
