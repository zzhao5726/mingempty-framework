package top.mingempty.domain.function;

/**
 * 一个没有出入参的方法
 *
 * @author zzhao
 */
@FunctionalInterface
public interface ApplyFunction {

    /**
     * 执行一个方法
     */
    void apply();
}
