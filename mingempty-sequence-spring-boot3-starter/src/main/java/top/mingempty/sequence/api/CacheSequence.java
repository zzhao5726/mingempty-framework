package top.mingempty.sequence.api;

/**
 * 基于缓存的序号生成器
 *
 * @param <T>
 * @author zzhao
 */
public interface CacheSequence<T> extends Sequence<T> {

    /**
     * 当前缓存大小
     */
    int cacheSize();

    /**
     * 步长取值
     */
    default int step() {
        return 50;
    }

    /**
     * 更新步长时机
     * 即当缓存数量小于该值时，则进行更新
     */
    default int updateStepTime() {
        return step() / 4;
    }


    /**
     * 是否更新步长
     */
    default boolean isUpdateStep() {
        int size = cacheSize();
        if (size == 0) {
            return true;
        }
        return cacheSize() < updateStepTime();
    }

    /**
     * 更新缓存值
     * <p>
     * 当返回值为0时，表示缓存更新失败
     * 当返回值为1时，表示更新缓存成功
     * 当返回值为2时，表示无需更新缓存
     */
    int updateCache();

}