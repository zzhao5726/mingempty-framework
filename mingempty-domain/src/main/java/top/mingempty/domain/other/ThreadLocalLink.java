package top.mingempty.domain.other;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 线程变量链表集
 *
 * @author zzhao
 */
public class ThreadLocalLink<T> {

    private final ThreadLocal<Deque<T>> THREAD_LOCAL;

    public ThreadLocalLink() {
        this(new ThreadLocal<>());
    }

    public ThreadLocalLink(ThreadLocal<Deque<T>> threadLocal) {
        this.THREAD_LOCAL = threadLocal;
    }

    /**
     * 获取当前线程Link切面数据
     */
    public T acquireData() {
        Deque<T> tDeque = THREAD_LOCAL.get();
        if (tDeque == null) {
            init();
        }
        return THREAD_LOCAL.get().peek();
    }

    /**
     * 设置当前线程Link切面数据
     */
    public void putData(T data) {
        if (data == null) {
            return;
        }
        if (THREAD_LOCAL.get() == null) {
            init();
        }
        THREAD_LOCAL.get().push(data);
    }

    /**
     * 清空线程当前数据
     */
    public void removeData() {
        Deque<T> deque = THREAD_LOCAL.get();
        if (deque != null) {
            deque.poll();
            if (deque.isEmpty()) {
                THREAD_LOCAL.remove();
            }
        }
    }

    /**
     * 强制清空本地线程全量数据
     */
    public void removeAll() {
        Deque<T> deque = THREAD_LOCAL.get();
        if (deque != null) {
            deque.clear();
        }
        THREAD_LOCAL.remove();
    }

    /**
     *
     * 初始化队列
     */
    private synchronized void init() {
        if (THREAD_LOCAL.get() == null) {
            THREAD_LOCAL.set(new ArrayDeque<>());
        }
    }


}
