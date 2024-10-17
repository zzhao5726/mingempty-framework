package top.mingempty.commons.util;

import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.date.format.FastDateParser;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.domain.other.DatePattern;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.ParsePosition;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * 日期时间工具类
 *
 * @author zzhao
 */
@Slf4j
public class DateTimeUtil {

    public final static String[] PARSE_PATTERNS;

    static {
        List<Field> declaredFieldsAll = ReflectionUtil.getDeclaredFieldsAll(DatePattern.class);
        PARSE_PATTERNS = declaredFieldsAll.stream()
                .map(field -> {
                    if (String.class.equals(field.getType())) {
                        int modifiers = field.getModifiers();
                        // 判断该属性是否是public final static 类型的
                        // 如果想取其它的,具体可以参考 Modifier 这个类里面的修饰符解码
                        if ((Modifier.isPublic(modifiers)
                                && Modifier.isStatic(modifiers)
                                && Modifier.isFinal(modifiers))) {
                            try {
                                return (String) field.get(null);
                            } catch (IllegalAccessException e) {
                                log.warn("解析反序列化");
                                throw new RuntimeException(e);
                            }
                        }
                    }
                    return "";
                })
                .filter(StrUtil::isNotEmpty)
                .toArray(String[]::new);
    }
    /*==============================日期转换    start===============================*/

    /**
     * 万能转换时间
     *
     * @param strDate 要转换为时间格式的字符串
     */
    public static Date parseAnyDate(String strDate) {

        if (strDate == null || PARSE_PATTERNS == null) {
            throw new IllegalArgumentException("Date and Patterns must not be null");
        }

        final TimeZone tz = TimeZone.getDefault();
        final Locale lcl = Locale.getDefault();
        final ParsePosition pos = new ParsePosition(0);
        final Calendar calendar = Calendar.getInstance(tz, lcl);
        calendar.setLenient(true);
        for (final String parsePattern : PARSE_PATTERNS) {
            final FastDateParser fdp = new FastDateParser(parsePattern, tz, lcl);
            calendar.clear();
            try {
                if (fdp.parse(strDate, pos, calendar) && pos.getIndex() == strDate.length()) {
                    return calendar.getTime();
                }
            } catch (final IllegalArgumentException ignore) {
                // leniency is preventing calendar from being set
            }
            pos.setIndex(0);
        }
        log.warn("Unable to parse the date:[{}]", strDate);
        return null;


    }

    /**
     * 万能转换时间
     *
     * @param strDate 要转换为时间格式的字符串
     */
    public static LocalDate parseAnyLocalDate(String strDate) {
        return dateToLocalDate(parseAnyDate(strDate));
    }

