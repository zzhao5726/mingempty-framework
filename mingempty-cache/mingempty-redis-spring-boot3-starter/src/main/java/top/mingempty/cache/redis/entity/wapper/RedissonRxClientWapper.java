package top.mingempty.cache.redis.entity.wapper;

import org.redisson.api.BatchOptions;
import org.redisson.api.ClusterNode;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LockOptions;
import org.redisson.api.MapCacheOptions;
import org.redisson.api.MapOptions;
import org.redisson.api.Node;
import org.redisson.api.NodesGroup;
import org.redisson.api.RAtomicDoubleRx;
import org.redisson.api.RAtomicLongRx;
import org.redisson.api.RBatchRx;
import org.redisson.api.RBinaryStreamRx;
import org.redisson.api.RBitSetRx;
import org.redisson.api.RBlockingDequeRx;
import org.redisson.api.RBlockingQueueRx;
import org.redisson.api.RBloomFilterRx;
import org.redisson.api.RBucketRx;
import org.redisson.api.RBucketsRx;
import org.redisson.api.RCountDownLatchRx;
import org.redisson.api.RDequeRx;
import org.redisson.api.RFencedLockRx;
import org.redisson.api.RFunctionRx;
import org.redisson.api.RGeoRx;
import org.redisson.api.RHyperLogLogRx;
import org.redisson.api.RIdGeneratorRx;
import org.redisson.api.RJsonBucketRx;
import org.redisson.api.RKeysRx;
import org.redisson.api.RLexSortedSetRx;
import org.redisson.api.RListMultimapCacheRx;
import org.redisson.api.RListMultimapRx;
import org.redisson.api.RListRx;
import org.redisson.api.RLocalCachedMapRx;
import org.redisson.api.RLock;
import org.redisson.api.RLockRx;
import org.redisson.api.RMapCacheNativeRx;
import org.redisson.api.RMapCacheRx;
import org.redisson.api.RMapRx;
import org.redisson.api.RPatternTopicRx;
import org.redisson.api.RPermitExpirableSemaphoreRx;
import org.redisson.api.RQueueRx;
import org.redisson.api.RRateLimiterRx;
import org.redisson.api.RReadWriteLockRx;
import org.redisson.api.RReliableTopicRx;
import org.redisson.api.RRemoteService;
import org.redisson.api.RRingBufferRx;
import org.redisson.api.RScoredSortedSetRx;
import org.redisson.api.RScriptRx;
import org.redisson.api.RSearchRx;
import org.redisson.api.RSemaphoreRx;
import org.redisson.api.RSetCacheRx;
import org.redisson.api.RSetMultimapCacheRx;
import org.redisson.api.RSetMultimapRx;
import org.redisson.api.RSetRx;
import org.redisson.api.RShardedTopicRx;
import org.redisson.api.RStreamRx;
import org.redisson.api.RTimeSeriesRx;
import org.redisson.api.RTopicRx;
import org.redisson.api.RTransactionRx;
import org.redisson.api.RTransferQueueRx;
import org.redisson.api.RedissonRxClient;
import org.redisson.api.TransactionOptions;
import org.redisson.api.options.CommonOptions;
import org.redisson.api.options.JsonBucketOptions;
import org.redisson.api.options.KeysOptions;
import org.redisson.api.options.OptionalOptions;
import org.redisson.api.options.PatternTopicOptions;
import org.redisson.api.options.PlainOptions;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonCodec;
import org.redisson.config.Config;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Map;

/**
 * RedissonRxClient封装
 *
 * @author zzhao
 * @date 2023/3/12 10:56
 */
