package top.mingempty.cache.redis.entity.wapper;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ObjectRecord;
import org.springframework.data.redis.core.BoundGeoOperations;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.BoundStreamOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.BulkMapper;
import org.springframework.data.redis.core.ClusterOperations;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StreamOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.query.SortQuery;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;
import top.mingempty.domain.other.AbstractRouter;

import java.io.Closeable;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 自定义Redis路由模板
 *
 * @author zzhao
 * @date 2023/3/11 21:28
 */
public class RedisTemplateWrapper extends AbstractRouter<RedisTemplate<String, Object>>
        implements RedisOperations<String, Object> {

    public RedisTemplateWrapper(String defaultTargetName, Map<String, RedisTemplate<String, Object>> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedisTemplateWrapper(String defaultTargetName, Map<String, RedisTemplate<String, Object>> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return RedisCacheAspect.acquireCacheName();
    }

    /************************RedisTemplate方法封装----------start*********************************************/
    /**
     * Executes the given action within a Redis connection. Application exceptions thrown by the action object get
     * propagated to the caller (can only be unchecked) whenever possible. Redis exceptions are transformed into
     * appropriate DAO ones. Allows for returning a result object, that is a domain object or a collection of domain
     * objects. Performs automatic serialization/deserialization for the given objects to and from binary data suitable
     * for the Redis storage. Note: Callback code is not supposed to handle transactions itself! Use an appropriate
     * transaction manager. Generally, callback code must not touch any Connection lifecycle methods, like close, to let
     * the template do its work.
     *
     * @param action callback object that specifies the Redis action. Must not be {@literal null}.
     * @return a result object returned by the action or {@literal null}
     */
    @Override
    public <T> T execute(RedisCallback<T> action) {
        return this.determineTargetRouter().execute(action);
    }

    /**
     * Executes a Redis session. Allows multiple operations to be executed in the same session enabling 'transactional'
     * capabilities through {@link #multi()} and {@link #watch(Collection)} operations.
     *
     * @param session session callback. Must not be {@literal null}.
     * @return result object returned by the action or {@literal null}
     */
    @Override
    public <T> T execute(SessionCallback<T> session) {
        return this.determineTargetRouter().execute(session);
    }

    /**
     * Executes the given action object on a pipelined connection, returning the results. Note that the callback
     * <b>cannot</b> return a non-null value as it gets overwritten by the pipeline. This method will use the default
     * serializers to deserialize results
     *
     * @param action callback object to execute
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(RedisCallback<?> action) {
        return this.determineTargetRouter().executePipelined(action);
    }

    /**
     * Executes the given action object on a pipelined connection, returning the results using a dedicated serializer.
     * Note that the callback <b>cannot</b> return a non-null value as it gets overwritten by the pipeline.
     *
     * @param action           callback object to execute
     * @param resultSerializer The Serializer to use for individual values or Collections of values. If any returned
     *                         values are hashes, this serializer will be used to deserialize both the key and value
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(RedisCallback<?> action, RedisSerializer<?> resultSerializer) {
        return this.determineTargetRouter().executePipelined(action, resultSerializer);
    }

    /**
     * Executes the given Redis session on a pipelined connection. Allows transactions to be pipelined. Note that the
     * callback <b>cannot</b> return a non-null value as it gets overwritten by the pipeline.
     *
     * @param session Session callback
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(SessionCallback<?> session) {
        return this.determineTargetRouter().executePipelined(session);
    }

    /**
     * Executes the given Redis session on a pipelined connection, returning the results using a dedicated serializer.
     * Allows transactions to be pipelined. Note that the callback <b>cannot</b> return a non-null value as it gets
     * overwritten by the pipeline.
     *
     * @param session          Session callback
     * @param resultSerializer
     * @return list of objects returned by the pipeline
     */
    @Override
    public List<Object> executePipelined(SessionCallback<?> session, RedisSerializer<?> resultSerializer) {
        return this.determineTargetRouter().executePipelined(session, resultSerializer);
    }

    /**
     * Executes the given {@link RedisScript}
     *
     * @param script The script to execute
     * @param keys   Any keys that need to be passed to the script
     * @param args   Any args that need to be passed to the script
     * @return The return value of the script or null if {@link RedisScript#getResultType()} is null, likely indicating a
     * throw-away status reply (i.e. "OK")
     */
    @Override
    public <T> T execute(RedisScript<T> script, List<String> keys, Object... args) {
        return this.determineTargetRouter().execute(script, keys, args);
    }

    /**
     * Executes the given {@link RedisScript}, using the provided {@link RedisSerializer}s to serialize the script
     * arguments and result.
     *
     * @param script           The script to execute
     * @param argsSerializer   The {@link RedisSerializer} to use for serializing args
     * @param resultSerializer The {@link RedisSerializer} to use for serializing the script return value
     * @param keys             Any keys that need to be passed to the script
     * @param args             Any args that need to be passed to the script
     * @return The return value of the script or null if {@link RedisScript#getResultType()} is null, likely indicating a
     * throw-away status reply (i.e. "OK")
     */
    @Override
    public <T> T execute(RedisScript<T> script, RedisSerializer<?> argsSerializer, RedisSerializer<T> resultSerializer, List<String> keys, Object... args) {
        return this.determineTargetRouter().execute(script, argsSerializer, resultSerializer, keys, args);
    }

    /**
     * Allocates and binds a new {@link RedisConnection} to the actual return type of the method. It is up to the caller
     * to free resources after use.
     *
     * @param callback must not be {@literal null}.
     * @return
     * @since 1.8
     */
    @Override
    public <T extends Closeable> T executeWithStickyConnection(RedisCallback<T> callback) {
        return this.determineTargetRouter().executeWithStickyConnection(callback);
    }

    /**
     * Copy given {@code sourceKey} to {@code targetKey}.
     *
     * @param sourceKey must not be {@literal null}.
     * @param targetKey must not be {@literal null}.
     * @param replace   whether the key was copied. {@literal null} when used in pipeline / transaction.
     * @return
     * @see <a href="https://redis.io/commands/copy">Redis Documentation: COPY</a>
     * @since 2.6
     */
    @Override
    public Boolean copy(String sourceKey, String targetKey, boolean replace) {
        return this.determineTargetRouter().copy(sourceKey, targetKey, replace);
    }

    /**
     * Determine if given {@code key} exists.
     *
     * @param key must not be {@literal null}.
     * @return
     * @see <a href="https://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     */
    @Override
    public Boolean hasKey(String key) {
        return this.determineTargetRouter().hasKey(key);
    }

    /**
     * Count the number of {@code keys} that exist.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys existing among the ones specified as arguments. Keys mentioned multiple times and
     * existing are counted multiple times.
     * @see <a href="https://redis.io/commands/exists">Redis Documentation: EXISTS</a>
     * @since 2.1
     */
    @Override
    public Long countExistingKeys(Collection<String> keys) {
        return this.determineTargetRouter().countExistingKeys(keys);
    }

    /**
     * Delete given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal true} if the key was removed.
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    @Override
    public Boolean delete(String key) {
        return this.determineTargetRouter().delete(key);
    }

    /**
     * Delete given {@code keys}.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/del">Redis Documentation: DEL</a>
     */
    @Override
    public Long delete(Collection<String> keys) {
        return this.determineTargetRouter().delete(keys);
    }

    /**
     * Unlink the {@code key} from the keyspace. Unlike with {@link #delete(String)} the actual memory reclaiming here
     * happens asynchronously.
     *
     * @param key must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/unlink">Redis Documentation: UNLINK</a>
     * @since 2.1
     */
    @Override
    public Boolean unlink(String key) {
        return this.determineTargetRouter().unlink(key);
    }

    /**
     * Unlink the {@code keys} from the keyspace. Unlike with {@link #delete(Collection)} the actual memory reclaiming
     * here happens asynchronously.
     *
     * @param keys must not be {@literal null}.
     * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/unlink">Redis Documentation: UNLINK</a>
     * @since 2.1
     */
    @Override
    public Long unlink(Collection<String> keys) {
        return this.determineTargetRouter().unlink(keys);
    }

    /**
     * Determine the type stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/type">Redis Documentation: TYPE</a>
     */
    @Override
    public DataType type(String key) {
        return this.determineTargetRouter().type(key);
    }

    /**
     * Find all keys matching the given {@code pattern}.
     *
     * @param pattern must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/keys">Redis Documentation: KEYS</a>
     */
    @Override
    public Set<String> keys(String pattern) {
        return this.determineTargetRouter().keys(pattern);
    }

    /**
     * Use a {@link Cursor} to iterate over keys. <br />
     * <strong>Important:</strong> Call {@link Cursor#close()} when done to avoid resource leaks.
     *
     * @param options must not be {@literal null}.
     * @return the result cursor providing access to the scan result. Must be closed once fully processed (e.g. through a
     * try-with-resources clause).
     * @see <a href="https://redis.io/commands/scan">Redis Documentation: SCAN</a>
     * @since 2.7
     */
    @Override
    public Cursor<String> scan(ScanOptions options) {
        return this.determineTargetRouter().scan(options);
    }

    /**
     * Return a random key from the keyspace.
     *
     * @return {@literal null} no keys exist or when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/randomkey">Redis Documentation: RANDOMKEY</a>
     */
    @Override
    public String randomKey() {
        return this.determineTargetRouter().randomKey();
    }

    /**
     * Rename key {@code oldKey} to {@code newKey}.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @see <a href="https://redis.io/commands/rename">Redis Documentation: RENAME</a>
     */
    @Override
    public void rename(String oldKey, String newKey) {
        this.determineTargetRouter().rename(oldKey, newKey);

    }

    /**
     * Rename key {@code oldKey} to {@code newKey} only if {@code newKey} does not exist.
     *
     * @param oldKey must not be {@literal null}.
     * @param newKey must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/renamenx">Redis Documentation: RENAMENX</a>
     */
    @Override
    public Boolean renameIfAbsent(String oldKey, String newKey) {
        return this.determineTargetRouter().renameIfAbsent(oldKey, newKey);
    }

    /**
     * Set time to live for given {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param timeout
     * @param unit    must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return this.determineTargetRouter().expire(key, timeout, unit);
    }

    /**
     * Set the expiration for given {@code key} as a {@literal date} timestamp.
     *
     * @param key  must not be {@literal null}.
     * @param date must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     */
    @Override
    public Boolean expireAt(String key, Date date) {
        return this.determineTargetRouter().expireAt(key, date);
    }

    /**
     * Remove the expiration from given {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/persist">Redis Documentation: PERSIST</a>
     */
    @Override
    public Boolean persist(String key) {
        return this.determineTargetRouter().persist(key);
    }

    /**
     * Get the time to live for {@code key} in seconds.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/ttl">Redis Documentation: TTL</a>
     */
    @Override
    public Long getExpire(String key) {
        return this.determineTargetRouter().getExpire(key);
    }

    /**
     * Get the time to live for {@code key} in and convert it to the given {@link TimeUnit}.
     *
     * @param key      must not be {@literal null}.
     * @param timeUnit must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @since 1.8
     */
    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return this.determineTargetRouter().getExpire(key, timeUnit);
    }

    /**
     * Move given {@code key} to database with {@code index}.
     *
     * @param key     must not be {@literal null}.
     * @param dbIndex
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/move">Redis Documentation: MOVE</a>
     */
    @Override
    public Boolean move(String key, int dbIndex) {
        return this.determineTargetRouter().move(key, dbIndex);
    }

    /**
     * Retrieve serialized version of the value stored at {@code key}.
     *
     * @param key must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/dump">Redis Documentation: DUMP</a>
     */
    @Override
    public byte[] dump(String key) {
        return this.determineTargetRouter().dump(key);
    }

    /**
     * Create {@code key} using the {@code serializedValue}, previously obtained using {@link #dump(String)}.
     *
     * @param key        must not be {@literal null}.
     * @param value      must not be {@literal null}.
     * @param timeToLive
     * @param unit       must not be {@literal null}.
     * @param replace    use {@literal true} to replace a potentially existing value instead of erroring.
     * @see <a href="https://redis.io/commands/restore">Redis Documentation: RESTORE</a>
     * @since 2.1
     */
    @Override
    public void restore(String key, byte[] value, long timeToLive, TimeUnit unit, boolean replace) {
        this.determineTargetRouter().restore(key, value, timeToLive, unit, replace);

    }

    /**
     * Sort the elements for {@code query}.
     *
     * @param query must not be {@literal null}.
     * @return the results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public List<Object> sort(SortQuery<String> query) {
        return this.determineTargetRouter().sort(query);
    }

    /**
     * Sort the elements for {@code query} applying {@link RedisSerializer}.
     *
     * @param query            must not be {@literal null}.
     * @param resultSerializer
     * @return the deserialized results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public <T> List<T> sort(SortQuery<String> query, RedisSerializer<T> resultSerializer) {
        return this.determineTargetRouter().sort(query, resultSerializer);
    }

    /**
     * Sort the elements for {@code query} applying {@link BulkMapper}.
     *
     * @param query      must not be {@literal null}.
     * @param bulkMapper
     * @return the deserialized results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public <T> List<T> sort(SortQuery<String> query, BulkMapper<T, Object> bulkMapper) {
        return this.determineTargetRouter().sort(query, bulkMapper);
    }

    /**
     * Sort the elements for {@code query} applying {@link BulkMapper} and {@link RedisSerializer}.
     *
     * @param query            must not be {@literal null}.
     * @param bulkMapper
     * @param resultSerializer
     * @return the deserialized results of sort. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public <T, S> List<T> sort(SortQuery<String> query, BulkMapper<T, S> bulkMapper, RedisSerializer<S> resultSerializer) {
        return this.determineTargetRouter().sort(query, bulkMapper, resultSerializer);
    }

    /**
     * Sort the elements for {@code query} and store result in {@code storeKey}.
     *
     * @param query    must not be {@literal null}.
     * @param storeKey must not be {@literal null}.
     * @return number of values. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/sort">Redis Documentation: SORT</a>
     */
    @Override
    public Long sort(SortQuery<String> query, String storeKey) {
        return this.determineTargetRouter().sort(query, storeKey);
    }

    /**
     * Watch given {@code key} for modifications during transaction started with {@link #multi()}.
     *
     * @param key must not be {@literal null}.
     * @see <a href="https://redis.io/commands/watch">Redis Documentation: WATCH</a>
     */
    @Override
    public void watch(String key) {
        this.determineTargetRouter().watch(key);
    }

    /**
     * Watch given {@code keys} for modifications during transaction started with {@link #multi()}.
     *
     * @param keys must not be {@literal null}.
     * @see <a href="https://redis.io/commands/watch">Redis Documentation: WATCH</a>
     */
    @Override
    public void watch(Collection<String> keys) {
        this.determineTargetRouter().watch(keys);

    }

    /**
     * Flushes all the previously {@link #watch(String)} keys.
     *
     * @see <a href="https://redis.io/commands/unwatch">Redis Documentation: UNWATCH</a>
     */
    @Override
    public void unwatch() {
        this.determineTargetRouter().unwatch();
    }

    /**
     * Mark the start of a transaction block. <br>
     * Commands will be queued and can then be executed by calling {@link #exec()} or rolled back using {@link #discard()}
     *
     * @see <a href="https://redis.io/commands/multi">Redis Documentation: MULTI</a>
     */
    @Override
    public void multi() {
        this.determineTargetRouter().multi();
    }

    /**
     * Discard all commands issued after {@link #multi()}.
     *
     * @see <a href="https://redis.io/commands/discard">Redis Documentation: DISCARD</a>
     */
    @Override
    public void discard() {
        this.determineTargetRouter().discard();
    }

    /**
     * Executes all queued commands in a transaction started with {@link #multi()}. <br>
     * If used along with {@link #watch(String)} the operation will fail if any of watched keys has been modified.
     *
     * @return List of replies for each executed command.
     * @see <a href="https://redis.io/commands/exec">Redis Documentation: EXEC</a>
     */
    @Override
    public List<Object> exec() {
        return this.determineTargetRouter().exec();
    }

    /**
     * Execute a transaction, using the provided {@link RedisSerializer} to deserialize any results that are byte[]s or
     * Collections of byte[]s. If a result is a Map, the provided {@link RedisSerializer} will be used for both the keys
     * and values. Other result types (Long, Boolean, etc) are left as-is in the converted results. Tuple results are
     * automatically converted to TypedTuples.
     *
     * @param valueSerializer The {@link RedisSerializer} to use for deserializing the results of transaction exec
     * @return The deserialized results of transaction exec
     */
    @Override
    public List<Object> exec(RedisSerializer<?> valueSerializer) {
        return this.determineTargetRouter().exec(valueSerializer);
    }

    /**
     * Request information and statistics about connected clients.
     *
     * @return {@link List} of {@link RedisClientInfo} objects.
     * @since 1.3
     */
    @Override
    public List<RedisClientInfo> getClientList() {
        return this.determineTargetRouter().getClientList();
    }

    /**
     * Closes a given client connection identified by {@literal ip:port} given in {@code client}.
     *
     * @param host of connection to close.
     * @param port of connection to close
     * @since 1.3
     */
    @Override
    public void killClient(String host, int port) {
        this.determineTargetRouter().killClient(host, port);
    }

    /**
     * Change redis replication setting to new master.
     *
     * @param host must not be {@literal null}.
     * @param port
     * @see <a href="https://redis.io/commands/replicaof">Redis Documentation: REPLICAOF</a>
     * @since 1.3
     */
    @Override
    public void replicaOf(String host, int port) {
        this.determineTargetRouter().replicaOf(host, port);
    }

    /**
     * Change server into master.
     *
     * @see <a href="https://redis.io/commands/replicaof">Redis Documentation: REPLICAOF</a>
     * @since 1.3
     */
    @Override
    public void replicaOfNoOne() {
        this.determineTargetRouter().replicaOfNoOne();
    }

    /**
     * Publishes the given message to the given channel.
     *
     * @param destination the channel to publish to, must not be {@literal null}.
     * @param message     message to publish.
     * @return the number of clients that received the message. {@literal null} when used in pipeline / transaction.
     * @see <a href="https://redis.io/commands/publish">Redis Documentation: PUBLISH</a>
     */
    @Override
    public Long convertAndSend(String destination, Object message) {
        return this.determineTargetRouter().convertAndSend(destination, message);
    }

    /**
     * Returns the cluster specific operations interface.
     *
     * @return never {@literal null}.
     * @since 1.7
     */
    @Override
    public ClusterOperations<String, Object> opsForCluster() {
        return this.determineTargetRouter().opsForCluster();
    }

    /**
     * Returns geospatial specific operations interface.
     *
     * @return never {@literal null}.
     * @since 1.8
     */
    @Override
    public GeoOperations<String, Object> opsForGeo() {
        return this.determineTargetRouter().opsForGeo();
    }

    /**
     * Returns geospatial specific operations interface bound to the given key.
     *
     * @param key must not be {@literal null}.
     * @return never {@literal null}.
     * @since 1.8
     */
    @Override
    public BoundGeoOperations<String, Object> boundGeoOps(String key) {
        return this.determineTargetRouter().boundGeoOps(key);
    }

    /**
     * Returns the operations performed on hash values.
     *
     * @return hash operations
     */
    @Override
    public <HK, HV> HashOperations<String, HK, HV> opsForHash() {
        return this.determineTargetRouter().opsForHash();
    }

    /**
     * Returns the operations performed on hash values bound to the given key.
     *
     * @param key Redis key
     * @return hash operations bound to the given key.
     */
    @Override
    public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String key) {
        return this.determineTargetRouter().boundHashOps(key);
    }

    /**
     * @return
     * @since 1.5
     */
    @Override
    public HyperLogLogOperations<String, Object> opsForHyperLogLog() {
        return this.determineTargetRouter().opsForHyperLogLog();
    }

    /**
     * Returns the operations performed on list values.
     *
     * @return list operations
     */
    @Override
    public ListOperations<String, Object> opsForList() {
        return this.determineTargetRouter().opsForList();
    }

    /**
     * Returns the operations performed on list values bound to the given key.
     *
     * @param key Redis key
     * @return list operations bound to the given key
     */
    @Override
    public BoundListOperations<String, Object> boundListOps(String key) {
        return this.determineTargetRouter().boundListOps(key);
    }

    /**
     * Returns the operations performed on set values.
     *
     * @return set operations
     */
    @Override
    public SetOperations<String, Object> opsForSet() {
        return this.determineTargetRouter().opsForSet();
    }

    /**
     * Returns the operations performed on set values bound to the given key.
     *
     * @param key Redis key
     * @return set operations bound to the given key
     */
    @Override
    public BoundSetOperations<String, Object> boundSetOps(String key) {
        return this.determineTargetRouter().boundSetOps(key);
    }

    /**
     * Returns the operations performed on Streams.
     *
     * @return stream operations.
     * @since 2.2
     */
    @Override
    public <HK, HV> StreamOperations<String, HK, HV> opsForStream() {
        return this.determineTargetRouter().opsForStream();
    }

    /**
     * Returns the operations performed on Streams.
     *
     * @param hashMapper the {@link HashMapper} to use when converting {@link ObjectRecord}.
     * @return stream operations.
     * @since 2.2
     */
    @Override
    public <HK, HV> StreamOperations<String, HK, HV> opsForStream(HashMapper<? super String, ? super HK, ? super HV> hashMapper) {
        return this.determineTargetRouter().opsForStream(hashMapper);
    }

    /**
     * Returns the operations performed on Streams bound to the given key.
     *
     * @param key
     * @return stream operations.
     * @since 2.2
     */
    @Override
    public <HK, HV> BoundStreamOperations<String, HK, HV> boundStreamOps(String key) {
        return this.determineTargetRouter().boundStreamOps(key);
    }

    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology).
     *
     * @return value operations
     */
    @Override
    public ValueOperations<String, Object> opsForValue() {
        return this.determineTargetRouter().opsForValue();
    }

    /**
     * Returns the operations performed on simple values (or Strings in Redis terminology) bound to the given key.
     *
     * @param key Redis key
     * @return value operations bound to the given key
     */
    @Override
    public BoundValueOperations<String, Object> boundValueOps(String key) {
        return this.determineTargetRouter().boundValueOps(key);
    }

    /**
     * Returns the operations performed on zset values (also known as sorted sets).
     *
     * @return zset operations
     */
    @Override
    public ZSetOperations<String, Object> opsForZSet() {
        return this.determineTargetRouter().opsForZSet();
    }

    /**
     * Returns the operations performed on zset values (also known as sorted sets) bound to the given key.
     *
     * @param key Redis key
     * @return zset operations bound to the given key.
     */
    @Override
    public BoundZSetOperations<String, Object> boundZSetOps(String key) {
        return this.determineTargetRouter().boundZSetOps(key);
    }

    /**
     * @return the key {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getKeySerializer() {
        return this.determineTargetRouter().getKeySerializer();
    }

    /**
     * @return the value {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getValueSerializer() {
        return this.determineTargetRouter().getValueSerializer();
    }

    /**
     * @return the hash key {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getHashKeySerializer() {
        return this.determineTargetRouter().getHashKeySerializer();
    }

    /**
     * @return the hash value {@link RedisSerializer}.
     */
    @Override
    public RedisSerializer<?> getHashValueSerializer() {
        return this.determineTargetRouter().getHashValueSerializer();
    }

    /**
     * Set time to live for given {@code key}.
     *
     * @param key     must not be {@literal null}.
     * @param timeout must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if the timeout is {@literal null}.
     * @since 2.3
     */
    @Override
    public Boolean expire(String key, Duration timeout) {
        return this.determineTargetRouter().expire(key, timeout);
    }

    /**
     * Set the expiration for given {@code key} as a {@literal date} timestamp.
     *
     * @param key      must not be {@literal null}.
     * @param expireAt must not be {@literal null}.
     * @return {@literal null} when used in pipeline / transaction.
     * @throws IllegalArgumentException if the instant is {@literal null} or too large to represent as a {@code Date}.
     * @since 2.3
     */
    @Override
    public Boolean expireAt(String key, Instant expireAt) {
        return this.determineTargetRouter().expireAt(key, expireAt);
    }

    /**
     * Create {@code key} using the {@code serializedValue}, previously obtained using {@link #dump(String)}.
     *
     * @param key        must not be {@literal null}.
     * @param value      must not be {@literal null}.
     * @param timeToLive
     * @param unit       must not be {@literal null}.
     * @see <a href="https://redis.io/commands/restore">Redis Documentation: RESTORE</a>
     */
    @Override
    public void restore(String key, byte[] value, long timeToLive, TimeUnit unit) {
        this.determineTargetRouter().restore(key, value, timeToLive, unit);
    }

    /**
     * Returns the connectionFactory.
     *
     * @return Returns the connectionFactory. Can be {@literal null}
     */
    @Nullable
    public RedisConnectionFactory getConnectionFactory() {
        return this.determineTargetRouter().getConnectionFactory();
    }

    /**
     * Returns the required {@link RedisConnectionFactory} or throws {@link IllegalStateException} if the connection
     * factory is not set.
     *
     * @return the associated {@link RedisConnectionFactory}.
     * @throws IllegalStateException if the connection factory is not set.
     * @since 2.0
     */
    public RedisConnectionFactory getRequiredConnectionFactory() {

        RedisConnectionFactory connectionFactory = getConnectionFactory();

        if (connectionFactory == null) {
            throw new IllegalStateException("RedisConnectionFactory is required");
        }

        return connectionFactory;
    }

    /************************RedisTemplate方法封装----------end*********************************************/

}
