package top.mingempty.sequence.api;

/**
 * 获取序号
 *
 * @param <T>
 * @author zzhao
 */
public interface Sequence<T> {

    /**
     * 下一个序号
     */
    T next();
}