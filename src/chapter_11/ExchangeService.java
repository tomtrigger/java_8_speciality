package chapter_11;

/**
 * @author huangxunian
 * @date 2020/2/22 20:50
 */
public class ExchangeService {

    public enum Money {
        USD(1.0), EUR(1.35387), GBP(1.69715), CAD(.92106), MIN(.07683);

        private final double rate;

        Money(double rate) {
            this.rate = rate;
        }
    }

    public static double getRate(Money source, Money destination) {
        return getRateWithDelay(source, destination);
    }

    public static double getRateWithDelay(Money source, Money destination) {
        Util.delay();
        return destination.rate / source.rate;
    }


}
