package top.mingempty.domain.other;

import java.util.regex.Pattern;

/**
 * 时间日期格式
 *
 * @author zzhao
 */
public interface DatePattern {

    /**
     * 年格式：yyyy
     */
    String NORM_YEAR_PATTERN = "yyyy";

    /**
     * 年月格式：yyyy-MM
     */
    String NORM_MONTH_PATTERN = "yyyy-MM";

    /**
     * 简单年月格式：yyyyMM
     */
    String SIMPLE_MONTH_PATTERN = "yyyyMM";

    /**
     * 标准日期格式：yyyy-MM-dd
     */
    String NORM_DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 标准时间格式：HH:mm:ss
     */
    String NORM_TIME_PATTERN = "HH:mm:ss";

    /**
     * 标准日期时间格式，精确到分：yyyy-MM-dd HH:mm
     */
    String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";

    /**
     * 标准日期时间格式，精确到秒：yyyy-MM-dd HH:mm:ss
     */
    String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 标准日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss.SSS
     */
    String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * ISO8601日期时间格式，精确到毫秒：yyyy-MM-dd HH:mm:ss,SSS
     */
    String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 标准日期格式：yyyy年MM月dd日
     */
    String CHINESE_DATE_PATTERN = "yyyy年MM月dd日";

    /**
     * 标准日期格式：yyyy年MM月dd日 HH时mm分ss秒
     */
    String CHINESE_DATE_TIME_PATTERN = "yyyy年MM月dd日HH时mm分ss秒";

    /**
     * 标准日期格式：yyyyMMdd
     */
    String PURE_DATE_PATTERN = "yyyyMMdd";

    /**
     * 标准日期格式：HHmmss
     */
    String PURE_TIME_PATTERN = "HHmmss";

    /**
     * 标准日期格式：HHmmssSSS
     */
    String PURE_TIME_MS_PATTERN = "HHmmssSSS";

    /**
     * 标准日期格式：yyyyMMddHHmmss
     */
    String PURE_DATETIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准日期格式：yyyyMMddHHmmssSSS
     */
    String PURE_DATETIME_MS_PATTERN = "yyyyMMddHHmmssSSS";

    /**
     * HTTP头中日期时间格式：EEE, dd MMM yyyy HH:mm:ss z
     */
    String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";

    /**
     * JDK中日期时间格式：EEE MMM dd HH:mm:ss zzz yyyy
     */
    String JDK_DATETIME_PATTERN = "EEE MMM dd HH:mm:ss zzz yyyy";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss
     */
    String UTC_SIMPLE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS
     */
    String UTC_SIMPLE_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss'Z'
     */
    String UTC_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    String UTC_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssXXX
     */
    String UTC_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ssXXX";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     */
    String UTC_MS_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ssZ
     */
    String UTC_MS_WITH_ZONE_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    /**
     * UTC时间：yyyy-MM-dd'T'HH:mm:ss.SSSXXX
     */
    String UTC_MS_WITH_XXX_OFFSET_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * 标准日期时间正则，每个字段支持单个数字或2个数字，包括：
     * <pre>
     *     yyyy-MM-dd HH:mm:ss.SSSSSS
     *     yyyy-MM-dd HH:mm:ss.SSS
     *     yyyy-MM-dd HH:mm:ss
     *     yyyy-MM-dd HH:mm
     *     yyyy-MM-dd
     * </pre>
     *
     * @since 5.3.6
     */
    Pattern REGEX_NORM = Pattern.compile("\\d{4}-\\d{1,2}-\\d{1,2}(\\s\\d{1,2}:\\d{1,2}(:\\d{1,2})?)?(.\\d{1,6})?");

}
