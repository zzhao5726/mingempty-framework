package top.mingempty.sequence.enums;

/**
 * 序号过期策略
 *
 * @author zzhao
 */
public enum ExpirationStrategyEnum {

    /**
     * 无
     * <p>
     * 默认策略，不过期
     */
    NONE(-1L),

    /**
     * 按日计算
     */
    DAYS(60 * 60 * 24L),

    /**
     * 按周计算
     */
    WEEKS(60 * 60 * 24 * 7L),

    /**
     * 按月计算
     * <p>
     * 使用31天，保证最大过期时间
     */
    MONTHS(60 * 60 * 24 * 31L),

    /**
     * 按年计算
     * <p>
     * 使用366天，保证最大过期时间
     */
    YEARS(60 * 60 * 24 * 365L),
    ;

    /**
     * 策略过期时间
     * <p>
     * 单位：秒
     */
    private final Long expirTime;

    ExpirationStrategyEnum(Long expirTime) {
        this.expirTime = expirTime;
    }


    public Long gainSeconds() {
        return expirTime;
    }
}
