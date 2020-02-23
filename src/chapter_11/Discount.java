package chapter_11;

/**
 * @author huangxunian
 * @date 2020/2/20 19:22
 */
public class Discount {

    public enum Code {

        NONE(0), SILVER(5), GOLD(10), PLATINUM(15), DIAMOND(20);

        private final int percentage;

        Code(int percentage) {
            this.percentage = percentage;
        }
    }

    public static String applyDiscount(Quote quote) {
        return quote.getShopName() + " price is " + Discount.apply(quote.getPrice(), quote.getDiscountCode());
    }

    public static double apply(double price, Code code) {
        Util.delay();
        return Util.format(price * (100 - code.percentage) / 100);
    }

}
