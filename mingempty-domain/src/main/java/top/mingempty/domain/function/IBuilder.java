package top.mingempty.domain.function;

/**
 * 建造者模式接口定义
 *
 * @param <T> 建造对象类型
 * @author zzhao
 */
@FunctionalInterface
public interface IBuilder<T> {
    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();
}