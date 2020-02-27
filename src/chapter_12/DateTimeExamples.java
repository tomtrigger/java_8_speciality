package chapter_12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.chrono.JapaneseDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author huangxunian
 * @date 2020/2/26 19:44
 */
public class DateTimeExamples {

    private static final ThreadLocal<DateFormat> FORMAT_THREAD_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("dd-MMM-yyyy");
        }
    };

    public static void main(String[] args) {
        // useOldDate();
        // useLocalDate();
        // useTemproalAdjuster();
        // useDateFormatter();
        useTimeZone();
    }

    private static void useOldDate() {
        Date date = new Date(114, 2, 18);
        System.out.println(date);
        System.out.println(FORMAT_THREAD_LOCAL.get().format(date));
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.FEBRUARY, 18);
        System.out.println(calendar);
    }

    private static void useLocalDate() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        int year = date.getYear(); // 2014
        Month month = date.getMonth(); // MARCH
        int day = date.getDayOfMonth(); // 18
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // TUESDAY
        int len = date.lengthOfMonth(); // 31 (days in March)
        boolean leap = date.isLeapYear(); // false (not a leap year)
        System.out.println(date);

        int y = date.get(ChronoField.YEAR); // 2014
        int m = date.get(ChronoField.MONTH_OF_YEAR); // 3
        int d = date.get(ChronoField.DAY_OF_MONTH); // 18

        LocalTime time = LocalTime.of(13, 45, 20); // 13:45:20
        int hour = time.getHour(); // 13
        int minute = time.getMinute(); // 45
        int second = time.getSecond(); // 20
        System.out.println(time);

        LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20); // 2014-03-18T13:45:20
        LocalDateTime dt2 = LocalDateTime.of(date, time);
        LocalDateTime dt3 = date.atTime(13, 45, 20);
        LocalDateTime dt4 = date.atTime(time);
        LocalDateTime dt5 = time.atDate(date);
        System.out.println(dt5);

        LocalDate date1 = dt1.toLocalDate();
        System.out.println(date1);
        LocalTime time1 = dt1.toLocalTime();
        System.out.println(time1);

        Instant instant = Instant.ofEpochSecond(44 * 365 * 86400); // 2013-12-21T00:00:00Z
        Instant now = Instant.now();
        System.out.println(now);

        Duration d1 = Duration.between(LocalTime.of(13, 45, 10), time);
        Duration d2 = Duration.between(instant, now);
        System.out.println(d1.getSeconds());
        System.out.println(d2.getSeconds());

        Duration thminutes = Duration.ofMinutes(3);
        Duration threeMinutes = Duration.of(3, ChronoUnit.MINUTES);
        System.out.println(thminutes);
        System.out.println(threeMinutes.get(ChronoUnit.SECONDS));

        Period tenDays = Period.between(LocalDate.of(2014, 3, 8), LocalDate.of(2014, 3, 18));
        System.out.println(tenDays.get(ChronoUnit.DAYS));

        Period tenDay = Period.ofDays(10);
        Period threeWeeks = Period.ofWeeks(3);
        Period twoYearsSixMonthsOneDay = Period.of(2, 6, 1);
        System.out.println(tenDay);
        System.out.println(threeWeeks);
        System.out.println(twoYearsSixMonthsOneDay);

        JapaneseDate japaneseDate = JapaneseDate.from(date);
        System.out.println(japaneseDate);
    }

    private static void useTemproalAdjuster() {
        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        date = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(date);
        date = date.with(TemporalAdjusters.lastDayOfMonth());
        System.out.println(date);

        date = date.with(new NextWorkingDay());
        System.out.println(date);
        date = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(new NextWorkingDay());
        System.out.println(date);

        date = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));
        System.out.println(date);
        date = date.with(temporal -> {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) { dayToAdd = 3; }
            if (dow == DayOfWeek.SATURDAY) { dayToAdd = 2; }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        });
        System.out.println(date);

    }

    private static class NextWorkingDay implements TemporalAdjuster {

        @Override
        public Temporal adjustInto(Temporal temporal) {
            DayOfWeek dow = DayOfWeek.of(temporal.get(ChronoField.DAY_OF_WEEK));
            int dayToAdd = 1;
            if (dow == DayOfWeek.FRIDAY) { dayToAdd = 3; }
            if (dow == DayOfWeek.SATURDAY) { dayToAdd = 2; }
            return temporal.plus(dayToAdd, ChronoUnit.DAYS);
        }
    }

    private static void useDateFormatter() {
        LocalDate date = LocalDate.of(2014, 3, 18);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter chinaFormatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.CHINA);

        System.out.println(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
        System.out.println(date.format(formatter));
        System.out.println(date.format(chinaFormatter));

        DateTimeFormatter complexFormatter = new DateTimeFormatterBuilder()
                .appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ")
                .appendText(ChronoField.MONTH_OF_YEAR)
                .appendLiteral(" ")
                .appendText(ChronoField.YEAR)
                .parseCaseInsensitive()
                .toFormatter(Locale.ITALIAN);
        System.out.println(date.format(complexFormatter));
    }

    private static void useTimeZone() {
        ZoneId romeZone = ZoneId.of("Europe/Rome");
        LocalDate date = LocalDate.of(2014, Month.MARCH, 18);
        ZonedDateTime zdt1 = date.atStartOfDay(romeZone);
        System.out.println(zdt1);

        LocalDateTime dateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        ZonedDateTime zdt2 = dateTime.atZone(romeZone);
        System.out.println(zdt2);

        Instant instant = Instant.now();
        ZonedDateTime zdt3 = instant.atZone(romeZone);
        System.out.println(zdt3);

        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");
        LocalDateTime localDateTime = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45);
        OffsetDateTime dateTimeInNewYork = OffsetDateTime.of(localDateTime, newYorkOffset);
        System.out.println(dateTimeInNewYork);
    }

}
