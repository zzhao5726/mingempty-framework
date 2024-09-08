package top.mingempty.distributed.lock.factory;

/**
 * 锁抽象接口
 *
 * @param <T>
 * @author zzhao
 */
public interface LockBase<T> {

    /**
     * 获取锁
     */
    T lock();
}
