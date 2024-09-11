package top.mingempty.zookeeper.entity.wapper;

import org.apache.curator.CuratorZookeeperClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.WatcherRemoveCuratorFramework;
import org.apache.curator.framework.api.CreateBuilder;
import org.apache.curator.framework.api.CuratorListener;
import org.apache.curator.framework.api.DeleteBuilder;
import org.apache.curator.framework.api.ExistsBuilder;
import org.apache.curator.framework.api.GetACLBuilder;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.framework.api.GetConfigBuilder;
import org.apache.curator.framework.api.GetDataBuilder;
import org.apache.curator.framework.api.ReconfigBuilder;
import org.apache.curator.framework.api.RemoveWatchesBuilder;
import org.apache.curator.framework.api.SetACLBuilder;
import org.apache.curator.framework.api.SetDataBuilder;
import org.apache.curator.framework.api.SyncBuilder;
import org.apache.curator.framework.api.UnhandledErrorListener;
import org.apache.curator.framework.api.WatchesBuilder;
import org.apache.curator.framework.api.transaction.CuratorMultiTransaction;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.TransactionOp;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.listen.Listenable;
import org.apache.curator.framework.schema.SchemaSet;
import org.apache.curator.framework.state.ConnectionStateErrorPolicy;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.utils.EnsurePath;
import org.apache.curator.utils.ZookeeperCompatibility;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.server.quorum.flexible.QuorumVerifier;
import org.springframework.beans.factory.DisposableBean;
import top.mingempty.domain.other.AbstractRouter;
import top.mingempty.zookeeper.aspect.ZookeeperAspect;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author zzhao
 */
