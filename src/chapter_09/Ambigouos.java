package chapter_09;

public class Ambigouos {

    public static void main(String[] args) {
        new C().hello();
    }

    static interface A {
        public default void hello() {
            System.out.println("Hello from A");
        }
    }

    static interface B {
        public default void hello() {
            System.out.println("Hello from B");
        }
    }

    static class C implements A, B {

        @Override
        public void hello() {
            A.super.hello();
        }
    }

}