public class RedissonRxClientWapper extends AbstractRouter<RedissonRxClient> implements RedissonRxClient {
    public RedissonRxClientWapper(String defaultTargetName, Map<String, RedissonRxClient> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedissonRxClientWapper(String defaultTargetName, Map<String, RedissonRxClient> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return RedisCacheAspect.acquireCacheName();
    }

    /************************RedissonRxClient方法封装----------start*********************************************/
    /**
     * Returns time-series instance by <code>name</code>
     *
     * @param name - name of instance
     * @return RTimeSeries object
     */
    @Override
    public <V, L> RTimeSeriesRx<V, L> getTimeSeries(String name) {
        return determineTargetRouter().getTimeSeries(name);
    }

    /**
     * Returns time-series instance by <code>name</code>
     * using provided <code>codec</code> for values.
     *
     * @param name  - name of instance
     * @param codec - codec for values
     * @return RTimeSeries object
     */
    @Override
    public <V, L> RTimeSeriesRx<V, L> getTimeSeries(String name, Codec codec) {
        return determineTargetRouter().getTimeSeries(name, codec);
    }

    /**
     * Returns time-series instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return RTimeSeries object
     */
    @Override
    public <V, L> RTimeSeriesRx<V, L> getTimeSeries(PlainOptions options) {
        return determineTargetRouter().getTimeSeries(options);
    }

    /**
     * Returns stream instance by <code>name</code>
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param name of stream
     * @return RStream object
     */
    @Override
    public <K, V> RStreamRx<K, V> getStream(String name) {
        return determineTargetRouter().getStream(name);
    }

    /**
     * Returns stream instance by <code>name</code>
     * using provided <code>codec</code> for entries.
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param name  - name of stream
     * @param codec - codec for entry
     * @return RStream object
     */
    @Override
    public <K, V> RStreamRx<K, V> getStream(String name, Codec codec) {
        return determineTargetRouter().getStream(name, codec);
    }

    /**
     * Returns time-series instance with specified <code>options</code>.
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param options instance options
     * @return RStream object
     */
    @Override
    public <K, V> RStreamRx<K, V> getStream(PlainOptions options) {
        return determineTargetRouter().getStream(options);
    }

    /**
     * Returns API for RediSearch module
     *
     * @return RSearch object
     */
    @Override
    public RSearchRx getSearch() {
        return determineTargetRouter().getSearch();
    }

    /**
     * Returns API for RediSearch module using defined codec for attribute values.
     *
     * @param codec
     * @return RSearch object
     */
    @Override
    public RSearchRx getSearch(Codec codec) {
        return determineTargetRouter().getSearch();
    }

    /**
     * Returns API for RediSearch module with specified <code>options</code>.
     *
     * @param options instance options
     * @return RSearch object
     */
    @Override
    public RSearchRx getSearch(OptionalOptions options) {
        return determineTargetRouter().getSearch(options);
    }

    /**
     * Returns geospatial items holder instance by <code>name</code>.
     *
     * @param name - name of object
     * @return Geo object
     */
    @Override
    public <V> RGeoRx<V> getGeo(String name) {
        return determineTargetRouter().getGeo(name);
    }

    /**
     * Returns geospatial items holder instance by <code>name</code>
     * using provided codec for geospatial members.
     *
     * @param name  - name of object
     * @param codec - codec for value
     * @return Geo object
     */
    @Override
    public <V> RGeoRx<V> getGeo(String name, Codec codec) {
        return determineTargetRouter().getGeo(name, codec);
    }

    /**
     * Returns geospatial items holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Geo object
     */
    @Override
    public <V> RGeoRx<V> getGeo(PlainOptions options) {
        return determineTargetRouter().getGeo(options);
    }

    /**
     * Returns rate limiter instance by <code>name</code>
     *
     * @param name of rate limiter
     * @return RateLimiter object
     */
    @Override
    public RRateLimiterRx getRateLimiter(String name) {
        return determineTargetRouter().getRateLimiter(name);
    }

    /**
     * Returns rate limiter instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return RateLimiter object
     */
    @Override
    public RRateLimiterRx getRateLimiter(CommonOptions options) {
        return determineTargetRouter().getRateLimiter(options);
    }

    /**
     * Returns binary stream holder instance by <code>name</code>
     *
     * @param name of binary stream
     * @return BinaryStream object
     */
    @Override
    public RBinaryStreamRx getBinaryStream(String name) {
        return determineTargetRouter().getBinaryStream(name);
    }

    /**
     * Returns binary stream holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BinaryStream object
     */
    @Override
    public RBinaryStreamRx getBinaryStream(CommonOptions options) {
        return determineTargetRouter().getBinaryStream(options);
    }

    /**
     * Returns semaphore instance by name
     *
     * @param name - name of object
     * @return Semaphore object
     */
    @Override
    public RSemaphoreRx getSemaphore(String name) {
        return determineTargetRouter().getSemaphore(name);
    }

    /**
     * Returns semaphore instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Semaphore object
     */
    @Override
    public RSemaphoreRx getSemaphore(CommonOptions options) {
        return determineTargetRouter().getSemaphore(options);
    }

    /**
     * Returns semaphore instance by name.
     * Supports lease time parameter for each acquired permit.
     *
     * @param name - name of object
     * @return PermitExpirableSemaphore object
     */
    @Override
    public RPermitExpirableSemaphoreRx getPermitExpirableSemaphore(String name) {
        return determineTargetRouter().getPermitExpirableSemaphore(name);
    }

    /**
     * Returns semaphore instance with specified <code>options</code>.
     * Supports lease time parameter for each acquired permit.
     *
     * @param options instance options
     * @return PermitExpirableSemaphore object
     */
    @Override
    public RPermitExpirableSemaphoreRx getPermitExpirableSemaphore(CommonOptions options) {
        return determineTargetRouter().getPermitExpirableSemaphore(options);
    }

    /**
     * Returns ReadWriteLock instance by name.
     * <p>
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param name - name of object
     * @return Lock object
     */
    @Override
    public RReadWriteLockRx getReadWriteLock(String name) {
        return determineTargetRouter().getReadWriteLock(name);
    }

    /**
     * Returns ReadWriteLock instance with specified <code>options</code>.
     * <p>
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param options instance options
     * @return Lock object
     */
    @Override
    public RReadWriteLockRx getReadWriteLock(CommonOptions options) {
        return determineTargetRouter().getReadWriteLock(options);
    }

    /**
     * Returns Lock instance by name.
     * <p>
     * Implements a <b>fair</b> locking so it guarantees an acquire order by threads.
     * <p>
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param name - name of object
     * @return Lock object
     */
    @Override
    public RLockRx getFairLock(String name) {
        return determineTargetRouter().getFairLock(name);
    }

    /**
     * Returns Lock instance with specified <code>options</code>.
     * <p>
     * Implements a <b>fair</b> locking so it guarantees an acquire order by threads.
     * <p>
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param options instance options
     * @return Lock object
     */
    @Override
    public RLockRx getFairLock(CommonOptions options) {
        return determineTargetRouter().getFairLock(options);
    }

    /**
     * Returns Lock instance by name.
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
     * <p>
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param name - name of object
     * @return Lock object
     */
    @Override
    public RLockRx getLock(String name) {
        return determineTargetRouter().getLock(name);
    }

    /**
     * Returns Lock instance with specified <code>options</code>.
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
     * <p>
     * To increase reliability during failover, all operations wait for propagation to all Redis slaves.
     *
     * @param options instance options
     * @return Lock object
     */
    @Override
    public RLockRx getLock(CommonOptions options) {
        return determineTargetRouter().getLock(options);
    }

    /**
     * Returns Spin lock instance by name.
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
     * <p>
     * Lock doesn't use a pub/sub mechanism
     *
     * @param name - name of object
     * @return Lock object
     */
    @Override
    public RLockRx getSpinLock(String name) {
        return determineTargetRouter().getSpinLock(name);
    }

    /**
     * Returns Spin lock instance by name with specified back off options.
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantees an acquire order by threads.
     * <p>
     * Lock doesn't use a pub/sub mechanism
     *
     * @param name    - name of object
     * @param backOff
     * @return Lock object
     */
    @Override
    public RLockRx getSpinLock(String name, LockOptions.BackOff backOff) {
        return determineTargetRouter().getSpinLock(name, backOff);
    }

    /**
     * Returns Fenced Lock by name.
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantee an acquire order by threads.
     *
     * @param name name of object
     * @return Lock object
     */
    @Override
    public RFencedLockRx getFencedLock(String name) {
        return determineTargetRouter().getFencedLock(name);
    }

    /**
     * Returns Fenced Lock instance with specified <code>options</code>..
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantee an acquire order by threads.
     *
     * @param options instance options
     * @return Lock object
     */
    @Override
    public RFencedLockRx getFencedLock(CommonOptions options) {
        return determineTargetRouter().getFencedLock(options);
    }

    /**
     * Returns MultiLock instance associated with specified <code>locks</code>
     *
     * @param locks - collection of locks
     * @return MultiLock object
     */
    @Override
    public RLockRx getMultiLock(RLockRx... locks) {
        return determineTargetRouter().getMultiLock(locks);
    }

    /**
     * @param locks
     * @return
     */
    @Override
    @Deprecated
    public RLockRx getMultiLock(RLock... locks) {
        return determineTargetRouter().getMultiLock(locks);
    }

    /**
     * @param locks
     * @return
     */
    @Override
    @Deprecated
    public RLockRx getRedLock(RLock... locks) {
        return determineTargetRouter().getRedLock(locks);
    }

    /**
     * Returns CountDownLatch instance by name.
     *
     * @param name - name of object
     * @return CountDownLatch object
     */
    @Override
    public RCountDownLatchRx getCountDownLatch(String name) {
        return determineTargetRouter().getCountDownLatch(name);
    }

    /**
     * Returns countDownLatch instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return CountDownLatch object
     */
    @Override
    public RCountDownLatchRx getCountDownLatch(CommonOptions options) {
        return determineTargetRouter().getCountDownLatch(options);
    }

    /**
     * Returns set-based cache instance by <code>name</code>.
     * Supports value eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSet(String, Codec)}.</p>
     *
     * @param name - name of object
     * @return SetCache object
     */
    @Override
    public <V> RSetCacheRx<V> getSetCache(String name) {
        return determineTargetRouter().getSetCache(name);
    }

    /**
     * Returns set-based cache instance by <code>name</code>.
     * Supports value eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSet(String, Codec)}.</p>
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return SetCache object
     */
    @Override
    public <V> RSetCacheRx<V> getSetCache(String name, Codec codec) {
        return determineTargetRouter().getSetCache(name, codec);
    }

    /**
     * Returns set-based cache instance with specified <code>options</code>.
     * Supports value eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSet(PlainOptions)}.</p>
     *
     * @param options instance options
     * @return SetCache object
     */
    @Override
    public <V> RSetCacheRx<V> getSetCache(PlainOptions options) {
        return determineTargetRouter().getSetCache(options);
    }

    /**
     * Returns map-based cache instance by name
     * using provided codec for both cache keys and values.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, Codec)}.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return MapCache object
     */
    @Override
    public <K, V> RMapCacheRx<K, V> getMapCache(String name, Codec codec) {
        return determineTargetRouter().getMapCache(name, codec);
    }

    /**
     * Returns map-based cache instance by <code>name</code>
     * using provided <code>codec</code> for both cache keys and values.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, Codec, MapOptions)}.
     *
     * @param name    object name
     * @param codec   codec for keys and values
     * @param options map options
     * @return MapCache object
     */
    @Override
    @Deprecated
    public <K, V> RMapCacheRx<K, V> getMapCache(String name, Codec codec, MapCacheOptions<K, V> options) {
        return determineTargetRouter().getMapCache(name, codec, options);
    }


    /**
     * Returns map-based cache instance by name.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String)}.
     *
     * @param name - name of object
     * @return MapCache object
     */
    @Override
    public <K, V> RMapCacheRx<K, V> getMapCache(String name) {
        return determineTargetRouter().getMapCache(name);
    }

    /**
     * Returns map-based cache instance by name.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, MapOptions)}.</p>
     *
     * @param name    name of object
     * @param options map options
     * @return MapCache object
     */
    @Override
    @Deprecated
    public <K, V> RMapCacheRx<K, V> getMapCache(String name, MapCacheOptions<K, V> options) {
        return determineTargetRouter().getMapCache(name, options);
    }

    /**
     * Returns map-based cache instance with specified <code>options</code>.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(org.redisson.api.options.MapOptions)}.</p>
     *
     * @param options instance options
     * @return MapCache object
     */
    @Override
    public <K, V> RMapCacheRx<K, V> getMapCache(org.redisson.api.options.MapCacheOptions<K, V> options) {
        return determineTargetRouter().getMapCache(options);
    }

    /**
     * Returns map instance by name.
     * Supports entry eviction with a given TTL.
     * <p>
     * Requires <b>Redis 7.4.0 and higher.</b>
     *
     * @param name name of object
     * @return Map object
     */
    @Override
    public <K, V> RMapCacheNativeRx<K, V> getMapCacheNative(String name) {
        return determineTargetRouter().getMapCacheNative(name);
    }

    /**
     * Returns map instance by name
     * using provided codec for both map keys and values.
     * Supports entry eviction with a given TTL.
     * <p>
     * Requires <b>Redis 7.4.0 and higher.</b>
     *
     * @param name  name of object
     * @param codec codec for keys and values
     * @return Map object
     */
    @Override
    public <K, V> RMapCacheNativeRx<K, V> getMapCacheNative(String name, Codec codec) {
        return determineTargetRouter().getMapCacheNative(name, codec);
    }

    /**
     * Returns map instance.
     * Supports entry eviction with a given TTL.
     * Configured by the parameters of the options-object.
     * <p>
     * Requires <b>Redis 7.4.0 and higher.</b>
     *
     * @param options instance options
     * @return Map object
     */
    @Override
    public <K, V> RMapCacheNativeRx<K, V> getMapCacheNative(org.redisson.api.options.MapOptions<K, V> options) {
        return determineTargetRouter().getMapCacheNative(options);
    }

    /**
     * Returns object holder instance by name
     *
     * @param name - name of object
     * @return Bucket object
     */
    @Override
    public <V> RBucketRx<V> getBucket(String name) {
        return determineTargetRouter().getBucket(name);
    }

    /**
     * Returns object holder instance by name
     * using provided codec for object.
     *
     * @param name  - name of object
     * @param codec - codec for value
     * @return Bucket object
     */
    @Override
    public <V> RBucketRx<V> getBucket(String name, Codec codec) {
        return determineTargetRouter().getBucket(name, codec);
    }

    /**
     * Returns object holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Bucket object
     */
    @Override
    public <V> RBucketRx<V> getBucket(PlainOptions options) {
        return determineTargetRouter().getBucket(options);
    }

    /**
     * Returns interface for mass operations with Bucket objects.
     *
     * @return Buckets
     */
    @Override
    public RBucketsRx getBuckets() {
        return determineTargetRouter().getBuckets();
    }

    /**
     * Returns interface for mass operations with Bucket objects
     * using provided codec for object.
     *
     * @param codec - codec for bucket objects
     * @return Buckets
     */
    @Override
    public RBucketsRx getBuckets(Codec codec) {
        return determineTargetRouter().getBuckets(codec);
    }

    /**
     * Returns API for mass operations over Bucket objects with specified <code>options</code>.
     *
     * @param options instance options
     * @return Buckets object
     */
    @Override
    public RBucketsRx getBuckets(OptionalOptions options) {
        return determineTargetRouter().getBuckets(options);
    }

    /**
     * Returns JSON data holder instance by name using provided codec.
     *
     * @param name  name of object
     * @param codec codec for values
     * @return JsonBucket object
     */
    @Override
    public <V> RJsonBucketRx<V> getJsonBucket(String name, JsonCodec codec) {
        return determineTargetRouter().getJsonBucket(name, codec);
    }

    /**
     * Returns JSON data holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return JsonBucket object
     */
    @Override
    public <V> RJsonBucketRx<V> getJsonBucket(JsonBucketOptions<V> options) {
        return determineTargetRouter().getJsonBucket(options);
    }

    /**
     * Returns HyperLogLog instance by name.
     *
     * @param name - name of object
     * @return HyperLogLog object
     */
    @Override
    public <V> RHyperLogLogRx<V> getHyperLogLog(String name) {
        return determineTargetRouter().getHyperLogLog(name);
    }

    /**
     * Returns HyperLogLog instance by name
     * using provided codec for hll objects.
     *
     * @param name  - name of object
     * @param codec - codec of values
     * @return HyperLogLog object
     */
    @Override
    public <V> RHyperLogLogRx<V> getHyperLogLog(String name, Codec codec) {
        return determineTargetRouter().getHyperLogLog(name, codec);
    }

    /**
     * Returns HyperLogLog instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return HyperLogLog object
     */
    @Override
    public <V> RHyperLogLogRx<V> getHyperLogLog(PlainOptions options) {
        return determineTargetRouter().getHyperLogLog(options);
    }

    /**
     * Returns id generator by name.
     *
     * @param name - name of object
     * @return IdGenerator object
     */
    @Override
    public RIdGeneratorRx getIdGenerator(String name) {
        return determineTargetRouter().getIdGenerator(name);
    }

    /**
     * Returns id generator instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return IdGenerator object
     */
    @Override
    public RIdGeneratorRx getIdGenerator(CommonOptions options) {
        return determineTargetRouter().getIdGenerator(options);
    }

    /**
     * Returns list instance by name.
     *
     * @param name - name of object
     * @return List object
     */
    @Override
    public <V> RListRx<V> getList(String name) {
        return determineTargetRouter().getList(name);
    }

    /**
     * Returns list instance by name
     * using provided codec for list objects.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return List object
     */
    @Override
    public <V> RListRx<V> getList(String name, Codec codec) {
        return determineTargetRouter().getList(name, codec);
    }

    /**
     * Returns list instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return List object
     */
    @Override
    public <V> RListRx<V> getList(PlainOptions options) {
        return determineTargetRouter().getList(options);
    }

    /**
     * Returns List based Multimap instance by name.
     *
     * @param name - name of object
     * @return ListMultimap object
     */
    @Override
    public <K, V> RListMultimapRx<K, V> getListMultimap(String name) {
        return determineTargetRouter().getListMultimap(name);
    }

    /**
     * Returns List based Multimap instance by name
     * using provided codec for both map keys and values.
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return RListMultimapReactive object
     */
    @Override
    public <K, V> RListMultimapRx<K, V> getListMultimap(String name, Codec codec) {
        return determineTargetRouter().getListMultimap(name, codec);
    }

    /**
     * Returns List based Multimap instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return ListMultimap object
     */
    @Override
    public <K, V> RListMultimapRx<K, V> getListMultimap(PlainOptions options) {
        return determineTargetRouter().getListMultimap(options);
    }

    /**
     * Returns List based Multimap cache instance by name.
     * Supports key eviction by specifying a time to live.
     * If eviction is not required then it's better to use regular list multimap {@link #getListMultimap(String)}.
     *
     * @param name - name of object
     * @return RListMultimapCacheRx object
     */
    @Override
    public <K, V> RListMultimapCacheRx<K, V> getListMultimapCache(String name) {
        return determineTargetRouter().getListMultimapCache(name);
    }

    /**
     * Returns List based Multimap cache instance by name using provided codec for both map keys and values.
     * Supports key eviction by specifying a time to live.
     * If eviction is not required then it's better to use regular list multimap {@link #getListMultimap(String, Codec)}.
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return RListMultimapCacheRx object
     */
    @Override
    public <K, V> RListMultimapCacheRx<K, V> getListMultimapCache(String name, Codec codec) {
        return determineTargetRouter().getListMultimapCache(name, codec);
    }

    /**
     * Returns List based Multimap instance by name.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String)}.</p>
     *
     * @param options instance options
     * @return ListMultimapCache object
     */
    @Override
    public <K, V> RListMultimapCacheRx<K, V> getListMultimapCache(PlainOptions options) {
        return determineTargetRouter().getListMultimapCache(options);
    }

    /**
     * Returns Set based Multimap instance by name.
     *
     * @param name - name of object
     * @return SetMultimap object
     */
    @Override
    public <K, V> RSetMultimapRx<K, V> getSetMultimap(String name) {
        return determineTargetRouter().getSetMultimap(name);
    }

    /**
     * Returns Set based Multimap instance by name
     * using provided codec for both map keys and values.
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return SetMultimap object
     */
    @Override
    public <K, V> RSetMultimapRx<K, V> getSetMultimap(String name, Codec codec) {
        return determineTargetRouter().getSetMultimap(name, codec);
    }

    /**
     * Returns Set based Multimap instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return SetMultimap object
     */
    @Override
    public <K, V> RSetMultimapRx<K, V> getSetMultimap(PlainOptions options) {
        return determineTargetRouter().getSetMultimap(options);
    }

    /**
     * Returns Set based Multimap cache instance by name.
     * Supports key eviction by specifying a time to live.
     * If eviction is not required then it's better to use regular set multimap {@link #getSetMultimap(String)}.
     *
     * @param name - name of object
     * @return RSetMultimapCacheRx object
     */
    @Override
    public <K, V> RSetMultimapCacheRx<K, V> getSetMultimapCache(String name) {
        return determineTargetRouter().getSetMultimapCache(name);
    }

    /**
     * Returns Set based Multimap cache instance by name using provided codec for both map keys and values.
     * Supports key eviction by specifying a time to live.
     * If eviction is not required then it's better to use regular set multimap {@link #getSetMultimap(String, Codec)}.
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return RSetMultimapCacheRx object
     */
    @Override
    public <K, V> RSetMultimapCacheRx<K, V> getSetMultimapCache(String name, Codec codec) {
        return determineTargetRouter().getSetMultimapCache(name, codec);
    }

    /**
     * Returns Set based Multimap instance with specified <code>options</code>.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(PlainOptions)}.</p>
     *
     * @param options instance options
     * @return SetMultimapCache object
     */
    @Override
    public <K, V> RSetMultimapCacheRx<K, V> getSetMultimapCache(PlainOptions options) {
        return determineTargetRouter().getSetMultimapCache(options);
    }

    /**
     * Returns map instance by name.
     *
     * @param name - name of object
     * @return Map object
     */
    @Override
    public <K, V> RMapRx<K, V> getMap(String name) {
        return determineTargetRouter().getMap(name);
    }

    /**
     * Returns map instance by name.
     *
     * @param name    - name of object
     * @param options - map options
     * @return Map object
     */
    @Override
    @Deprecated
    public <K, V> RMapRx<K, V> getMap(String name, MapOptions<K, V> options) {
        return determineTargetRouter().getMap(name, options);
    }

    /**
     * Returns map instance by name
     * using provided codec for both map keys and values.
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return Map object
     */
    @Override
    public <K, V> RMapRx<K, V> getMap(String name, Codec codec) {
        return determineTargetRouter().getMap(name, codec);
    }

    /**
     * Returns map instance by name
     * using provided codec for both map keys and values.
     *
     * @param name    - name of object
     * @param codec   - codec for keys and values
     * @param options - map options
     * @return Map object
     */
    @Override
    @Deprecated
    public <K, V> RMapRx<K, V> getMap(String name, Codec codec, MapOptions<K, V> options) {
        return determineTargetRouter().getMap(name, codec, options);
    }

    /**
     * Returns map instance by name.
     *
     * @param options instance options
     * @return Map object
     */
    @Override
    public <K, V> RMapRx<K, V> getMap(org.redisson.api.options.MapOptions<K, V> options) {
        return determineTargetRouter().getMap(options);
    }

    /**
     * Returns local cached map instance by name.
     * Configured by parameters of options-object.
     *
     * @param name    - name of object
     * @param options - local map options
     * @return LocalCachedMap object
     */
    @Override
    @Deprecated
    public <K, V> RLocalCachedMapRx<K, V> getLocalCachedMap(String name, LocalCachedMapOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMap(name, options);
    }

    /**
     * Returns local cached map instance by name
     * using provided codec. Configured by parameters of options-object.
     *
     * @param name    - name of object
     * @param codec   - codec for keys and values
     * @param options - local map options
     * @return LocalCachedMap object
     */
    @Override
    @Deprecated
    public <K, V> RLocalCachedMapRx<K, V> getLocalCachedMap(String name, Codec codec, LocalCachedMapOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMap(name, codec, options);
    }

    /**
     * Returns local cached map instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return LocalCachedMap object
     */
    @Override
    public <K, V> RLocalCachedMapRx<K, V> getLocalCachedMap(org.redisson.api.options.LocalCachedMapOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMap(options);
    }

    /**
     * Returns set instance by name.
     *
     * @param name - name of object
     * @return Set object
     */
    @Override
    public <V> RSetRx<V> getSet(String name) {
        return determineTargetRouter().getSet(name);
    }

    /**
     * Returns set instance by name
     * using provided codec for set objects.
     *
     * @param name  - name of set
     * @param codec - codec for values
     * @return Set object
     */
    @Override
    public <V> RSetRx<V> getSet(String name, Codec codec) {
        return determineTargetRouter().getSet(name, codec);
    }

    /**
     * Returns set instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Set object
     */
    @Override
    public <V> RSetRx<V> getSet(PlainOptions options) {
        return determineTargetRouter().getSet(options);
    }

    /**
     * Returns Redis Sorted Set instance by name.
     * This sorted set sorts objects by object score.
     *
     * @param name of scored sorted set
     * @return ScoredSortedSet object
     */
    @Override
    public <V> RScoredSortedSetRx<V> getScoredSortedSet(String name) {
        return determineTargetRouter().getScoredSortedSet(name);
    }

    /**
     * Returns Redis Sorted Set instance by name
     * using provided codec for sorted set objects.
     * This sorted set sorts objects by object score.
     *
     * @param name  - name of scored sorted set
     * @param codec - codec for values
     * @return ScoredSortedSet object
     */
    @Override
    public <V> RScoredSortedSetRx<V> getScoredSortedSet(String name, Codec codec) {
        return determineTargetRouter().getScoredSortedSet(name, codec);
    }

    /**
     * Returns Redis Sorted Set instance with specified <code>options</code>.
     * This sorted set sorts objects by object score.
     *
     * @param options instance options
     * @return ScoredSortedSet object
     */
    @Override
    public <V> RScoredSortedSetRx<V> getScoredSortedSet(PlainOptions options) {
        return determineTargetRouter().getScoredSortedSet(options);
    }

    /**
     * Returns String based Redis Sorted Set instance by name
     * All elements are inserted with the same score during addition,
     * in order to force lexicographical ordering
     *
     * @param name - name of object
     * @return LexSortedSet object
     */
    @Override
    public RLexSortedSetRx getLexSortedSet(String name) {
        return determineTargetRouter().getLexSortedSet(name);
    }

    /**
     * Returns String based Redis Sorted Set instance with specified <code>options</code>.
     * All elements are inserted with the same score during addition,
     * in order to force lexicographical ordering
     *
     * @param options instance options
     * @return LexSortedSet object
     */
    @Override
    public RLexSortedSetRx getLexSortedSet(CommonOptions options) {
        return determineTargetRouter().getLexSortedSet(options);
    }

    /**
     * Returns Sharded Topic instance by name.
     * <p>
     * Messages are delivered to message listeners connected to the same Topic.
     * <p>
     *
     * @param name - name of object
     * @return Topic object
     */
    @Override
    public RShardedTopicRx getShardedTopic(String name) {
        return determineTargetRouter().getShardedTopic(name);
    }

    /**
     * Returns Sharded Topic instance by name using provided codec for messages.
     * <p>
     * Messages are delivered to message listeners connected to the same Topic.
     * <p>
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Topic object
     */
    @Override
    public RShardedTopicRx getShardedTopic(String name, Codec codec) {
        return determineTargetRouter().getShardedTopic(name, codec);
    }

    /**
     * Returns Sharded Topic instance with specified <code>options</code>.
     * <p>
     * Messages are delivered to message listeners connected to the same Topic.
     * <p>
     *
     * @param options instance options
     * @return Topic object
     */
    @Override
    public RShardedTopicRx getShardedTopic(PlainOptions options) {
        return determineTargetRouter().getShardedTopic(options);
    }

    /**
     * Returns topic instance by name.
     *
     * @param name - name of object
     * @return Topic object
     */
    @Override
    public RTopicRx getTopic(String name) {
        return determineTargetRouter().getTopic(name);
    }

    /**
     * Returns topic instance by name
     * using provided codec for messages.
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Topic object
     */
    @Override
    public RTopicRx getTopic(String name, Codec codec) {
        return determineTargetRouter().getTopic(name, codec);
    }

    /**
     * Returns topic instance with specified <code>options</code>.
     * <p>
     * Messages are delivered to message listeners connected to the same Topic.
     * <p>
     *
     * @param options instance options
     * @return Topic object
     */
    @Override
    public RTopicRx getTopic(PlainOptions options) {
        return determineTargetRouter().getTopic(options);
    }

    /**
     * Returns reliable topic instance by name.
     * <p>
     * Dedicated Redis connection is allocated per instance (subscriber) of this object.
     * Messages are delivered to all listeners attached to the same Redis setup.
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param name - name of object
     * @return ReliableTopic object
     */
    @Override
    public RReliableTopicRx getReliableTopic(String name) {
        return determineTargetRouter().getReliableTopic(name);
    }

    /**
     * Returns reliable topic instance by name
     * using provided codec for messages.
     * <p>
     * Dedicated Redis connection is allocated per instance (subscriber) of this object.
     * Messages are delivered to all listeners attached to the same Redis setup.
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return ReliableTopic object
     */
    @Override
    public RReliableTopicRx getReliableTopic(String name, Codec codec) {
        return determineTargetRouter().getReliableTopic(name, codec);
    }

    /**
     * Returns reliable topic instance with specified <code>options</code>.
     * <p>
     * Dedicated Redis connection is allocated per instance (subscriber) of this object.
     * Messages are delivered to all listeners attached to the same Redis setup.
     * <p>
     * Requires <b>Redis 5.0.0 and higher.</b>
     *
     * @param options instance options
     * @return ReliableTopic object
     */
    @Override
    public RReliableTopicRx getReliableTopic(PlainOptions options) {
        return determineTargetRouter().getReliableTopic(options);
    }

    /**
     * Returns topic instance satisfies by pattern name.
     * <p>
     * Supported glob-style patterns:
     * h?llo subscribes to hello, hallo and hxllo
     * h*llo subscribes to hllo and heeeello
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern of the topic
     * @return PatternTopic object
     */
    @Override
    public RPatternTopicRx getPatternTopic(String pattern) {
        return determineTargetRouter().getPatternTopic(pattern);
    }

    /**
     * Returns topic instance satisfies by pattern name
     * using provided codec for messages.
     * <p>
     * Supported glob-style patterns:
     * h?llo subscribes to hello, hallo and hxllo
     * h*llo subscribes to hllo and heeeello
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param pattern of the topic
     * @param codec   - codec for message
     * @return PatternTopic object
     */
    @Override
    public RPatternTopicRx getPatternTopic(String pattern, Codec codec) {
        return determineTargetRouter().getPatternTopic(pattern, codec);
    }

    /**
     * Returns topic instance satisfies pattern name and specified <code>options</code>..
     * <p>
     * Supported glob-style patterns:
     * h?llo subscribes to hello, hallo and hxllo
     * h*llo subscribes to hllo and heeeello
     * h[ae]llo subscribes to hello and hallo, but not hillo
     *
     * @param options instance options
     * @return PatterTopic object
     */
    @Override
    public RPatternTopicRx getPatternTopic(PatternTopicOptions options) {
        return determineTargetRouter().getPatternTopic(options);
    }

    /**
     * Returns queue instance by name.
     *
     * @param name - name of object
     * @return Queue object
     */
    @Override
    public <V> RQueueRx<V> getQueue(String name) {
        return determineTargetRouter().getQueue(name);
    }

    /**
     * Returns queue instance by name
     * using provided codec for queue objects.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return Queue object
     */
    @Override
    public <V> RQueueRx<V> getQueue(String name, Codec codec) {
        return determineTargetRouter().getQueue(name, codec);
    }

    /**
     * Returns unbounded queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return queue object
     */
    @Override
    public <V> RQueueRx<V> getQueue(PlainOptions options) {
        return determineTargetRouter().getQueue(options);
    }

    /**
     * Returns RingBuffer based queue.
     *
     * @param name - name of object
     * @return RingBuffer object
     */
    @Override
    public <V> RRingBufferRx<V> getRingBuffer(String name) {
        return determineTargetRouter().getRingBuffer(name);
    }

    /**
     * Returns RingBuffer based queue.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return RingBuffer object
     */
    @Override
    public <V> RRingBufferRx<V> getRingBuffer(String name, Codec codec) {
        return determineTargetRouter().getRingBuffer(name, codec);
    }

    /**
     * Returns RingBuffer based queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return RingBuffer object
     */
    @Override
    public <V> RRingBufferRx<V> getRingBuffer(PlainOptions options) {
        return determineTargetRouter().getRingBuffer(options);
    }

    /**
     * Returns blocking queue instance by name.
     *
     * @param name - name of object
     * @return BlockingQueue object
     */
    @Override
    public <V> RBlockingQueueRx<V> getBlockingQueue(String name) {
        return determineTargetRouter().getBlockingQueue(name);
    }

    /**
     * Returns blocking queue instance by name
     * using provided codec for queue objects.
     *
     * @param name  - name of object
     * @param codec - code for values
     * @return BlockingQueue object
     */
    @Override
    public <V> RBlockingQueueRx<V> getBlockingQueue(String name, Codec codec) {
        return determineTargetRouter().getBlockingQueue(name, codec);
    }

    /**
     * Returns unbounded blocking queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BlockingQueue object
     */
    @Override
    public <V> RBlockingQueueRx<V> getBlockingQueue(PlainOptions options) {
        return determineTargetRouter().getBlockingQueue(options);
    }

    /**
     * Returns unbounded blocking deque instance by name.
     *
     * @param name - name of object
     * @return BlockingDeque object
     */
    @Override
    public <V> RBlockingDequeRx<V> getBlockingDeque(String name) {
        return determineTargetRouter().getBlockingDeque(name);
    }

    /**
     * Returns unbounded blocking deque instance by name
     * using provided codec for deque objects.
     *
     * @param name  - name of object
     * @param codec - deque objects codec
     * @return BlockingDeque object
     */
    @Override
    public <V> RBlockingDequeRx<V> getBlockingDeque(String name, Codec codec) {
        return determineTargetRouter().getBlockingDeque(name, codec);
    }

    /**
     * Returns unbounded blocking deque instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BlockingDeque object
     */
    @Override
    public <V> RBlockingDequeRx<V> getBlockingDeque(PlainOptions options) {
        return determineTargetRouter().getBlockingDeque(options);
    }

    /**
     * Returns transfer queue instance by name.
     *
     * @param name - name of object
     * @return TransferQueue object
     */
    @Override
    public <V> RTransferQueueRx<V> getTransferQueue(String name) {
        return determineTargetRouter().getTransferQueue(name);
    }

    /**
     * Returns transfer queue instance by name
     * using provided codec for queue objects.
     *
     * @param name  - name of object
     * @param codec - code for values
     * @return TransferQueue object
     */
    @Override
    public <V> RTransferQueueRx<V> getTransferQueue(String name, Codec codec) {
        return determineTargetRouter().getTransferQueue(name, codec);
    }

    /**
     * Returns transfer queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return TransferQueue object
     */
    @Override
    public <V> RTransferQueueRx<V> getTransferQueue(PlainOptions options) {
        return determineTargetRouter().getTransferQueue(options);
    }

    /**
     * Returns deque instance by name.
     *
     * @param name - name of object
     * @return Deque object
     */
    @Override
    public <V> RDequeRx<V> getDeque(String name) {
        return determineTargetRouter().getDeque(name);
    }

    /**
     * Returns deque instance by name
     * using provided codec for deque objects.
     *
     * @param name  - name of object
     * @param codec - coded for values
     * @return Deque object
     */
    @Override
    public <V> RDequeRx<V> getDeque(String name, Codec codec) {
        return determineTargetRouter().getDeque(name, codec);
    }

    /**
     * Returns unbounded deque instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Deque object
     */
    @Override
    public <V> RDequeRx<V> getDeque(PlainOptions options) {
        return determineTargetRouter().getDeque(options);
    }

    /**
     * Returns "atomic long" instance by name.
     *
     * @param name of the "atomic long"
     * @return AtomicLong object
     */
    @Override
    public RAtomicLongRx getAtomicLong(String name) {
        return determineTargetRouter().getAtomicLong(name);
    }

    /**
     * Returns atomicLong instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return AtomicLong object
     */
    @Override
    public RAtomicLongRx getAtomicLong(CommonOptions options) {
        return determineTargetRouter().getAtomicLong(options);
    }

    /**
     * Returns "atomic double" instance by name.
     *
     * @param name of the "atomic double"
     * @return AtomicLong object
     */
    @Override
    public RAtomicDoubleRx getAtomicDouble(String name) {
        return determineTargetRouter().getAtomicDouble(name);
    }

    /**
     * Returns atomicDouble instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return AtomicDouble object
     */
    @Override
    public RAtomicDoubleRx getAtomicDouble(CommonOptions options) {
        return determineTargetRouter().getAtomicDouble(options);
    }

    /**
     * Returns object for remote operations prefixed with the default name (redisson_remote_service)
     *
     * @return RemoteService object
     */
    @Override
    @Deprecated
    public RRemoteService getRemoteService() {
        return determineTargetRouter().getRemoteService();
    }

    /**
     * Returns object for remote operations prefixed with the default name (redisson_remote_service)
     * and uses provided codec for method arguments and result.
     *
     * @param codec - codec for response and request
     * @return RemoteService object
     */
    @Override
    @Deprecated
    public RRemoteService getRemoteService(Codec codec) {
        return determineTargetRouter().getRemoteService(codec);
    }

    /**
     * Returns object for remote operations prefixed with the specified name
     *
     * @param name - the name used as the Redis key prefix for the services
     * @return RemoteService object
     */
    @Override
    public RRemoteService getRemoteService(String name) {
        return determineTargetRouter().getRemoteService(name);
    }

    /**
     * Returns object for remote operations prefixed with the specified name
     * and uses provided codec for method arguments and result.
     *
     * @param name  - the name used as the Redis key prefix for the services
     * @param codec - codec for response and request
     * @return RemoteService object
     */
    @Override
    public RRemoteService getRemoteService(String name, Codec codec) {
        return determineTargetRouter().getRemoteService(name, codec);
    }

    /**
     * Returns object for remote operations prefixed with specified <code>options</code>.
     *
     * @param options instance options
     * @return RemoteService object
     */
    @Override
    public RRemoteService getRemoteService(PlainOptions options) {
        return determineTargetRouter().getRemoteService(options);
    }

    /**
     * Returns bitSet instance by name.
     *
     * @param name - name of object
     * @return BitSet object
     */
    @Override
    public RBitSetRx getBitSet(String name) {
        return determineTargetRouter().getBitSet(name);
    }

    /**
     * Returns bitSet instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BitSet object
     */
    @Override
    public RBitSetRx getBitSet(CommonOptions options) {
        return determineTargetRouter().getBitSet(options);
    }

    /**
     * Returns bloom filter instance by name.
     *
     * @param name name of object
     * @return BloomFilter object
     */
    @Override
    public <V> RBloomFilterRx<V> getBloomFilter(String name) {
        return determineTargetRouter().getBloomFilter(name);
    }

    /**
     * Returns bloom filter instance by name
     * using provided codec for objects.
     *
     * @param name  name of object
     * @param codec codec for values
     * @return BloomFilter object
     */
    @Override
    public <V> RBloomFilterRx<V> getBloomFilter(String name, Codec codec) {
        return determineTargetRouter().getBloomFilter(name, codec);
    }

    /**
     * Returns bloom filter instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BloomFilter object
     */
    @Override
    public <V> RBloomFilterRx<V> getBloomFilter(PlainOptions options) {
        return determineTargetRouter().getBloomFilter(options);
    }

    /**
     * Returns interface for Redis Function feature
     *
     * @return function object
     */
    @Override
    public RFunctionRx getFunction() {
        return determineTargetRouter().getFunction();
    }

    /**
     * Returns interface for Redis Function feature using provided codec
     *
     * @param codec - codec for params and result
     * @return function interface
     */
    @Override
    public RFunctionRx getFunction(Codec codec) {
        return determineTargetRouter().getFunction(codec);
    }

    /**
     * Returns interface for Redis Function feature with specified <code>options</code>.
     *
     * @param options instance options
     * @return function object
     */
    @Override
    public RFunctionRx getFunction(OptionalOptions options) {
        return determineTargetRouter().getFunction(options);
    }

    /**
     * Returns script operations object
     *
     * @return Script object
     */
    @Override
    public RScriptRx getScript() {
        return determineTargetRouter().getScript();
    }

    /**
     * Returns script operations object using provided codec.
     *
     * @param codec - codec for params and result
     * @return Script object
     */
    @Override
    public RScriptRx getScript(Codec codec) {
        return determineTargetRouter().getScript(codec);
    }

    /**
     * Returns script operations object with specified <code>options</code>.
     *
     * @param options instance options
     * @return Script object
     */
    @Override
    public RScriptRx getScript(OptionalOptions options) {
        return determineTargetRouter().getScript(options);
    }

    /**
     * Creates transaction with <b>READ_COMMITTED</b> isolation level.
     *
     * @param options - transaction configuration
     * @return Transaction object
     */
    @Override
    public RTransactionRx createTransaction(TransactionOptions options) {
        return determineTargetRouter().createTransaction(options);
    }

    /**
     * Return batch object which executes group of
     * command in pipeline.
     * <p>
     * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
     *
     * @param options - batch configuration
     * @return Batch object
     */
    @Override
    public RBatchRx createBatch(BatchOptions options) {
        return determineTargetRouter().createBatch(options);
    }

    /**
     * Return batch object which executes group of
     * command in pipeline.
     * <p>
     * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
     *
     * @return Batch object
     */
    @Override
    public RBatchRx createBatch() {
        return determineTargetRouter().createBatch();
    }

    /**
     * Returns keys operations.
     * Each of Redis/Redisson object associated with own key
     *
     * @return Keys object
     */
    @Override
    public RKeysRx getKeys() {
        return determineTargetRouter().getKeys();
    }

    /**
     * Returns interface for operations over Redis keys with specified <code>options</code>.
     * Each of Redis/Redisson object is associated with own key.
     *
     * @param options
     * @return Keys object
     */
    @Override
    public RKeysRx getKeys(KeysOptions options) {
        return determineTargetRouter().getKeys(options);
    }

    /**
     * Shuts down Redisson instance <b>NOT</b> Redis server
     */
    @Override
    @Deprecated
    public void shutdown() {
        determineTargetRouter().shutdown();
    }

    /**
     * Allows to get configuration provided
     * during Redisson instance creation. Further changes on
     * this object not affect Redisson instance.
     *
     * @return Config object
     */
    @Override
    public Config getConfig() {
        return determineTargetRouter().getConfig();
    }

    /**
     * Get Redis nodes group for server operations
     *
     * @return NodesGroup object
     */
    @Override
    @Deprecated
    public NodesGroup<Node> getNodesGroup() {
        return determineTargetRouter().getNodesGroup();
    }

    /**
     * Get Redis cluster nodes group for server operations
     *
     * @return NodesGroup object
     */
    @Override
    @Deprecated
    public NodesGroup<ClusterNode> getClusterNodesGroup() {
        return determineTargetRouter().getClusterNodesGroup();
    }

    /**
     * Returns {@code true} if this Redisson instance has been shut down.
     *
     * @return <code>true</code> if this Redisson instance has been shut down otherwise <code>false</code>
     */
    @Override
    public boolean isShutdown() {
        return determineTargetRouter().isShutdown();
    }

    /**
     * Returns {@code true} if this Redisson instance was started to be shutdown
     * or was shutdown {@link #isShutdown()} already.
     *
     * @return <code>true</code> if this Redisson instance was started to be shutdown
     * or was shutdown {@link #isShutdown()} already otherwise <code>false</code>
     */
    @Override
    public boolean isShuttingDown() {
        return determineTargetRouter().isShuttingDown();
    }

    /**
     * Returns id of this Redisson instance
     *
     * @return id
     */
    @Override
    public String getId() {
        return determineTargetRouter().getId();
    }
    /************************RedissonClient方法封装----------end*********************************************/

}
