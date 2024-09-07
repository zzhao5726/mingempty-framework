package top.mingempty.zookeeper.entity;

import lombok.Getter;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.api.RemoveWatchesBuilder;
import org.apache.curator.framework.api.RemoveWatchesType;
import org.apache.curator.framework.api.Watchable;
import org.apache.curator.framework.api.WatchableBase;
import org.apache.zookeeper.Watcher;
import org.springframework.util.Assert;

/**
 * zookeeper监听器
 *
 * @author zzhao
 */
@Getter
public class ZookeeperWatchable {

    private final Watcher watcher;

    private final CuratorWatcher curatorWatcher;

    public ZookeeperWatchable() {
        this.watcher = null;
        this.curatorWatcher = null;
    }

    public ZookeeperWatchable(Watcher watcher) {
        Assert.notNull(watcher, "watcher must not be null");
        this.watcher = watcher;
        this.curatorWatcher = null;
    }

    public ZookeeperWatchable(CuratorWatcher curatorWatcher) {
        Assert.notNull(curatorWatcher, "curatorWatcher must not be null");
        this.watcher = null;
        this.curatorWatcher = curatorWatcher;
    }


    public <T> T getWatcher(WatchableBase<T> watchableBase) {
        if (watcher != null) {
            return watchableBase.usingWatcher(watcher);
        }
        if (curatorWatcher != null) {
            return watchableBase.usingWatcher(curatorWatcher);
        }

        if (watchableBase instanceof Watchable watchable) {
            return (T) watchable.watched();
        }

        throw new IllegalArgumentException("watchableBase must be Watchable or WatchableBase");
    }


    public RemoveWatchesType removeWatcher(RemoveWatchesBuilder removeWatchesBuilder) {
        if (watcher != null) {
            return removeWatchesBuilder.remove(watcher);
        }
        if (curatorWatcher != null) {
            return removeWatchesBuilder.remove(curatorWatcher);
        }
        return removeWatchesBuilder.removeAll();
    }
}
