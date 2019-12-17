package chapter_08;

abstract class OnlineBanking {

    public void processCustomer(int id) {
        Customer c = Database.getCustomerWithId(id);
        makeCustomerHappy(c);
    }

    abstract void makeCustomerHappy(Customer customer);

    // dummy Customer class
    static private class Customer {}

    // dummy Customer class
    static private class Database {
        static Customer getCustomerWithId(int id) {
            return new Customer();
        }
    }

}