public class CuratorFrameworkWrapper
        extends AbstractRouter<CuratorFramework>
        implements CuratorFramework, DisposableBean {

    public CuratorFrameworkWrapper(String defaultTargetName, Map<String, CuratorFramework> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public CuratorFrameworkWrapper(String defaultTargetName, Map<String, CuratorFramework> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }


    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return ZookeeperAspect.acquireName();
    }

    /************************CuratorFramework方法封装----------start*********************************************/

    /**
     * Start the client. Most mutator methods will not work until the client is started
     */
    @Override
    public void start() {
        this.getResolvedRouters()
                .stream()
                .filter(curatorFramework -> CuratorFrameworkState.LATENT.equals(curatorFramework.getState()))
                .forEach(CuratorFramework::start);
    }

    /**
     * Stop the client
     */
    @Override
    public void close() {
        this.getResolvedRouters()
                .stream()
                .filter(curatorFramework -> CuratorFrameworkState.STARTED.equals(curatorFramework.getState()))
                .forEach(CuratorFramework::close);
    }

    /**
     * Returns the state of this instance
     *
     * @return state
     */
    @Override
    public CuratorFrameworkState getState() {
        return determineTargetRouter().getState();
    }

    /**
     * Return true if the client is started, not closed, etc.
     *
     * @return true/false
     * @deprecated use {@link #getState()} instead
     */
    @Override
    @Deprecated
    public boolean isStarted() {
        return determineTargetRouter().isStarted();
    }

    /**
     * Start a create builder
     *
     * @return builder object
     */
    @Override
    public CreateBuilder create() {
        return determineTargetRouter().create();
    }

    /**
     * Start a delete builder
     *
     * @return builder object
     */
    @Override
    public DeleteBuilder delete() {
        return determineTargetRouter().delete();
    }

    /**
     * Start an exists builder
     * <p>
     * The builder will return a Stat object as if org.apache.zookeeper.ZooKeeper.exists() were called.  Thus, a null
     * means that it does not exist and an actual Stat object means it does exist.
     *
     * @return builder object
     */
    @Override
    public ExistsBuilder checkExists() {
        return determineTargetRouter().checkExists();
    }

    /**
     * Start a get data builder
     *
     * @return builder object
     */
    @Override
    public GetDataBuilder getData() {
        return determineTargetRouter().getData();
    }

    /**
     * Start a set data builder
     *
     * @return builder object
     */
    @Override
    public SetDataBuilder setData() {
        return determineTargetRouter().setData();
    }

    /**
     * Start a get children builder
     *
     * @return builder object
     */
    @Override
    public GetChildrenBuilder getChildren() {
        return determineTargetRouter().getChildren();
    }

    /**
     * Start a get ACL builder
     *
     * @return builder object
     */
    @Override
    public GetACLBuilder getACL() {
        return determineTargetRouter().getACL();
    }

    /**
     * Start a set ACL builder
     *
     * @return builder object
     */
    @Override
    public SetACLBuilder setACL() {
        return determineTargetRouter().setACL();
    }

    /**
     * Start a reconfig builder
     *
     * @return builder object
     */
    @Override
    public ReconfigBuilder reconfig() {
        return determineTargetRouter().reconfig();
    }

    /**
     * Start a getConfig builder
     *
     * @return builder object
     */
    @Override
    public GetConfigBuilder getConfig() {
        return determineTargetRouter().getConfig();
    }

    /**
     * Start a transaction builder
     *
     * @return builder object
     * @deprecated use {@link #transaction()} instead
     */
    @Override
    @Deprecated
    public CuratorTransaction inTransaction() {
        return determineTargetRouter().inTransaction();
    }

    /**
     * Start a transaction builder
     *
     * @return builder object
     */
    @Override
    public CuratorMultiTransaction transaction() {
        return determineTargetRouter().transaction();
    }

    /**
     * Allocate an operation that can be used with {@link #transaction()}.
     * NOTE: {@link CuratorOp} instances created by this builder are
     * reusable.
     *
     * @return operation builder
     */
    @Override
    public TransactionOp transactionOp() {
        return determineTargetRouter().transactionOp();
    }

    /**
     * Perform a sync on the given path - syncs are always in the background
     *
     * @param path                    the path
     * @param backgroundContextObject optional context
     * @deprecated use {@link #sync()} instead
     */
    @Override
    @Deprecated
    public void sync(String path, Object backgroundContextObject) {
        determineTargetRouter().sync(path, backgroundContextObject);
    }

    /**
     * Create all nodes in the specified path as containers if they don't
     * already exist
     *
     * @param path path to create
     * @throws Exception errors
     */
    @Override
    public void createContainers(String path) throws Exception {
        determineTargetRouter().createContainers(path);
    }

    /**
     * Start a sync builder. Note: sync is ALWAYS in the background even
     * if you don't use one of the background() methods
     *
     * @return builder object
     */
    @Override
    public SyncBuilder sync() {
        return determineTargetRouter().sync();
    }

    /**
     * Start a remove watches builder.
     *
     * @return builder object
     * @deprecated use {@link #watchers()} in ZooKeeper 3.6+
     */
    @Override
    @Deprecated
    public RemoveWatchesBuilder watches() {
        return determineTargetRouter().watches();
    }

    /**
     * Start a watch builder. Supported only when ZooKeeper JAR of version 3.6 or
     * above is used, throws {@code IllegalStateException} for ZooKeeper JAR 3.5 or below
     *
     * @return builder object
     * @throws IllegalStateException ZooKeeper JAR is 3.5 or below
     */
    @Override
    public WatchesBuilder watchers() {
        return determineTargetRouter().watchers();
    }

    /**
     * Returns the listenable interface for the Connect State
     *
     * @return listenable
     */
    @Override
    public Listenable<ConnectionStateListener> getConnectionStateListenable() {
        return determineTargetRouter().getConnectionStateListenable();
    }

    /**
     * Returns the listenable interface for events
     *
     * @return listenable
     */
    @Override
    public Listenable<CuratorListener> getCuratorListenable() {
        return determineTargetRouter().getCuratorListenable();
    }

    /**
     * Returns the listenable interface for unhandled errors
     *
     * @return listenable
     */
    @Override
    public Listenable<UnhandledErrorListener> getUnhandledErrorListenable() {
        return determineTargetRouter().getUnhandledErrorListenable();
    }

    /**
     * Returns a facade of the current instance that does _not_ automatically
     * pre-pend the namespace to all paths
     *
     * @return facade
     * @deprecated Since 2.9.0 - use {@link #usingNamespace} passing <code>null</code>
     */
    @Override
    @Deprecated
    public CuratorFramework nonNamespaceView() {
        return determineTargetRouter().nonNamespaceView();
    }

    /**
     * Returns a facade of the current instance that uses the specified namespace
     * or no namespace if <code>newNamespace</code> is <code>null</code>.
     *
     * @param newNamespace the new namespace or null for none
     * @return facade
     */
    @Override
    public CuratorFramework usingNamespace(String newNamespace) {
        return determineTargetRouter().usingNamespace(newNamespace);
    }

    /**
     * Return the current namespace or "" if none
     *
     * @return namespace
     */
    @Override
    public String getNamespace() {
        return determineTargetRouter().getNamespace();
    }

    /**
     * Return the managed zookeeper client
     *
     * @return client
     */
    @Override
    public CuratorZookeeperClient getZookeeperClient() {
        return determineTargetRouter().getZookeeperClient();
    }

    /**
     * Return zookeeper server compatibility
     *
     * @return compatibility
     */
    @Override
    public ZookeeperCompatibility getZookeeperCompatibility() {
        return determineTargetRouter().getZookeeperCompatibility();
    }

    /**
     * Allocates an ensure path instance that is namespace aware
     *
     * @param path path to ensure
     * @return new EnsurePath instance
     * @deprecated Since 2.9.0 - prefer {@link CreateBuilder#creatingParentContainersIfNeeded()}, {@link ExistsBuilder#creatingParentContainersIfNeeded()}
     * or {@link CuratorFramework#createContainers(String)}
     */
    @Override
    @Deprecated
    public EnsurePath newNamespaceAwareEnsurePath(String path) {
        return determineTargetRouter().newNamespaceAwareEnsurePath(path);
    }

    /**
     * Curator can hold internal references to watchers that may inhibit garbage collection.
     * Call this method on watchers you are no longer interested in.
     *
     * @param watcher the watcher
     * @deprecated As of ZooKeeper 3.5 Curators recipes will handle removing watcher references
     * when they are no longer used. If you write your own recipe, follow the example of Curator
     * recipes and use {@link #newWatcherRemoveCuratorFramework} calling {@link WatcherRemoveCuratorFramework#removeWatchers()}
     * when closing your instance.
     */
    @Override
    @Deprecated
    public void clearWatcherReferences(Watcher watcher) {
        determineTargetRouter().clearWatcherReferences(watcher);
    }

    /**
     * Block until a connection to ZooKeeper is available or the maxWaitTime has been exceeded
     *
     * @param maxWaitTime The maximum wait time. Specify a value &lt;= 0 to wait indefinitely
     * @param units       The time units for the maximum wait time.
     * @return True if connection has been established, false otherwise.
     * @throws InterruptedException If interrupted while waiting
     */
    @Override
    public boolean blockUntilConnected(int maxWaitTime, TimeUnit units) throws InterruptedException {
        return determineTargetRouter().blockUntilConnected(maxWaitTime, units);
    }

    /**
     * Block until a connection to ZooKeeper is available. This method will not return until a
     * connection is available or it is interrupted, in which case an InterruptedException will
     * be thrown
     *
     * @throws InterruptedException If interrupted while waiting
     */
    @Override
    public void blockUntilConnected() throws InterruptedException {
        determineTargetRouter().blockUntilConnected();
    }

    /**
     * Returns a facade of the current instance that tracks
     * watchers created and allows a one-shot removal of all watchers
     * via {@link WatcherRemoveCuratorFramework#removeWatchers()}
     *
     * @return facade
     */
    @Override
    public WatcherRemoveCuratorFramework newWatcherRemoveCuratorFramework() {
        return determineTargetRouter().newWatcherRemoveCuratorFramework();
    }

    /**
     * Return the configured error policy
     *
     * @return error policy
     */
    @Override
    public ConnectionStateErrorPolicy getConnectionStateErrorPolicy() {
        return determineTargetRouter().getConnectionStateErrorPolicy();
    }

    /**
     * Current maintains a cached view of the Zookeeper quorum config.
     *
     * @return the current config
     */
    @Override
    public QuorumVerifier getCurrentConfig() {
        return determineTargetRouter().getCurrentConfig();
    }

    /**
     * Return this instance's schema set
     *
     * @return schema set
     */
    @Override
    public SchemaSet getSchemaSet() {
        return determineTargetRouter().getSchemaSet();
    }

    /**
     * Calls {@link #notifyAll()} on the given object after first synchronizing on it. This is
     * done from the {@link #runSafe(Runnable)} thread.
     *
     * @param monitorHolder object to sync on and notify
     * @return a CompletableFuture that can be used to monitor when the call is complete
     * @since 4.1.0
     */
    @Override
    public CompletableFuture<Void> postSafeNotify(Object monitorHolder) {
        return determineTargetRouter().postSafeNotify(monitorHolder);
    }

    /**
     * Curator (and user) recipes can use this to run notifyAll
     * and other blocking calls that might normally block ZooKeeper's event thread.
     *
     * @param runnable proc to call from a safe internal thread
     * @return a CompletableFuture that can be used to monitor when the call is complete
     * @since 4.1.0
     */
    @Override
    public CompletableFuture<Void> runSafe(Runnable runnable) {
        return determineTargetRouter().runSafe(runnable);
    }

    /************************CuratorFramework方法封装----------end*********************************************/

    @Override
    public void destroy() throws Exception {
        this.close();
    }
}
