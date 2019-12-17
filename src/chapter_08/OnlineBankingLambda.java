package chapter_08;

import java.util.function.Consumer;

public class OnlineBankingLambda {

    public static void main(String[] args) {
        new OnlineBankingLambda().processCustomer(1337, c -> System.out.println("Hello!"));
    }

    public void processCustomer(int id, Consumer<Customer> makeCustoerHappy) {
        Customer customer = Database.getCustomerWithId(id);
        makeCustoerHappy.accept(customer);
    }

    static private class Customer{}

    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }

}
