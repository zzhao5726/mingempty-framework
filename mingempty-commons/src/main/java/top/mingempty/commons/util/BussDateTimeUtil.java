package top.mingempty.commons.util;

import cn.hutool.core.comparator.CompareUtil;
import cn.hutool.core.date.DatePattern;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;


/**
 * 日期时间工具类
 *
 * @author zzhao
 */
@Slf4j
public class BussDateTimeUtil {



    /*==============================最大最小时间    start===============================*/

    /**
     * 获取最大日期
     */
    public static Date bigDate() {
        return new GregorianCalendar(9999, Calendar.DECEMBER, 31).getTime();
    }

    /**
     * 获取最小日期
     */
    public static Date smallDate() {
        return new GregorianCalendar(0001, Calendar.JANUARY, 1).getTime();
    }

    /**
     * 获取最大日期
     */
    public static LocalDate bigLocalDate() {
        return DateTimeUtil.dateToLocalDate(bigDate());
    }

    /**
     * 获取最小日期
     */
    public static LocalDate smallLocalDate() {
        return DateTimeUtil.dateToLocalDate(smallDate());
    }

    /**
     * 获取最大实际
     */
    public static LocalDateTime bigLocalDateTime() {
        return DateTimeUtil.dateToLocalDateTime(bigDate());
    }

    /**
     * 获取最小实际
     */
    public static LocalDateTime smallLocalDateTime() {
        return DateTimeUtil.dateToLocalDateTime(smallDate());
    }

    /*==============================最大最小时间    end===============================*/

    /*====================日期减操作  start==============================*/

    /**
     * 获取当前日期减一天的时间
     */
    public static LocalDate minusLocalDateDays() {
        return minusLocalDateDays(1);
    }

    /**
     * 获取当前日期减指定天数的时间
     */
    public static LocalDate minusLocalDateDays(long daysToSubtract) {
        return minusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期减一周的时间
     */
    public static LocalDate minusLocalDateWeeks() {
        return minusLocalDateWeeks(1);
    }

    /**
     * 获取当前日期减指定周的时间
     */
    public static LocalDate minusLocalDateWeeks(long daysToSubtract) {
        return minusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.WEEKS);
    }

    /**
     * 获取当前日期减一月的时间
     */
    public static LocalDate minusLocalDateMonths() {
        return minusLocalDateMonths(1);
    }

