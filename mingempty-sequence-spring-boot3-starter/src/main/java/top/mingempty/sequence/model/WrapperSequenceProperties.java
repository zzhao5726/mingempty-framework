package top.mingempty.sequence.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.mingempty.sequence.enums.ExpirationStrategyEnum;
import top.mingempty.sequence.enums.SeqRealizeEnum;

/**
 * 基础序号生成器的配置
 *
 * @author zzhao
 */
@Data
@EqualsAndHashCode(of = {"instanceName", "seqName"})
public class WrapperSequenceProperties {

    /**
     * 序号事项机制
     */
    private SeqRealizeEnum seqRealize;

    /**
     * 实例名称
     */
    private String instanceName;

    /**
     * 序号的名称
     */
    private String seqName;

    /**
     * 缓存的步长
     */
    private int step = 50;

    /**
     * 序号过期策略
     */
    private ExpirationStrategyEnum expirationStrategy;

    /**
     * 是否删除过期的序号
     * <p>
     * 默认为false
     * <p>
     * 仅当{@code seqRealize}值为{@link SeqRealizeEnum#Redis}以及{@code expirationStrategy}值不为{@link ExpirationStrategyEnum#NONE}时有效
     */
    private boolean expirationDelete = false;

}