    /**
     * 万能转换时间
     *
     * @param strDate 要转换为时间格式的字符串
     */
    public static LocalDateTime parseAnyLocalDateTime(String strDate) {
        return dateToLocalDateTime(parseAnyDate(strDate));
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为Date日期格式
     *
     * @param strDate 即将转换的字符串日期
     */
    public static Date parseDate(String strDate) {
        return parseDate(strDate, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将字符串按照指定格式转换为Date日期格式
     *
     * @param strDate   即将转换的字符串日期
     * @param formatter 指定日期转换格式
     */
    public static Date parseDate(String strDate, String formatter) {
        try {
            return FastDateFormat.getInstance(formatter).parse(strDate);
        } catch (Exception e) {
            log.warn("将字符串按照指定格式转换为Date日期格式，即将转换的时间为:[{}]，指定的日期转换格式为:[{}]，异常原因为:",
                    strDate, formatter, e);
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd格式字符串转换为LocalDate日期格式
     *
     * @param strDate 即将转换的字符串日期
     */
    public static LocalDate parseLocalDate(String strDate) {
        return parseLocalDate(strDate, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 将字符串按照指定格式转换为LocalDate日期格式
     *
     * @param strDate   即将转换的字符串日期
     * @param formatter 指定日期转换格式
     */
    public static LocalDate parseLocalDate(String strDate, String formatter) {
        try {
            return LocalDate.parse(strDate, DateTimeFormatter.ofPattern(formatter));
        } catch (Exception e) {
            log.warn("将字符串按照指定格式转换为LocalDate日期格式，即将转换的时间为:[{}]，指定的日期转换格式为:[{}]，异常原因为:",
                    strDate, formatter, e);
            return null;
        }
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss格式字符串转换为LocalDateTime日期格式
     *
     * @param strDate 即将转换的字符串日期
     */
    public static LocalDateTime parseLocalDateTime(String strDate) {
        return parseLocalDateTime(strDate, DatePattern.NORM_DATETIME_PATTERN);

    }

    /**
     * 将字符串按照指定格式转换为LocalDateTime日期格式
     *
     * @param strDate   即将转换的字符串日期
     * @param formatter 指定日期转换格式
     */
    public static LocalDateTime parseLocalDateTime(String strDate, String formatter) {
        try {
            return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(formatter));
        } catch (Exception e) {
            log.warn("将字符串按照指定格式转换为LocalDateTime日期格式，即将转换的时间为:[{}]，指定的日期转换格式为:[{}]，异常原因为:",
                    strDate, formatter, e);
            return null;
        }
    }

    /**
     * 将日期Date转换为为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param date 日期
     */
    public static String formatDate(Date date) {
        return formatDate(date, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将日期Date按照指定格式转换为字符串
     *
     * @param date      日期
     * @param formatter 指定日期转换格式
     */
    public static String formatDate(Date date, String formatter) {
        if (date == null) {
            return null;
        }
        try {
            return FastDateFormat.getInstance(formatter).format(date);
        } catch (Exception e) {
            log.warn("将日期Date按照指定格式转换为字符串，即将转换的时间为:[{}]，指定的日期转换格式为:[{}]，异常原因为:",
                    date, formatter, e);
            return null;
        }
    }

    /**
     * 将日期LocalDate转换为为yyyy-MM-dd格式的字符串
     *
     * @param localDate 日期
     */
    public static String formatLocalDate(LocalDate localDate) {
        return formatLocalDate(localDate, DatePattern.NORM_DATE_PATTERN);
    }

    /**
     * 将日期LocalDate按照指定格式转换为字符串
     *
     * @param localDate 日期
     * @param formatter 指定日期转换格式
     */
    public static String formatLocalDate(LocalDate localDate, String formatter) {
        if (localDate == null) {
            return null;
        }
        try {
            return DateTimeFormatter.ofPattern(formatter).format(localDate);
        } catch (Exception e) {
            log.warn("将日期LocalDate按照指定格式转换为字符串，即将转换的时间为:[{}]，指定的日期转换格式为:[{}]，异常原因为:",
                    localDate, formatter, e);
            return null;
        }
    }

    /**
     * 将日期LocalDateTime转换为为yyyy-MM-dd HH:mm:ss格式的字符串
     *
     * @param localDateTime 日期
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime) {
        return formatLocalDateTime(localDateTime, DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 将日期LocalDateTime按照指定格式转换为字符串
     *
     * @param localDateTime 日期
     * @param formatter     指定日期转换格式
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String formatter) {
        if (localDateTime == null) {
            return null;
        }
        try {
            return DateTimeFormatter.ofPattern(formatter).format(localDateTime);
        } catch (Exception e) {
            log.warn("将日期LocalDateTime按照指定格式转换为字符串，即将转换的时间为:[{}]，指定的日期转换格式为:[{}]，异常原因为:",
                    localDateTime, formatter, e);
            return null;
        }
    }

    /**
     * 获取Date时间戳
     *
     * @param date 指定时间
     */
    public static long dateToLong(Date date) {
        if (date == null) {
            return 0;
        }
        try {
            return date.getTime();
        } catch (Exception e) {
            log.warn("获取Date[{}]时间戳，异常原因为:", date, e);
            return 0;
        }
    }

    /**
     * 获取LocalDate时间戳
     *
     * @param localDate 指定时间
     */
    public static long localDateToLong(LocalDate localDate) {
        if (localDate == null) {
            return 0;
        }
        try {
            return localDateTimeToLong(localDateToLocalDateTime(localDate));
        } catch (Exception e) {
            log.warn("获取LocalDate[{}]时间戳，异常原因为:", localDate, e);
            return 0;
        }
    }

    /**
     * 获取LocalDateTime时间戳
     *
     * @param localDateTime 指定时间
     */
    public static long localDateTimeToLong(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return 0;
        }
        try {
            return localDateTime.atZone(ZoneOffset.ofHours(8)).toInstant().toEpochMilli();
        } catch (Exception e) {
            log.warn("获取LocalDateTime[{}]时间戳，异常原因为:", localDateTime, e);
            return 0;
        }

    }

    /**
     * 将Date转换为LocalDate
     *
     * @param date 指定时间
     */
    public static LocalDate dateToLocalDate(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            log.warn("将Date[{}]转换为LocalDate异常，异常原因为：", date, e);
            return null;
        }
    }

    /**
     * 将Date转换为LocalDateTime
     *
     * @param date 指定时间
     */
    public static LocalDateTime dateToLocalDateTime(Date date) {
        try {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            log.warn("将Date[{}]转换为LocalDateTime异常，异常原因为：", date, e);
            return null;
        }

    }

    /**
     * 将LocalDate转换为Date
     *
     * @param localDate 指定时间
     */
    public static Date localDateToDate(LocalDate localDate) {
        try {
            return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            log.warn("将LocalDate[{}]转换为Date异常，异常原因为：", localDate, e);
            return null;
        }
    }

    /**
     * 将LocalDate转换为LocalDateTime
     *
     * @param localDate 指定时间
     */
    public static LocalDateTime localDateToLocalDateTime(LocalDate localDate) {
        try {
            return localDate.atStartOfDay();
        } catch (Exception e) {
            log.warn("将LocalDate[{}]转换为LocalDateTime异常，异常原因为：", localDate, e);
            return null;
        }
    }

    /**
     * 将LocalDateTime转换为Date
     *
     * @param localDateTime 指定时间
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        try {
            return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        } catch (Exception e) {
            log.warn("将LocalDateTime[{}]转换为Date异常，异常原因为：", localDateTime, e);
            return null;
        }
    }

    /**
     * 将LocalDateTime转换为LocalDate
     *
     * @param localDateTime 指定时间
     */
    public static LocalDate localDateTimeToLocalDate(LocalDateTime localDateTime) {
        try {
            return localDateTime.toLocalDate();
        } catch (Exception e) {
            log.warn("将LocalDateTime[{}]转换为LocalDate异常，异常原因为：", localDateTime, e);
            return null;
        }
    }

    /*==============================日期转换    end===============================*/


    /*==============================具有计算超时方法的工具    start===============================*/

    /**
     * 检查给定的持续时间是否可以表示 {@code sec} 或需要 {@code msec} 表示。
     *
     * @param duration 实际{@link Duration}检查，从不为空。
     * @return 如果包含毫秒信息，则{@link Duration}为true。
     */
    public static boolean hasMillis(Duration duration) {
        return duration.toMillis() % 1000 != 0;
    }

    /**
     * 将给定的超时时间转换为秒。
     * <p>
     * 由于0超时会使某些Redis操作无限期阻塞，如果原始值大于0但在转换时被截断为0，
     * 此方法将返回1。
     *
     * @param duration 要转换的持续时间
     * @return 转换后的超时时间（秒）
     * @since 2.3
     */
    public static long toSeconds(Duration duration) {
        return roundUpIfNecessary(duration.toMillis(), duration.getSeconds());
    }

    /**
     * 将给定的超时时间转换为秒。
     * <p>
     * 由于0超时会使某些Redis操作无限期阻塞，如果原始值大于0但在转换时被截断为0，
     * 此方法将返回1。
     *
     * @param timeout 要转换的超时时间
     * @param unit    超时时间的单位
     * @return 转换后的超时时间（秒）
     */
    public static long toSeconds(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toSeconds(timeout));
    }

    /**
     * 将给定的超时时间转换为带有小数部分的秒。
     *
     * @param timeout 要转换的超时时间
     * @param unit    超时时间的单位
     * @return 转换后的超时时间（秒）
     * @since 2.6
     */
    public static double toDoubleSeconds(long timeout, TimeUnit unit) {

        return switch (unit) {
            case MILLISECONDS, MICROSECONDS, NANOSECONDS -> unit.toMillis(timeout) / 1000d;
            default -> unit.toSeconds(timeout);
        };
    }

    /**
     * 将给定的超时时间转换为毫秒。
     * <p>
     * 由于0超时会使某些Redis操作无限期阻塞，如果原始值大于0但在转换时被截断为0，
     * 此方法将返回1。
     *
     * @param timeout 要转换的超时时间
     * @param unit    超时时间的单位
     * @return 转换后的超时时间（毫秒）
     */
    public static long toMillis(long timeout, TimeUnit unit) {
        return roundUpIfNecessary(timeout, unit.toMillis(timeout));
    }

    /**
     * 如果必要，将转换后的超时时间向上取整到1。
     *
     * @param timeout          原始超时时间
     * @param convertedTimeout 转换后的超时时间
     * @return 向上取整后的超时时间
     */
    private static long roundUpIfNecessary(long timeout, long convertedTimeout) {

        // 0超时会使某些Redis操作无限期阻塞，如果不是这个意图，则向上取整
        if (timeout > 0 && convertedTimeout == 0) {
            return 1;
        }

        return convertedTimeout;
    }
    /*==============================具有计算超时方法的工具    end===============================*/




}
