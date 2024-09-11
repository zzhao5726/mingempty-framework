package top.mingempty.sequence.api.impl.base;


import cn.hutool.core.lang.UUID;
import top.mingempty.sequence.api.Sequence;

/**
 * UUID序列生成器
 *
 * @author zzhao
 */
public class UUIDSequence implements Sequence<String> {

    /**
     * 全局唯一单例类
     */
    private final static UUIDSequence UUID_SEQUENCE = new UUIDSequence();

    /**
     * 私有化构造器
     */
    private UUIDSequence() {
    }

    /**
     * 下一个序号
     */
    @Override
    public String next() {
        return UUID.fastUUID().toString();
    }

    /**
     * 获取UUID生成的单例
     *
     * @return
     */
    public static UUIDSequence instance() {
        return UUID_SEQUENCE;
    }
}
