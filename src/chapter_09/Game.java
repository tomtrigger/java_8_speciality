package chapter_09;

import java.util.Arrays;
import java.util.List;

public class Game {

    public static void main(String[] args) {
        List<Resizable> resizables = Arrays.asList(new Square(), new Triangle(), new Ellipse());
        Utils.paint(resizables);
    }

}