    /**
     * 获取当前日期减指定月份的时间
     */
    public static LocalDate minusLocalDateMonths(long daysToSubtract) {
        return minusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.MONTHS);
    }

    /**
     * 获取当前日期减一年的时间
     */
    public static LocalDate minusLocalDateYears() {
        return minusLocalDateYears(1);
    }

    /**
     * 获取当前日期减指定年的时间
     */
    public static LocalDate minusLocalDateYears(long daysToSubtract) {
        return minusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.YEARS);
    }

    /**
     * 返回此日期的副本，并减去指定的时间
     */
    public static LocalDate minusLocalDate(LocalDate localDate, long amountToSubtract, ChronoUnit chronoUnit) {
        if (localDate == null
                || chronoUnit == null) {
            return null;
        }
        return localDate.minus(amountToSubtract, chronoUnit);
    }

    /**
     * 获取当前日期减一毫秒的时间
     */
    public static LocalDateTime minusLocalDateTimeMillis() {
        return minusLocalDateTimeMillis(1);
    }

    /**
     * 获取当前日期减指定毫秒的时间
     */
    public static LocalDateTime minusLocalDateTimeMillis(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.MILLIS);
    }

    /**
     * 获取当前日期减一秒的时间
     */
    public static LocalDateTime minusLocalDateTimeSeconds() {
        return minusLocalDateTimeSeconds(1);
    }

    /**
     * 获取当前日期减指定秒数的时间
     */
    public static LocalDateTime minusLocalDateTimeSeconds(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.SECONDS);
    }

    /**
     * 获取当前日期减一分钟的时间
     */
    public static LocalDateTime minusLocalDateTimeMinutes() {
        return minusLocalDateTimeMinutes(1);
    }

    /**
     * 获取当前日期减指定分钟数的时间
     */
    public static LocalDateTime minusLocalDateTimeMinutes(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.MINUTES);
    }

    /**
     * 获取当前日期减一小时的时间
     */
    public static LocalDateTime minusLocalDateTimeHours() {
        return minusLocalDateTimeHours(1);
    }

    /**
     * 获取当前日期减指定小时数的时间
     */
    public static LocalDateTime minusLocalDateTimeHours(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.HOURS);
    }

    /**
     * 获取当前日期减一天的时间
     */
    public static LocalDateTime minusLocalDateTimeDays() {
        return minusLocalDateTimeDays(1);
    }

    /**
     * 获取当前日期减指定天数的时间
     */
    public static LocalDateTime minusLocalDateTimeDays(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期减一周的时间
     */
    public static LocalDateTime minusLocalDateTimeWeeks() {
        return minusLocalDateTimeWeeks(1);
    }

    /**
     * 获取当前日期减指定周的时间
     */
    public static LocalDateTime minusLocalDateTimeWeeks(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.WEEKS);
    }

    /**
     * 获取当前日期减一月的时间
     */
    public static LocalDateTime minusLocalDateTimeMonths() {
        return minusLocalDateTimeMonths(1);
    }

    /**
     * 获取当前日期减指定月份的时间
     */
    public static LocalDateTime minusLocalDateTimeMonths(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.MONTHS);
    }

    /**
     * 获取当前日期减一年的时间
     */
    public static LocalDateTime minusLocalDateTimeYears() {
        return minusLocalDateTimeYears(1);
    }

    /**
     * 获取当前日期减指定年的时间
     */
    public static LocalDateTime minusLocalDateTimeYears(long daysToSubtract) {
        return minusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.YEARS);
    }

    /**
     * 返回此日期的副本，并减去指定的时间
     */
    public static LocalDateTime minusLocalDateTime(LocalDateTime localDateTime, long amountToSubtract, ChronoUnit chronoUnit) {
        if (localDateTime == null
                || chronoUnit == null) {
            return null;
        }
        return localDateTime.minus(amountToSubtract, chronoUnit);
    }

    /**
     * 获取当前日期减一毫秒的时间
     */
    public static Date minusDateMillis() {
        return minusDateMillis(1);
    }

    /**
     * 获取当前日期减指定毫秒的时间
     */
    public static Date minusDateMillis(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.MILLIS);
    }

    /**
     * 获取当前日期减一秒的时间
     */
    public static Date minusDateSeconds() {
        return minusDateSeconds(1);
    }

    /**
     * 获取当前日期减指定秒数的时间
     */
    public static Date minusDateSeconds(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.SECONDS);
    }

    /**
     * 获取当前日期减一分钟的时间
     */
    public static Date minusDateMinutes() {
        return minusDateMinutes(1);
    }

    /**
     * 获取当前日期减指定分钟数的时间
     */
    public static Date minusDateMinutes(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.MINUTES);
    }

    /**
     * 获取当前日期减一小时的时间
     */
    public static Date minusDateHours() {
        return minusDateHours(1);
    }

    /**
     * 获取当前日期减指定小时数的时间
     */
    public static Date minusDateHours(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.HOURS);
    }

    /**
     * 获取当前日期减一天的时间
     */
    public static Date minusDateDays() {
        return minusDateDays(1);
    }

    /**
     * 获取当前日期减指定天数的时间
     */
    public static Date minusDateDays(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期减一周的时间
     */
    public static Date minusDateWeeks() {
        return minusDateWeeks(1);
    }

    /**
     * 获取当前日期减指定周的时间
     */
    public static Date minusDateWeeks(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.WEEKS);
    }

    /**
     * 获取当前日期减一月的时间
     */
    public static Date minusDateMonths() {
        return minusDateMonths(1);
    }

    /**
     * 获取当前日期减指定月份的时间
     */
    public static Date minusDateMonths(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.MONTHS);
    }

    /**
     * 获取当前日期减一年的时间
     */
    public static Date minusDateYears() {
        return minusDateYears(1);
    }

    /**
     * 获取当前日期减指定年的时间
     */
    public static Date minusDateYears(long daysToSubtract) {
        return minusDate(new Date(), daysToSubtract, ChronoUnit.YEARS);
    }

    /**
     * 返回此日期的副本，并减去指定的时间
     */
    public static Date minusDate(Date date, long amountToSubtract, ChronoUnit chronoUnit) {
        if (date == null
                || chronoUnit == null) {
            return null;
        }
        return DateTimeUtil.localDateTimeToDate(minusLocalDateTime(DateTimeUtil.dateToLocalDateTime(date), amountToSubtract, chronoUnit));
    }

    /*====================日期减操作  end==============================*/

    /*====================日期加操作  start==============================*/

    /**
     * 获取当前日期加一天的时间
     */
    public static LocalDate plusLocalDateDays() {
        return plusLocalDateDays(1);
    }

    /**
     * 获取当前日期加指定天数的时间
     */
    public static LocalDate plusLocalDateDays(long daysToSubtract) {
        return plusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期加一周的时间
     */
    public static LocalDate plusLocalDateWeeks() {
        return plusLocalDateWeeks(1);
    }

    /**
     * 获取当前日期加指定周的时间
     */
    public static LocalDate plusLocalDateWeeks(long daysToSubtract) {
        return plusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.WEEKS);
    }

    /**
     * 获取当前日期加一月的时间
     */
    public static LocalDate plusLocalDateMonths() {
        return plusLocalDateMonths(1);
    }

    /**
     * 获取当前日期加指定月份的时间
     */
    public static LocalDate plusLocalDateMonths(long daysToSubtract) {
        return plusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.MONTHS);
    }

    /**
     * 获取当前日期加一年的时间
     */
    public static LocalDate plusLocalDateYears() {
        return plusLocalDateYears(1);
    }

    /**
     * 获取当前日期加指定年的时间
     */
    public static LocalDate plusLocalDateYears(long daysToSubtract) {
        return plusLocalDate(LocalDate.now(), daysToSubtract, ChronoUnit.YEARS);
    }

    /**
     * 返回此日期的副本，并加上指定的时间
     */
    public static LocalDate plusLocalDate(LocalDate localDate, long amountToSubtract, ChronoUnit chronoUnit) {
        if (localDate == null
                || chronoUnit == null) {
            return null;
        }
        return localDate.plus(amountToSubtract, chronoUnit);
    }

    /**
     * 获取当前日期加一毫秒的时间
     */
    public static LocalDateTime plusLocalDateTimeMillis() {
        return plusLocalDateTimeMillis(1);
    }

    /**
     * 获取当前日期加指定毫秒的时间
     */
    public static LocalDateTime plusLocalDateTimeMillis(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.MILLIS);
    }

    /**
     * 获取当前日期加一秒的时间
     */
    public static LocalDateTime plusLocalDateTimeSeconds() {
        return plusLocalDateTimeSeconds(1);
    }

    /**
     * 获取当前日期加指定秒数的时间
     */
    public static LocalDateTime plusLocalDateTimeSeconds(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.SECONDS);
    }

    /**
     * 获取当前日期加一分钟的时间
     */
    public static LocalDateTime plusLocalDateTimeMinutes() {
        return plusLocalDateTimeMinutes(1);
    }

    /**
     * 获取当前日期加指定分钟数的时间
     */
    public static LocalDateTime plusLocalDateTimeMinutes(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.MINUTES);
    }

    /**
     * 获取当前日期加一小时的时间
     */
    public static LocalDateTime plusLocalDateTimeHours() {
        return plusLocalDateTimeHours(1);
    }

    /**
     * 获取当前日期加指定小时数的时间
     */
    public static LocalDateTime plusLocalDateTimeHours(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.HOURS);
    }

    /**
     * 获取当前日期加一天的时间
     */
    public static LocalDateTime plusLocalDateTimeDays() {
        return plusLocalDateTimeDays(1);
    }

    /**
     * 获取当前日期加指定天数的时间
     */
    public static LocalDateTime plusLocalDateTimeDays(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期加一周的时间
     */
    public static LocalDateTime plusLocalDateTimeWeeks() {
        return plusLocalDateTimeWeeks(1);
    }

    /**
     * 获取当前日期加指定周的时间
     */
    public static LocalDateTime plusLocalDateTimeWeeks(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.WEEKS);
    }

    /**
     * 获取当前日期加一月的时间
     */
    public static LocalDateTime plusLocalDateTimeMonths() {
        return plusLocalDateTimeMonths(1);
    }

    /**
     * 获取当前日期加指定月份的时间
     */
    public static LocalDateTime plusLocalDateTimeMonths(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.MONTHS);
    }

    /**
     * 获取当前日期加一年的时间
     */
    public static LocalDateTime plusLocalDateTimeYears() {
        return plusLocalDateTimeYears(1);
    }

    /**
     * 获取当前日期加指定年的时间
     */
    public static LocalDateTime plusLocalDateTimeYears(long daysToSubtract) {
        return plusLocalDateTime(LocalDateTime.now(), daysToSubtract, ChronoUnit.YEARS);
    }

    /**
     * 返回此日期的副本，并加上指定的时间
     */
    public static LocalDateTime plusLocalDateTime(LocalDateTime localDateTime, long amountToSubtract, ChronoUnit chronoUnit) {
        if (localDateTime == null
                || chronoUnit == null) {
            return null;
        }
        return localDateTime.plus(amountToSubtract, chronoUnit);
    }

    /**
     * 获取当前日期加一毫秒的时间
     */
    public static Date plusDateMillis() {
        return plusDateMillis(1);
    }

    /**
     * 获取当前日期加指定毫秒的时间
     */
    public static Date plusDateMillis(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.MILLIS);
    }

    /**
     * 获取当前日期加一秒的时间
     */
    public static Date plusDateSeconds() {
        return plusDateSeconds(1);
    }

    /**
     * 获取当前日期加指定秒数的时间
     */
    public static Date plusDateSeconds(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.SECONDS);
    }

    /**
     * 获取当前日期加一分钟的时间
     */
    public static Date plusDateMinutes() {
        return plusDateMinutes(1);
    }

    /**
     * 获取当前日期加指定分钟数的时间
     */
    public static Date plusDateMinutes(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.MINUTES);
    }

    /**
     * 获取当前日期加一小时的时间
     */
    public static Date plusDateHours() {
        return plusDateHours(1);
    }

    /**
     * 获取当前日期加指定小时数的时间
     */
    public static Date plusDateHours(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.HOURS);
    }

    /**
     * 获取当前日期加一天的时间
     */
    public static Date plusDateDays() {
        return plusDateDays(1);
    }

    /**
     * 获取当前日期加指定天数的时间
     */
    public static Date plusDateDays(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.DAYS);
    }

    /**
     * 获取当前日期加一周的时间
     */
    public static Date plusDateWeeks() {
        return plusDateWeeks(1);
    }

    /**
     * 获取当前日期加指定周的时间
     */
    public static Date plusDateWeeks(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.WEEKS);
    }

    /**
     * 获取当前日期加一月的时间
     */
    public static Date plusDateMonths() {
        return plusDateMonths(1);
    }

    /**
     * 获取当前日期加指定月份的时间
     */
    public static Date plusDateMonths(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.MONTHS);
    }

    /**
     * 获取当前日期加一年的时间
     */
    public static Date plusDateYears() {
        return plusDateYears(1);
    }

    /**
     * 获取当前日期加指定年的时间
     */
    public static Date plusDateYears(long daysToSubtract) {
        return plusDate(new Date(), daysToSubtract, ChronoUnit.YEARS);
    }

    /**
     * 返回此日期的副本，并加上指定的时间
     */
    public static Date plusDate(Date date, long amountToSubtract, ChronoUnit chronoUnit) {
        if (date == null
                || chronoUnit == null) {
            return null;
        }
        return DateTimeUtil.localDateTimeToDate(plusLocalDateTime(DateTimeUtil.dateToLocalDateTime(date), amountToSubtract, chronoUnit));
    }

    /*====================日期加操作  end==============================*/

    /*==============================杂项    start===============================*/

    /**
     * 获取年
     *
     * @param date 指定时间
     */
    public static int getYear(Date date) {
        if (date == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)).getYear();
        } catch (Exception e) {
            log.warn("获取日期[{}]对应的年份数值异常，异常原因为：", date, e);
            return 0;
        }
    }

    /**
     * 获取月
     *
     * @param date 指定时间
     */
    public static int getMonth(Date date) {
        if (date == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)).getMonthValue();
        } catch (Exception e) {
            log.warn("获取日期[{}]对应的月份数值异常，异常原因为：", date, e);
            return 0;
        }

    }

    /**
     * 获取当前年的天
     *
     * @param date 指定时间
     */

    public static int getDayWithYear(Date date) {
        if (date == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)).getDayOfYear();
        } catch (Exception e) {
            log.warn("获取日期[{}]对应当前年的天数值异常，异常原因为：", date, e);
            return 0;
        }
    }

    /**
     * 获取当前月的天
     *
     * @param date 指定时间
     */

    public static int getDayOfMonth(Date date) {
        if (date == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)).getDayOfMonth();
        } catch (Exception e) {
            log.warn("获取日期[{}]对应当前月的天数值异常，异常原因为：", date, e);
            return 0;
        }
    }

    /**
     * 获取当前周的天
     *
     * @param date 指定时间
     */

    public static int getDayWithWeek(Date date) {
        if (date == null) {
            return 0;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)).getDayOfWeek().getValue();
        } catch (Exception e) {
            log.warn("获取日期[{}]对应当前周的天数值异常，异常原因为：", date, e);
            return 0;
        }
    }

    /**
     * 获取当前月份的第一天
     */
    public static String getDayStrWithMonthFirst() {
        return DateTimeUtil.formatLocalDate(getDayWithMonthFirst(LocalDate.now()), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取当前月份的第一天
     */
    public static LocalDate getDayWithMonthFirst() {
        return getDayWithMonthFirst(LocalDate.now());
    }

    /**
     * 获取指定时间所在月份的第一天
     *
     * @param date 指定时间
     */
    public static LocalDate getDayWithMonthFirst(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return getDayWithMonthFirst(Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)));
        } catch (Exception e) {
            log.warn("获取指定时间Date[{}]所在月份的第一天异常，异常原因为：", date, e);
            return null;
        }
    }

    /**
     * 获取指定时间所在月份的第一天
     *
     * @param localDate 指定时间
     */
    public static LocalDate getDayWithMonthFirst(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        try {
            return localDate.with(TemporalAdjusters.firstDayOfMonth());
        } catch (Exception e) {
            log.warn("获取指定时间LocalDate[{}]所在月份的第一天异常，异常原因为：", localDate, e);
            return null;
        }

    }

    /**
     * 获取指定时间所在月份的第一天
     *
     * @param localDateTime 指定时间
     */
    public static LocalDate getDayWithMonthFirst(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.localDateTimeToLocalDate(localDateTime))
                    .with(TemporalAdjusters.firstDayOfMonth());
        } catch (Exception e) {
            log.warn("获取指定时间LocalDateTime[{}]所在月份的第一天异常，异常原因为：", localDateTime, e);
            return null;
        }
    }

    /**
     * 获取当前月份的最后一天
     */
    public static String getDayStrWithMonthEnd() {
        return DateTimeUtil.formatLocalDate(getDayWithMonthEnd(LocalDate.now()), DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取当前月份的最后一天
     */
    public static LocalDate getDayWithMonthEnd() {
        return getDayWithMonthEnd(LocalDate.now());
    }

    /**
     * 获取指定时间所在月份的最后一天
     *
     * @param date 指定时间
     */
    public static LocalDate getDayWithMonthEnd(Date date) {
        if (date == null) {
            return null;
        }
        try {
            return getDayWithMonthEnd(Objects.requireNonNull(DateTimeUtil.dateToLocalDate(date)));
        } catch (Exception e) {
            log.warn("获取指定时间Date[{}]所在月份的最后一天异常，异常原因为：", date, e);
            return null;
        }
    }

    /**
     * 获取指定时间所在月份的最后一天
     *
     * @param localDate 指定时间
     */
    public static LocalDate getDayWithMonthEnd(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        try {
            return localDate.with(TemporalAdjusters.lastDayOfMonth());
        } catch (Exception e) {
            log.warn("获取指定时间LocalDate[{}]所在月份的最后一天异常，异常原因为：", localDate, e);
            return null;
        }

    }

    /**
     * 获取指定时间所在月份的最后一天
     *
     * @param localDateTime 指定时间
     */
    public static LocalDate getDayWithMonthEnd(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        try {
            return Objects.requireNonNull(DateTimeUtil.localDateTimeToLocalDate(localDateTime))
                    .with(TemporalAdjusters.lastDayOfMonth());
        } catch (Exception e) {
            log.warn("获取指定时间LocalDateTime[{}]所在月份的最后一天异常，异常原因为：", localDateTime, e);
            return null;
        }
    }

    /**
     * 比较传入的两个日期，返回日期最大
     *
     * @param firstDate   第一个日期
     * @param sencondDate 第二个日期
     */
    public static Date getMaxDate(Date firstDate, Date sencondDate) {
        return CompareUtil.compare(firstDate, sencondDate) >= 0
                ? firstDate : sencondDate;
    }

    /**
     * 比较传入的两个日期，返回日期最大
     *
     * @param firstLocalDate   第一个日期
     * @param sencondLocalDate 第二个日期
     */
    public static LocalDate getMaxLocalDate(LocalDate firstLocalDate, LocalDate sencondLocalDate) {
        return CompareUtil.compare(firstLocalDate, sencondLocalDate) >= 0
                ? firstLocalDate : sencondLocalDate;
    }

    /**
     * 比较传入的两个日期，返回日期最大
     *
     * @param firstLocalDateTime   第一个日期
     * @param sencondLocalDateTime 第二个日期
     */
    public static LocalDateTime getMaxLocalDateTime(LocalDateTime firstLocalDateTime, LocalDateTime sencondLocalDateTime) {
        return CompareUtil.compare(firstLocalDateTime, sencondLocalDateTime) >= 0
                ? firstLocalDateTime : sencondLocalDateTime;
    }

    /**
     * 传入两个日期，返回最小的那个日期
     *
     * @param firstDate   第一个日期
     * @param sencondDate 第二个日期
     */
    public static Date getMinDate(Date firstDate, Date sencondDate) {
        return CompareUtil.compare(firstDate, sencondDate) >= 0
                ? sencondDate : firstDate;
    }

    /**
     * 传入两个日期，返回最小的那个日期
     *
     * @param firstLocalDate   第一个日期
     * @param sencondLocalDate 第二个日期
     */
    public static LocalDate getMinLocalDate(LocalDate firstLocalDate, LocalDate sencondLocalDate) {
        return CompareUtil.compare(firstLocalDate, sencondLocalDate) >= 0
                ? sencondLocalDate : firstLocalDate;
    }

    /**
     * 传入两个日期，返回最小的那个日期
     *
     * @param firstLocalDateTime   第一个日期
     * @param sencondLocalDateTime 第二个日期
     */
    public static LocalDateTime getMinLocalDateTime(LocalDateTime firstLocalDateTime,
                                                    LocalDateTime sencondLocalDateTime) {
        return CompareUtil.compare(firstLocalDateTime, sencondLocalDateTime) >= 0
                ? sencondLocalDateTime : firstLocalDateTime;
    }

    /**
     * 获取毫秒时间差。时间格式为yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 毫秒时间差
     */
    public static long durationForMillisecond(String startTime, String endTime) {
        return durationForMillisecond(startTime, endTime, DatePattern.NORM_DATETIME_MS_PATTERN);
    }

    /**
     * 获取指定格式的字符串时间相差的毫秒时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param formatter 日期格式
     * @return 毫秒时间差
     */
    public static long durationForMillisecond(String startTime, String endTime, String formatter) {
        try {
            return durationForMillisecond(DateTimeUtil.parseLocalDateTime(startTime, formatter), DateTimeUtil.parseLocalDateTime(endTime, formatter));
        } catch (Exception e) {
            log.warn("获取指定格式的字符串时间相差的毫秒时间差异常，startTime[{}],endTime[{}],formatter[{}],异常原因为:",
                    startTime, endTime, formatter, e);
            return 0;
        }

    }

    /**
     * 获取两个指定时间Date的时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 毫秒时间差
     */
    public static long durationForMillisecond(Date startTime, Date endTime) {
        try {
            return durationForMillisecond(DateTimeUtil.dateToLocalDateTime(startTime), DateTimeUtil.dateToLocalDateTime(endTime));
        } catch (Exception e) {
            log.warn("获取两个指定时间Date的时间差异常，startTime[{}],endTime[{}],异常原因为:",
                    startTime, endTime, e);
            return 0;
        }
    }

    /**
     * 获取两个指定时间LocalDate的时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 毫秒时间差
     */
    public static long durationForMillisecond(LocalDate startTime, LocalDate endTime) {
        try {
            return durationForMillisecond(DateTimeUtil.localDateToLocalDateTime(startTime), DateTimeUtil.localDateToLocalDateTime(endTime));
        } catch (Exception e) {
            log.warn("获取两个指定时间LocalDate的时间差异常，startTime[{}],endTime[{}],异常原因为:",
                    startTime, endTime, e);
            return 0;
        }
    }

    /**
     * 获取两个指定时间LocalDateTime的时间差
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return 毫秒时间差
     */
    public static long durationForMillisecond(LocalDateTime startTime, LocalDateTime endTime) {
        try {
            long start = DateTimeUtil.localDateTimeToLong(startTime);
            long end = DateTimeUtil.localDateTimeToLong(endTime);
            return end - start;
        } catch (Exception e) {
            log.warn("获取两个指定时间LocalDateTime的时间差异常，startTime[{}],endTime[{}],异常原因为:",
                    startTime, endTime, e);
            return 0;
        }
    }


    /**
     * 计算两个日期之前的自然年数
     *
     * @param fromLocalDate 来源时间
     * @param toLocalDate   目标时间
     */
    public static int calculateYearCount(LocalDate fromLocalDate, LocalDate toLocalDate) {
        return Period.between(fromLocalDate, toLocalDate).getYears();
    }

    /**
     * 计算两个日期之前的自然年数
     *
     * @param fromLocalDateTime 来源时间
     * @param toLocalDateTime   目标时间
     */
    public static int calculateYearCount(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        return calculateYearCount(DateTimeUtil.localDateTimeToLocalDate(fromLocalDateTime), DateTimeUtil.localDateTimeToLocalDate(toLocalDateTime));
    }

    /**
     * 计算两个日期之前的自然年数
     *
     * @param fromDate 来源时间
     * @param toDate   目标时间
     */
    public static int calculateYearCount(Date fromDate, Date toDate) {
        return calculateYearCount(DateTimeUtil.dateToLocalDate(fromDate), DateTimeUtil.dateToLocalDate(toDate));
    }

    /**
     * 计算两个日期之前的自然月数
     *
     * @param fromLocalDate 来源时间
     * @param toLocalDate   目标时间
     */
    public static int calculateMonthCount(LocalDate fromLocalDate, LocalDate toLocalDate) {
        return Period.between(fromLocalDate, toLocalDate).getMonths();
    }

    /**
     * 计算两个日期之前的自然月数
     *
     * @param fromLocalDateTime 来源时间
     * @param toLocalDateTime   目标时间
     */
    public static int calculateMonthCount(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        return calculateMonthCount(DateTimeUtil.localDateTimeToLocalDate(fromLocalDateTime), DateTimeUtil.localDateTimeToLocalDate(toLocalDateTime));
    }

    /**
     * 计算两个日期之前的自然月数
     *
     * @param fromDate 来源时间
     * @param toDate   目标时间
     */
    public static int calculateMonthCount(Date fromDate, Date toDate) {
        return calculateMonthCount(DateTimeUtil.dateToLocalDate(fromDate), DateTimeUtil.dateToLocalDate(toDate));
    }

    /**
     * 计算两个日期之前的自然天数
     *
     * @param fromLocalDate 来源时间
     * @param toLocalDate   目标时间
     */
    public static int calculateDayCount(LocalDate fromLocalDate, LocalDate toLocalDate) {
        return Period.between(fromLocalDate, toLocalDate).getDays();
    }

    /**
     * 计算两个日期之前的自然天数
     *
     * @param fromLocalDateTime 来源时间
     * @param toLocalDateTime   目标时间
     */
    public static int calculateDayCount(LocalDateTime fromLocalDateTime, LocalDateTime toLocalDateTime) {
        return calculateDayCount(DateTimeUtil.localDateTimeToLocalDate(fromLocalDateTime), DateTimeUtil.localDateTimeToLocalDate(toLocalDateTime));
    }

    /**
     * 计算两个日期之前的自然天数
     *
     * @param fromDate 来源时间
     * @param toDate   目标时间
     */
    public static int calculateDayCount(Date fromDate, Date toDate) {
        return calculateDayCount(DateTimeUtil.dateToLocalDate(fromDate), DateTimeUtil.dateToLocalDate(toDate));
    }

    /**
     * 判断是否是同一年以及同一个月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否是同一年以及同一个月
     */
    public static boolean isSameYearMonth(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();

        cal1.setTime(date1);
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
    }

    /**
     * 判断是否是同一年以及同一个月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否是同一年以及同一个月
     */
    public static boolean isSameYearMonth(LocalDate date1, LocalDate date2) {
        return date1.getYear() == date2.getYear() &&
                date1.getMonthValue() == date2.getMonthValue();
    }

    /**
     * 判断是否是同一年以及同一个月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return 是否是同一年以及同一个月
     */
    public static boolean isSameYearMonth(LocalDateTime date1, LocalDateTime date2) {
        return date1.getYear() == date2.getYear() &&
                date1.getMonthValue() == date2.getMonthValue();
    }



    /*==============================杂项    end===============================*/

    /*====================业务计算  start==============================*/


    /**
     * 根据出生日期计算当前年龄
     *
     * @param birthLocalDateTime 出生日期
     */
    public static int calculatedAge(LocalDateTime birthLocalDateTime) {
        return calculatedAge(DateTimeUtil.localDateTimeToDate(birthLocalDateTime));
    }

    /**
     * 计算年龄
     *
     * @param birthLocalDateTime 出生日期
     * @param endLocalDateTime   投保日期
     */
    public static int calculatedAge(LocalDateTime birthLocalDateTime, LocalDateTime endLocalDateTime) {
        return calculatedAge(DateTimeUtil.localDateTimeToDate(birthLocalDateTime), DateTimeUtil.localDateTimeToDate(endLocalDateTime));
    }

    /**
     * 根据出生日期计算当前年龄
     *
     * @param birthLocalDate 出生日期
     */
    public static int calculatedAge(LocalDate birthLocalDate) {
        return calculatedAge(DateTimeUtil.localDateToDate(birthLocalDate));
    }

    /**
     * 计算年龄
     *
     * @param birthLocalDate 出生日期
     * @param endLocalDate   投保日期
     */
    public static int calculatedAge(LocalDate birthLocalDate, LocalDate endLocalDate) {
        // 参数校验：确保出生日期不晚于结束日期
        if (birthLocalDate.isAfter(endLocalDate)) {
            throw new IllegalArgumentException("Birth date cannot be after end date.");
        }

        // 使用java.time.Period计算年龄
        Period age = Period.between(birthLocalDate, endLocalDate);

        return age.getYears();
    }

    /**
     * 根据出生日期计算当前年龄
     *
     * @param birthDay 出生日期
     */
    public static int calculatedAge(Date birthDay) {
        return calculatedAge(birthDay, new Date());
    }

    /**
     * 计算年龄
     *
     * @param birthDay 出生日期
     * @param endDate  指定计算时间
     */
    public static int calculatedAge(Date birthDay, Date endDate) {
        return calculatedAge(Objects.requireNonNull(DateTimeUtil.dateToLocalDate(birthDay)), Objects.requireNonNull(DateTimeUtil.dateToLocalDate(endDate)));
    }
    /*====================业务计算  end==============================*/

}
