package top.mingempty.event;

import org.springframework.core.Ordered;

/**
 * 统一事件监听业务执行服务
 *
 * @author zzhao
 */
public interface MeEventListenerHandler<D> extends Ordered {

    /**
     * 获取方法名称</br>
     *
     * @return
     */
    default String gainFunctionName() {
        return this.getClass().getName();
    }


    /**
     * 获取事件是否异步执行</br>
     *
     * @return 默认非异步
     */
    default boolean gainAsync() {
        return Boolean.FALSE;
    }

    /**
     * 获取异步执行的bean名称</br>
     * <p>
     * 如果为空或无法通过spring容器获取，则默认使用默认异步执行器</br>
     *
     * @return
     */
    default String gainAsyncBeanName() {
        return null;
    }

    /**
     * 异步执行时是否开启新的链路树节点
     *
     * @return
     */
    default boolean gainNewSpan() {
        return Boolean.FALSE;
    }

    /**
     * 执行事件的统一入口
     * 需要自行实现真正执行业务的方法
     */
    void handle(D data);

    /**
     * Get the order value of this object.
     * <p>Higher values are interpreted as lower priority. As a consequence,
     * the object with the lowest value has the highest priority (somewhat
     * analogous to Servlet {@code load-on-startup} values).
     * <p>Same order values will result in arbitrary sort positions for the
     * affected objects.
     *
     * @return the order value
     * @see #HIGHEST_PRECEDENCE
     * @see #LOWEST_PRECEDENCE
     */
    @Override
    default int getOrder() {
        return 0;
    }
}

