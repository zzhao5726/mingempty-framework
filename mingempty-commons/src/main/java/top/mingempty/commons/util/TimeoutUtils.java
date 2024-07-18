package top.mingempty.commons.util;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * 具有计算超时方法的工具类
 *
 * @author Jennifer Hickey
 * @author Mark Paluch
 * @author Christoph Strobl
 */
public abstract class TimeoutUtils {

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

}
