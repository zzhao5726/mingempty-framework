package top.mingempty.concurrent.model;


import top.mingempty.domain.other.GlobalConstant;

/**
 * 设置线程池别名
 *
 * @author zzhao
 */
public class ExecutorServiceThreadLocal {

    private static final ThreadLocal<String> THREAD_LOCAL = ThreadLocal.withInitial(() -> GlobalConstant.DEFAULT_INSTANCE_NAME);


    /**
     * 设置当前线程变量
     *
     * @param threadPoolName 线程池名称
     */
    public static void put(String threadPoolName) {
        THREAD_LOCAL.set(threadPoolName);
    }

    /**
     * 获取当前线程变量
     *
     * @return
     */
    public static String gain() {
        return THREAD_LOCAL.get();
    }

    /**
     * 移除当前线程变量
     */
    public static void remove() {
        THREAD_LOCAL.remove();
    }


}
