package top.mingempty.sequence.api.impl;

import cn.hutool.core.lang.Assert;
import lombok.Getter;
import top.mingempty.commons.util.BussDateTimeUtil;
import top.mingempty.commons.util.DateTimeUtil;
import top.mingempty.domain.other.DatePattern;
import top.mingempty.sequence.api.Sequence;
import top.mingempty.sequence.enums.ExpirationStrategyEnum;

import java.time.LocalDate;

/**
 * 针对具有过期策略的序号生成器的抽象
 *
 * @author zzhao
 */
public abstract class AbstractExpirationStrategySequence<T extends AbstractCacheSequence> implements Sequence<Long> {

    @Getter
    private final ExpirationStrategyEnum expirationStrategy;

    @Getter
    private final T cacheSequence;

    @Getter
    private volatile String key = "";

    private volatile String dateStr = "000000";


    protected AbstractExpirationStrategySequence(T cacheSequence, ExpirationStrategyEnum expirationStrategy) {
        Assert.notNull(cacheSequence, "cacheSequence is null");
        this.cacheSequence = cacheSequence;
        this.expirationStrategy = expirationStrategy;
        expirationStrategyKey();
    }


    /**
     * 获取一个具体的序号生成器
     */
    public T gainCacheSequence() {
        return this.cacheSequence;
    }

    /**
     * 下一个序号
     */
    @Override
    public Long next() {
        expirationStrategyKey();
        this.gainCacheSequence().changeSeqName(this.getKey());
        return this.gainCacheSequence().next();
    }

    /**
     * 设置一个新的步长
     *
     * @param step
     */
    public void changeStep(int step) {
        this.gainCacheSequence().changeStep(step);
    }

    /**
     * 计算名称
     */

    protected final String expirationStrategyKey() {
        String dateStr = switch (this.expirationStrategy) {
            case NONE -> "";
            case DAYS -> DateTimeUtil.formatLocalDate(LocalDate.now(), DatePattern.PURE_DATE_PATTERN);
            case WEEKS -> BussDateTimeUtil.getDayStrWithWeekFirst();
            case MONTHS -> BussDateTimeUtil.getDayStrWithMonthFirst();
            case YEARS -> BussDateTimeUtil.getDayStrWithYearFirst();
        };
        if (dateStr.equals(this.dateStr)) {
            return this.key;
        }
        this.dateStr = dateStr;
        if (dateStr.isEmpty()) {
            this.key = this.expirationStrategy.name().concat(this.cacheSequence.seqRealize().getSeparator()).concat(this.cacheSequence.seqName());
            return this.key;
        }
        this.key = this.expirationStrategy.name().concat(this.cacheSequence.seqRealize().getSeparator()).concat(this.dateStr).concat(this.cacheSequence.seqRealize().getSeparator()).concat(this.cacheSequence.seqName());
        return this.key;
    }


}
