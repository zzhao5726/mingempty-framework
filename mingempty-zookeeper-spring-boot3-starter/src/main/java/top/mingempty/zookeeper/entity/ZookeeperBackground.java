package top.mingempty.zookeeper.entity;

import lombok.Builder;
import lombok.Getter;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.Backgroundable;
import org.apache.curator.framework.api.ErrorListenerPathAndBytesable;
import org.apache.curator.framework.api.ErrorListenerPathable;
import org.apache.curator.framework.api.PathAndBytesable;
import org.apache.curator.framework.api.Pathable;
import org.apache.curator.framework.api.UnhandledErrorListener;

import java.util.Optional;
import java.util.concurrent.Executor;

/**
 * zookeeper后台执行参数
 *
 * @author zzhao
 */
@Getter
@Builder
public class ZookeeperBackground {
    private Object context;
    private BackgroundCallback callback;
    private Executor executor;
    private UnhandledErrorListener listener;

    public <T> T inBackground(Backgroundable<T> backgroundable) {
        if (callback != null && context != null && executor != null) {
            return backgroundable.inBackground(callback, context, executor);
        }

        if (callback != null && executor != null) {
            return backgroundable.inBackground(callback, executor);
        }

        if (callback != null && context != null) {
            return backgroundable.inBackground(callback, context);
        }

        if (callback != null) {
            return backgroundable.inBackground(callback);
        }

        if (context != null) {
            return backgroundable.inBackground(context);
        }

        return backgroundable.inBackground();
    }

    /**
     * 获取带有错误监听的PathAndBytesable
     *
     * @param backgroundable
     * @param <T>
     * @return
     */
    public <T extends ErrorListenerPathAndBytesable<V>,V> PathAndBytesable<V> inBackgroundWithPathAndBytesable(Backgroundable<T> backgroundable) {
        T pathAndBytesable = inBackground(backgroundable);
        return Optional.ofNullable(listener)
                .flatMap(listener1 -> Optional.of(pathAndBytesable.withUnhandledErrorListener(listener1)))
                .orElse(pathAndBytesable);
    }

    /**
     * 获取带有错误监听的Pathable
     *
     * @param backgroundable
     * @param <T>
     * @return
     */
    public <T extends ErrorListenerPathable<V>, V> Pathable<V> inBackgroundWithPathable(Backgroundable<T> backgroundable) {
        T pathable = inBackground(backgroundable);
        return Optional.ofNullable(listener)
                .flatMap(listener1 -> Optional.of(pathable.withUnhandledErrorListener(listener1)))
                .orElse(pathable);
    }

}
