package top.mingempty.cache.redis.entity.wapper;

import org.redisson.api.BatchOptions;
import org.redisson.api.ClusterNodesGroup;
import org.redisson.api.ExecutorOptions;
import org.redisson.api.LocalCachedMapCacheOptions;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.LockOptions;
import org.redisson.api.MapCacheOptions;
import org.redisson.api.MapOptions;
import org.redisson.api.Node;
import org.redisson.api.NodesGroup;
import org.redisson.api.RAtomicDouble;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RBatch;
import org.redisson.api.RBinaryStream;
import org.redisson.api.RBitSet;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RBucket;
import org.redisson.api.RBuckets;
import org.redisson.api.RClientSideCaching;
import org.redisson.api.RCountDownLatch;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RDeque;
import org.redisson.api.RDoubleAdder;
import org.redisson.api.RFencedLock;
import org.redisson.api.RFunction;
import org.redisson.api.RGeo;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RIdGenerator;
import org.redisson.api.RJsonBucket;
import org.redisson.api.RJsonBuckets;
import org.redisson.api.RKeys;
import org.redisson.api.RLexSortedSet;
import org.redisson.api.RList;
import org.redisson.api.RListMultimap;
import org.redisson.api.RListMultimapCache;
import org.redisson.api.RListMultimapCacheNative;
import org.redisson.api.RLiveObjectService;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RLocalCachedMapCache;
import org.redisson.api.RLock;
import org.redisson.api.RLongAdder;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RMapCacheNative;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RPriorityBlockingDeque;
import org.redisson.api.RPriorityBlockingQueue;
import org.redisson.api.RPriorityDeque;
import org.redisson.api.RPriorityQueue;
import org.redisson.api.RQueue;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RReliableTopic;
import org.redisson.api.RRemoteService;
import org.redisson.api.RRingBuffer;
import org.redisson.api.RScheduledExecutorService;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RScript;
import org.redisson.api.RSearch;
import org.redisson.api.RSemaphore;
import org.redisson.api.RSet;
import org.redisson.api.RSetCache;
import org.redisson.api.RSetMultimap;
import org.redisson.api.RSetMultimapCache;
import org.redisson.api.RSetMultimapCacheNative;
import org.redisson.api.RShardedTopic;
import org.redisson.api.RSortedSet;
import org.redisson.api.RStream;
import org.redisson.api.RTimeSeries;
import org.redisson.api.RTopic;
import org.redisson.api.RTransaction;
import org.redisson.api.RTransferQueue;
import org.redisson.api.RedissonClient;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.api.RedissonRxClient;
import org.redisson.api.TransactionOptions;
import org.redisson.api.options.ClientSideCachingOptions;
import org.redisson.api.options.CommonOptions;
import org.redisson.api.options.JsonBucketOptions;
import org.redisson.api.options.KeysOptions;
import org.redisson.api.options.LiveObjectOptions;
import org.redisson.api.options.OptionalOptions;
import org.redisson.api.options.PatternTopicOptions;
import org.redisson.api.options.PlainOptions;
import org.redisson.api.redisnode.BaseRedisNodes;
import org.redisson.api.redisnode.RedisNodes;
import org.redisson.client.codec.Codec;
import org.redisson.codec.JsonCodec;
import org.redisson.config.Config;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * RedissonClient封装
 *
 * @author zzhao
 * @date 2023/3/12 10:56
 */
public class RedissonClientWrapper extends AbstractRouter<RedissonClient> implements RedissonClient {
    public RedissonClientWrapper(String defaultTargetName, Map<String, RedissonClient> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedissonClientWrapper(String defaultTargetName, Map<String, RedissonClient> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return RedisCacheAspect.acquireCacheName();
    }

    /************************RedissonClient方法封装----------start*********************************************/
    /**
     * Returns time-series instance by <code>name</code>
     *
     * @param name - name of instance
     * @return RTimeSeries object
     */
    @Override
    public <V, L> RTimeSeries<V, L> getTimeSeries(String name) {
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
    public <V, L> RTimeSeries<V, L> getTimeSeries(String name, Codec codec) {
        return determineTargetRouter().getTimeSeries(name, codec);
    }

    /**
     * Returns time-series instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return RTimeSeries object
     */
    @Override
    public <V, L> RTimeSeries<V, L> getTimeSeries(PlainOptions options) {
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
    public <K, V> RStream<K, V> getStream(String name) {
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
    public <K, V> RStream<K, V> getStream(String name, Codec codec) {
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
    public <K, V> RStream<K, V> getStream(PlainOptions options) {
        return determineTargetRouter().getStream(options);
    }

    /**
     * Returns API for RediSearch module
     *
     * @return RSearch object
     */
    @Override
    public RSearch getSearch() {
        return determineTargetRouter().getSearch();
    }

    /**
     * Returns API for RediSearch module using defined codec for attribute values.
     *
     * @param codec codec for entry
     * @return RSearch object
     */
    @Override
    public RSearch getSearch(Codec codec) {
        return determineTargetRouter().getSearch(codec);
    }

    /**
     * Returns API for RediSearch module with specified <code>options</code>.
     *
     * @param options instance options
     * @return RSearch object
     */
    @Override
    public RSearch getSearch(OptionalOptions options) {
        return determineTargetRouter().getSearch(options);
    }

    /**
     * Returns rate limiter instance by <code>name</code>
     *
     * @param name of rate limiter
     * @return RateLimiter object
     */
    @Override
    public RRateLimiter getRateLimiter(String name) {
        return determineTargetRouter().getRateLimiter(name);
    }

    /**
     * Returns rate limiter instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return RateLimiter object
     */
    @Override
    public RRateLimiter getRateLimiter(CommonOptions options) {
        return determineTargetRouter().getRateLimiter(options);
    }

    /**
     * Returns binary stream holder instance by <code>name</code>
     *
     * @param name of binary stream
     * @return BinaryStream object
     */
    @Override
    public RBinaryStream getBinaryStream(String name) {
        return determineTargetRouter().getBinaryStream(name);
    }

    /**
     * Returns binary stream holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BinaryStream object
     */
    @Override
    public RBinaryStream getBinaryStream(CommonOptions options) {
        return determineTargetRouter().getBinaryStream(options);
    }

    /**
     * Returns geospatial items holder instance by <code>name</code>.
     *
     * @param name - name of object
     * @return Geo object
     */
    @Override
    public <V> RGeo<V> getGeo(String name) {
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
    public <V> RGeo<V> getGeo(String name, Codec codec) {
        return determineTargetRouter().getGeo(name, codec);
    }

    /**
     * Returns geospatial items holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Geo object
     */
    @Override
    public <V> RGeo<V> getGeo(PlainOptions options) {
        return determineTargetRouter().getGeo(options);
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
    public <V> RSetCache<V> getSetCache(String name) {
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
    public <V> RSetCache<V> getSetCache(String name, Codec codec) {
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
    public <V> RSetCache<V> getSetCache(PlainOptions options) {
        return determineTargetRouter().getSetCache(options);
    }

    /**
     * Returns map-based cache instance by <code>name</code>
     * using provided <code>codec</code> for both cache keys and values.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, Codec)}.
     *
     * @param name  - object name
     * @param codec - codec for keys and values
     * @return MapCache object
     */
    @Override
    public <K, V> RMapCache<K, V> getMapCache(String name, Codec codec) {
        return determineTargetRouter().getMapCache(name, codec);
    }

    /**
     * Returns map-based cache instance by <code>name</code>
     * using provided <code>codec</code> for both cache keys and values.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String, Codec)}.
     *
     * @param name    object name
     * @param codec   codec for keys and values
     * @param options map options
     * @return MapCache object
     */
    @Override
    @Deprecated
    public <K, V> RMapCache<K, V> getMapCache(String name, Codec codec, MapCacheOptions<K, V> options) {
        return determineTargetRouter().getMapCache(name, codec, options);
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
    public <K, V> RMapCache<K, V> getMapCache(org.redisson.api.options.MapCacheOptions<K, V> options) {
        return determineTargetRouter().getMapCache(options);
    }

    /**
     * Returns map-based cache instance by name.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String)}.</p>
     *
     * @param name - name of object
     * @return MapCache object
     */
    @Override
    public <K, V> RMapCache<K, V> getMapCache(String name) {
        return determineTargetRouter().getMapCache(name);
    }

    /**
     * Returns map-based cache instance by name.
     * Supports entry eviction with a given MaxIdleTime and TTL settings.
     * <p>
     * If eviction is not required then it's better to use regular map {@link #getMap(String)}.</p>
     *
     * @param name    name of object
     * @param options map options
     * @return MapCache object
     */
    @Override
    @Deprecated
    public <K, V> RMapCache<K, V> getMapCache(String name, MapCacheOptions<K, V> options) {
        return determineTargetRouter().getMapCache(name, options);
    }

    /**
     * Returns object holder instance by name.
     *
     * @param name - name of object
     * @return Bucket object
     */
    @Override
    public <V> RBucket<V> getBucket(String name) {
        return determineTargetRouter().getBucket(name);
    }

    /**
     * Returns object holder instance by name
     * using provided codec for object.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return Bucket object
     */
    @Override
    public <V> RBucket<V> getBucket(String name, Codec codec) {
        return determineTargetRouter().getBucket(name, codec);
    }

    /**
     * Returns object holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Bucket object
     */
    @Override
    public <V> RBucket<V> getBucket(PlainOptions options) {
        return determineTargetRouter().getBucket(options);
    }

    /**
     * Returns interface for mass operations with Bucket objects.
     *
     * @return Buckets
     */
    @Override
    public RBuckets getBuckets() {
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
    public RBuckets getBuckets(Codec codec) {
        return determineTargetRouter().getBuckets(codec);
    }

    /**
     * Returns API for mass operations over Bucket objects with specified <code>options</code>.
     *
     * @param options instance options
     * @return Buckets object
     */
    @Override
    public RBuckets getBuckets(OptionalOptions options) {
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
    public <V> RJsonBucket<V> getJsonBucket(String name, JsonCodec codec) {
        return determineTargetRouter().getJsonBucket(name, codec);
    }

    /**
     * Returns JSON data holder instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return JsonBucket object
     */
    @Override
    public <V> RJsonBucket<V> getJsonBucket(JsonBucketOptions<V> options) {
        return determineTargetRouter().getJsonBucket(options);
    }

    @Override
    public RJsonBuckets getJsonBuckets(JsonCodec codec) {
        return determineTargetRouter().getJsonBuckets(codec);
    }

    /**
     * Returns HyperLogLog instance by name.
     *
     * @param name - name of object
     * @return HyperLogLog object
     */
    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(String name) {
        return determineTargetRouter().getHyperLogLog(name);
    }

    /**
     * Returns HyperLogLog instance by name
     * using provided codec for hll objects.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return HyperLogLog object
     */
    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(String name, Codec codec) {
        return determineTargetRouter().getHyperLogLog(name, codec);
    }

    /**
     * Returns HyperLogLog instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return HyperLogLog object
     */
    @Override
    public <V> RHyperLogLog<V> getHyperLogLog(PlainOptions options) {
        return determineTargetRouter().getHyperLogLog(options);
    }

    /**
     * Returns list instance by name.
     *
     * @param name - name of object
     * @return List object
     */
    @Override
    public <V> RList<V> getList(String name) {
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
    public <V> RList<V> getList(String name, Codec codec) {
        return determineTargetRouter().getList(name, codec);
    }

    /**
     * Returns list instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return List object
     */
    @Override
    public <V> RList<V> getList(PlainOptions options) {
        return determineTargetRouter().getList(options);
    }

    /**
     * Returns List based Multimap instance by name.
     *
     * @param name - name of object
     * @return ListMultimap object
     */
    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(String name) {
        return determineTargetRouter().getListMultimap(name);
    }

    /**
     * Returns List based Multimap instance by name
     * using provided codec for both map keys and values.
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return ListMultimap object
     */
    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(String name, Codec codec) {
        return determineTargetRouter().getListMultimap(name, codec);
    }

    /**
     * Returns List based Multimap instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return ListMultimap object
     */
    @Override
    public <K, V> RListMultimap<K, V> getListMultimap(PlainOptions options) {
        return determineTargetRouter().getListMultimap(options);
    }

    /**
     * Returns List based Multimap instance by name.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String)}.</p>
     *
     * @param name - name of object
     * @return ListMultimapCache object
     */
    @Override
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(String name) {
        return determineTargetRouter().getListMultimapCache(name);
    }

    /**
     * Returns List based Multimap instance by name
     * using provided codec for both map keys and values.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String, Codec)}.</p>
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return ListMultimapCache object
     */
    @Override
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(String name, Codec codec) {
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
    public <K, V> RListMultimapCache<K, V> getListMultimapCache(PlainOptions options) {
        return determineTargetRouter().getListMultimapCache(options);
    }

    @Override
    public <K, V> RLocalCachedMapCache<K, V> getLocalCachedMapCache(String name, LocalCachedMapCacheOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMapCache(name, options);
    }

    @Override
    public <K, V> RLocalCachedMapCache<K, V> getLocalCachedMapCache(String name, Codec codec, LocalCachedMapCacheOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMapCache(name, codec, options);
    }

    @Override
    public <K, V> RListMultimapCacheNative<K, V> getListMultimapCacheNative(String name) {
        return determineTargetRouter().getListMultimapCacheNative(name);
    }

    @Override
    public <K, V> RListMultimapCacheNative<K, V> getListMultimapCacheNative(String name, Codec codec) {
        return determineTargetRouter().getListMultimapCacheNative(name, codec);
    }

    @Override
    public <K, V> RListMultimapCacheNative<K, V> getListMultimapCacheNative(PlainOptions options) {
        return determineTargetRouter().getListMultimapCacheNative(options);
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
    public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(String name, LocalCachedMapOptions<K, V> options) {
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
    public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(String name, Codec codec, LocalCachedMapOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMap(name, codec, options);
    }

    /**
     * Returns local cached map instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return LocalCachedMap object
     */
    @Override
    public <K, V> RLocalCachedMap<K, V> getLocalCachedMap(org.redisson.api.options.LocalCachedMapOptions<K, V> options) {
        return determineTargetRouter().getLocalCachedMap(options);
    }

    /**
     * Returns map instance by name.
     *
     * @param name - name of object
     * @return Map object
     */
    @Override
    public <K, V> RMap<K, V> getMap(String name) {
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
    public <K, V> RMap<K, V> getMap(String name, MapOptions<K, V> options) {
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
    public <K, V> RMap<K, V> getMap(String name, Codec codec) {
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
    public <K, V> RMap<K, V> getMap(String name, Codec codec, MapOptions<K, V> options) {
        return determineTargetRouter().getMap(name, codec, options);
    }

    /**
     * Returns map instance by name.
     *
     * @param options instance options
     * @return Map object
     */
    @Override
    public <K, V> RMap<K, V> getMap(org.redisson.api.options.MapOptions<K, V> options) {
        return determineTargetRouter().getMap(options);
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
    public <K, V> RMapCacheNative<K, V> getMapCacheNative(String name) {
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
    public <K, V> RMapCacheNative<K, V> getMapCacheNative(String name, Codec codec) {
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
    public <K, V> RMapCacheNative<K, V> getMapCacheNative(org.redisson.api.options.MapOptions<K, V> options) {
        return determineTargetRouter().getMapCacheNative(options);
    }

    /**
     * Returns Set based Multimap instance by name.
     *
     * @param name - name of object
     * @return SetMultimap object
     */
    @Override
    public <K, V> RSetMultimap<K, V> getSetMultimap(String name) {
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
    public <K, V> RSetMultimap<K, V> getSetMultimap(String name, Codec codec) {
        return determineTargetRouter().getSetMultimap(name, codec);
    }

    /**
     * Returns Set based Multimap instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return SetMultimap object
     */
    @Override
    public <K, V> RSetMultimap<K, V> getSetMultimap(PlainOptions options) {
        return determineTargetRouter().getSetMultimap(options);
    }

    /**
     * Returns Set based Multimap instance by name.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String)}.</p>
     *
     * @param name - name of object
     * @return SetMultimapCache object
     */
    @Override
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(String name) {
        return determineTargetRouter().getSetMultimapCache(name);
    }

    /**
     * Returns Set based Multimap instance by name
     * using provided codec for both map keys and values.
     * Supports key-entry eviction with a given TTL value.
     *
     * <p>If eviction is not required then it's better to use regular map {@link #getSetMultimap(String, Codec)}.</p>
     *
     * @param name  - name of object
     * @param codec - codec for keys and values
     * @return SetMultimapCache object
     */
    @Override
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(String name, Codec codec) {
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
    public <K, V> RSetMultimapCache<K, V> getSetMultimapCache(PlainOptions options) {
        return determineTargetRouter().getSetMultimapCache(options);
    }

    @Override
    public <K, V> RSetMultimapCacheNative<K, V> getSetMultimapCacheNative(String name) {
        return determineTargetRouter().getSetMultimapCacheNative(name);
    }

    @Override
    public <K, V> RSetMultimapCacheNative<K, V> getSetMultimapCacheNative(String name, Codec codec) {
        return determineTargetRouter().getSetMultimapCacheNative(name, codec);
    }

    @Override
    public <K, V> RSetMultimapCacheNative<K, V> getSetMultimapCacheNative(PlainOptions options) {
        return determineTargetRouter().getSetMultimapCacheNative(options);
    }

    /**
     * Returns semaphore instance by name
     *
     * @param name - name of object
     * @return Semaphore object
     */
    @Override
    public RSemaphore getSemaphore(String name) {
        return determineTargetRouter().getSemaphore(name);
    }

    /**
     * Returns semaphore instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Semaphore object
     */
    @Override
    public RSemaphore getSemaphore(CommonOptions options) {
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
    public RPermitExpirableSemaphore getPermitExpirableSemaphore(String name) {
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
    public RPermitExpirableSemaphore getPermitExpirableSemaphore(CommonOptions options) {
        return determineTargetRouter().getPermitExpirableSemaphore(options);
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
    public RLock getLock(String name) {
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
    public RLock getLock(CommonOptions options) {
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
    public RLock getSpinLock(String name) {
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
    public RLock getSpinLock(String name, LockOptions.BackOff backOff) {
        return determineTargetRouter().getSpinLock(name, backOff);
    }

    /**
     * Returns Fenced Lock instance by name.
     * <p>
     * Implements a <b>non-fair</b> locking so doesn't guarantee an acquire order by threads.
     *
     * @param name name of object
     * @return Lock object
     */
    @Override
    public RFencedLock getFencedLock(String name) {
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
    public RFencedLock getFencedLock(CommonOptions options) {
        return determineTargetRouter().getFencedLock(options);
    }

    /**
     * Returns MultiLock instance associated with specified <code>locks</code>
     *
     * @param locks - collection of locks
     * @return MultiLock object
     */
    @Override
    public RLock getMultiLock(RLock... locks) {
        return determineTargetRouter().getMultiLock(locks);
    }

    @Override
    public RLock getMultiLock(String group, Collection<Object> values) {
        return determineTargetRouter().getMultiLock(group, values);
    }

    /**
     * @param locks
     * @return
     */
    @Override
    @Deprecated
    public RLock getRedLock(RLock... locks) {
        return determineTargetRouter().getRedLock(locks);
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
    public RLock getFairLock(String name) {
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
    public RLock getFairLock(CommonOptions options) {
        return determineTargetRouter().getFairLock(options);
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
    public RReadWriteLock getReadWriteLock(String name) {
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
    public RReadWriteLock getReadWriteLock(CommonOptions options) {
        return determineTargetRouter().getReadWriteLock(options);
    }

    /**
     * Returns set instance by name.
     *
     * @param name - name of object
     * @return Set object
     */
    @Override
    public <V> RSet<V> getSet(String name) {
        return determineTargetRouter().getSet(name);
    }

    /**
     * Returns set instance by name
     * using provided codec for set objects.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return Set object
     */
    @Override
    public <V> RSet<V> getSet(String name, Codec codec) {
        return determineTargetRouter().getSet(name, codec);
    }

    /**
     * Returns set instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Set object
     */
    @Override
    public <V> RSet<V> getSet(PlainOptions options) {
        return determineTargetRouter().getSet(options);
    }

    /**
     * Returns sorted set instance by name.
     * This sorted set uses comparator to sort objects.
     *
     * @param name - name of object
     * @return SortedSet object
     */
    @Override
    public <V> RSortedSet<V> getSortedSet(String name) {
        return determineTargetRouter().getSortedSet(name);
    }

    /**
     * Returns sorted set instance by name
     * using provided codec for sorted set objects.
     * This sorted set sorts objects using comparator.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return SortedSet object
     */
    @Override
    public <V> RSortedSet<V> getSortedSet(String name, Codec codec) {
        return determineTargetRouter().getSortedSet(name, codec);
    }

    /**
     * Returns sorted set instance with specified <code>options</code>.
     * This sorted set uses comparator to sort objects.
     *
     * @param options instance options
     * @return SortedSet object
     */
    @Override
    public <V> RSortedSet<V> getSortedSet(PlainOptions options) {
        return determineTargetRouter().getSortedSet(options);
    }

    /**
     * Returns Redis Sorted Set instance by name.
     * This sorted set sorts objects by object score.
     *
     * @param name - name of object
     * @return ScoredSortedSet object
     */
    @Override
    public <V> RScoredSortedSet<V> getScoredSortedSet(String name) {
        return determineTargetRouter().getScoredSortedSet(name);
    }

    /**
     * Returns Redis Sorted Set instance by name
     * using provided codec for sorted set objects.
     * This sorted set sorts objects by object score.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return ScoredSortedSet object
     */
    @Override
    public <V> RScoredSortedSet<V> getScoredSortedSet(String name, Codec codec) {
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
    public <V> RScoredSortedSet<V> getScoredSortedSet(PlainOptions options) {
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
    public RLexSortedSet getLexSortedSet(String name) {
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
    public RLexSortedSet getLexSortedSet(CommonOptions options) {
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
    public RShardedTopic getShardedTopic(String name) {
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
    public RShardedTopic getShardedTopic(String name, Codec codec) {
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
    public RShardedTopic getShardedTopic(PlainOptions options) {
        return determineTargetRouter().getShardedTopic(options);
    }

    /**
     * Returns topic instance by name.
     * <p>
     * Messages are delivered to message listeners connected to the same Topic.
     * <p>
     *
     * @param name - name of object
     * @return Topic object
     */
    @Override
    public RTopic getTopic(String name) {
        return determineTargetRouter().getTopic(name);
    }

    /**
     * Returns topic instance by name
     * using provided codec for messages.
     * <p>
     * Messages are delivered to message listeners connected to the same Topic.
     * <p>
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Topic object
     */
    @Override
    public RTopic getTopic(String name, Codec codec) {
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
    public RTopic getTopic(PlainOptions options) {
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
    public RReliableTopic getReliableTopic(String name) {
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
    public RReliableTopic getReliableTopic(String name, Codec codec) {
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
    public RReliableTopic getReliableTopic(PlainOptions options) {
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
     * @return PatterTopic object
     */
    @Override
    public RPatternTopic getPatternTopic(String pattern) {
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
     * @return PatterTopic object
     */
    @Override
    public RPatternTopic getPatternTopic(String pattern, Codec codec) {
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
    public RPatternTopic getPatternTopic(PatternTopicOptions options) {
        return determineTargetRouter().getPatternTopic(options);
    }

    /**
     * Returns unbounded queue instance by name.
     *
     * @param name of object
     * @return queue object
     */
    @Override
    public <V> RQueue<V> getQueue(String name) {
        return determineTargetRouter().getQueue(name);
    }

    /**
     * Returns transfer queue instance by name.
     *
     * @param name - name of object
     * @return TransferQueue object
     */
    @Override
    public <V> RTransferQueue<V> getTransferQueue(String name) {
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
    public <V> RTransferQueue<V> getTransferQueue(String name, Codec codec) {
        return determineTargetRouter().getTransferQueue(name, codec);
    }

    /**
     * Returns transfer queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return TransferQueue object
     */
    @Override
    public <V> RTransferQueue<V> getTransferQueue(PlainOptions options) {
        return determineTargetRouter().getTransferQueue(options);
    }

    /**
     * Returns unbounded delayed queue instance by name.
     * <p>
     * Could be attached to destination queue only.
     * All elements are inserted with transfer delay to destination queue.
     *
     * @param destinationQueue - destination queue
     * @return Delayed queue object
     */
    @Override
    public <V> RDelayedQueue<V> getDelayedQueue(RQueue<V> destinationQueue) {
        return determineTargetRouter().getDelayedQueue(destinationQueue);
    }

    /**
     * Returns unbounded queue instance by name
     * using provided codec for queue objects.
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    @Override
    public <V> RQueue<V> getQueue(String name, Codec codec) {
        return determineTargetRouter().getQueue(name, codec);
    }

    /**
     * Returns unbounded queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return queue object
     */
    @Override
    public <V> RQueue<V> getQueue(PlainOptions options) {
        return determineTargetRouter().getQueue(options);
    }

    /**
     * Returns RingBuffer based queue.
     *
     * @param name - name of object
     * @return RingBuffer object
     */
    @Override
    public <V> RRingBuffer<V> getRingBuffer(String name) {
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
    public <V> RRingBuffer<V> getRingBuffer(String name, Codec codec) {
        return determineTargetRouter().getRingBuffer(name, codec);
    }

    /**
     * Returns RingBuffer based queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return RingBuffer object
     */
    @Override
    public <V> RRingBuffer<V> getRingBuffer(PlainOptions options) {
        return determineTargetRouter().getRingBuffer(options);
    }

    /**
     * Returns priority unbounded queue instance by name.
     * It uses comparator to sort objects.
     *
     * @param name of object
     * @return Queue object
     */
    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(String name) {
        return determineTargetRouter().getPriorityQueue(name);
    }

    /**
     * Returns priority unbounded queue instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(String name, Codec codec) {
        return determineTargetRouter().getPriorityQueue(name, codec);
    }

    /**
     * @param options
     * @param <V>
     * @return
     */
    @Override
    public <V> RPriorityQueue<V> getPriorityQueue(PlainOptions options) {
        return determineTargetRouter().getPriorityQueue(options);
    }

    /**
     * Returns unbounded priority blocking queue instance by name.
     * It uses comparator to sort objects.
     *
     * @param name of object
     * @return Queue object
     */
    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(String name) {
        return determineTargetRouter().getPriorityBlockingQueue(name);
    }

    /**
     * Returns unbounded priority blocking queue instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(String name, Codec codec) {
        return determineTargetRouter().getPriorityBlockingQueue(name, codec);
    }

    /**
     * Returns unbounded priority blocking queue instance with specified <code>options</code>.
     * It uses comparator to sort objects.
     *
     * @param options instance options
     * @return Queue object
     */
    @Override
    public <V> RPriorityBlockingQueue<V> getPriorityBlockingQueue(PlainOptions options) {
        return determineTargetRouter().getPriorityBlockingQueue(options);
    }

    /**
     * Returns unbounded priority blocking deque instance by name.
     * It uses comparator to sort objects.
     *
     * @param name of object
     * @return Queue object
     */
    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(String name) {
        return determineTargetRouter().getPriorityBlockingDeque(name);
    }

    /**
     * Returns unbounded priority blocking deque instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(String name, Codec codec) {
        return determineTargetRouter().getPriorityBlockingDeque(name, codec);
    }

    /**
     * Returns unbounded priority blocking deque instance with specified <code>options</code>.
     * It uses comparator to sort objects.
     *
     * @param options instance options
     * @return Queue object
     */
    @Override
    public <V> RPriorityBlockingDeque<V> getPriorityBlockingDeque(PlainOptions options) {
        return determineTargetRouter().getPriorityBlockingDeque(options);
    }

    /**
     * Returns priority unbounded deque instance by name.
     * It uses comparator to sort objects.
     *
     * @param name of object
     * @return Queue object
     */
    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(String name) {
        return determineTargetRouter().getPriorityDeque(name);
    }

    /**
     * Returns priority unbounded deque instance by name
     * using provided codec for queue objects.
     * It uses comparator to sort objects.
     *
     * @param name  - name of object
     * @param codec - codec for message
     * @return Queue object
     */
    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(String name, Codec codec) {
        return determineTargetRouter().getPriorityDeque(name, codec);
    }

    /**
     * Returns priority unbounded deque instance with specified <code>options</code>.
     * It uses comparator to sort objects.
     *
     * @param options instance options
     * @return Queue object
     */
    @Override
    public <V> RPriorityDeque<V> getPriorityDeque(PlainOptions options) {
        return determineTargetRouter().getPriorityDeque(options);
    }

    /**
     * Returns unbounded blocking queue instance by name.
     *
     * @param name - name of object
     * @return BlockingQueue object
     */
    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(String name) {
        return determineTargetRouter().getBlockingQueue(name);
    }

    /**
     * Returns unbounded blocking queue instance by name
     * using provided codec for queue objects.
     *
     * @param name  - name of queue
     * @param codec - queue objects codec
     * @return BlockingQueue object
     */
    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(String name, Codec codec) {
        return determineTargetRouter().getBlockingQueue(name, codec);
    }

    /**
     * Returns unbounded blocking queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BlockingQueue object
     */
    @Override
    public <V> RBlockingQueue<V> getBlockingQueue(PlainOptions options) {
        return determineTargetRouter().getBlockingQueue(options);
    }

    /**
     * Returns bounded blocking queue instance by name.
     *
     * @param name of queue
     * @return BoundedBlockingQueue object
     */
    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(String name) {
        return determineTargetRouter().getBoundedBlockingQueue(name);
    }

    /**
     * Returns bounded blocking queue instance by name
     * using provided codec for queue objects.
     *
     * @param name  - name of queue
     * @param codec - codec for values
     * @return BoundedBlockingQueue object
     */
    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(String name, Codec codec) {
        return determineTargetRouter().getBoundedBlockingQueue(name, codec);
    }

    /**
     * Returns bounded blocking queue instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BoundedBlockingQueue object
     */
    @Override
    public <V> RBoundedBlockingQueue<V> getBoundedBlockingQueue(PlainOptions options) {
        return determineTargetRouter().getBoundedBlockingQueue(options);
    }

    /**
     * Returns unbounded deque instance by name.
     *
     * @param name - name of object
     * @return Deque object
     */
    @Override
    public <V> RDeque<V> getDeque(String name) {
        return determineTargetRouter().getDeque(name);
    }

    /**
     * Returns unbounded deque instance by name
     * using provided codec for deque objects.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return Deque object
     */
    @Override
    public <V> RDeque<V> getDeque(String name, Codec codec) {
        return determineTargetRouter().getDeque(name, codec);
    }

    /**
     * Returns unbounded deque instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return Deque object
     */
    @Override
    public <V> RDeque<V> getDeque(PlainOptions options) {
        return determineTargetRouter().getDeque(options);
    }

    /**
     * Returns unbounded blocking deque instance by name.
     *
     * @param name - name of object
     * @return BlockingDeque object
     */
    @Override
    public <V> RBlockingDeque<V> getBlockingDeque(String name) {
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
    public <V> RBlockingDeque<V> getBlockingDeque(String name, Codec codec) {
        return determineTargetRouter().getBlockingDeque(name, codec);
    }

    /**
     * Returns unbounded blocking deque instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BlockingDeque object
     */
    @Override
    public <V> RBlockingDeque<V> getBlockingDeque(PlainOptions options) {
        return determineTargetRouter().getBlockingDeque(options);
    }

    /**
     * Returns atomicLong instance by name.
     *
     * @param name - name of object
     * @return AtomicLong object
     */
    @Override
    public RAtomicLong getAtomicLong(String name) {
        return determineTargetRouter().getAtomicLong(name);
    }

    /**
     * Returns atomicLong instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return AtomicLong object
     */
    @Override
    public RAtomicLong getAtomicLong(CommonOptions options) {
        return determineTargetRouter().getAtomicLong(options);
    }

    /**
     * Returns atomicDouble instance by name.
     *
     * @param name - name of object
     * @return AtomicDouble object
     */
    @Override
    public RAtomicDouble getAtomicDouble(String name) {
        return determineTargetRouter().getAtomicDouble(name);
    }

    /**
     * Returns atomicDouble instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return AtomicDouble object
     */
    @Override
    public RAtomicDouble getAtomicDouble(CommonOptions options) {
        return determineTargetRouter().getAtomicDouble(options);
    }

    /**
     * Returns LongAdder instances by name.
     *
     * @param name - name of object
     * @return LongAdder object
     */
    @Override
    public RLongAdder getLongAdder(String name) {
        return determineTargetRouter().getLongAdder(name);
    }

    /**
     * Returns LongAdder instances with specified <code>options</code>.
     *
     * @param options instance options
     * @return LongAdder object
     */
    @Override
    public RLongAdder getLongAdder(CommonOptions options) {
        return determineTargetRouter().getLongAdder(options);
    }

    /**
     * Returns DoubleAdder instances by name.
     *
     * @param name - name of object
     * @return LongAdder object
     */
    @Override
    public RDoubleAdder getDoubleAdder(String name) {
        return determineTargetRouter().getDoubleAdder(name);
    }

    /**
     * Returns DoubleAdder instances with specified <code>options</code>.
     *
     * @param options instance options
     * @return LongAdder object
     */
    @Override
    public RDoubleAdder getDoubleAdder(CommonOptions options) {
        return determineTargetRouter().getDoubleAdder(options);
    }

    /**
     * Returns countDownLatch instance by name.
     *
     * @param name - name of object
     * @return CountDownLatch object
     */
    @Override
    public RCountDownLatch getCountDownLatch(String name) {
        return determineTargetRouter().getCountDownLatch(name);
    }

    /**
     * Returns countDownLatch instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return CountDownLatch object
     */
    @Override
    public RCountDownLatch getCountDownLatch(CommonOptions options) {
        return determineTargetRouter().getCountDownLatch(options);
    }

    /**
     * Returns bitSet instance by name.
     *
     * @param name - name of object
     * @return BitSet object
     */
    @Override
    public RBitSet getBitSet(String name) {
        return determineTargetRouter().getBitSet(name);
    }

    /**
     * Returns bitSet instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BitSet object
     */
    @Override
    public RBitSet getBitSet(CommonOptions options) {
        return determineTargetRouter().getBitSet(options);
    }

    /**
     * Returns bloom filter instance by name.
     *
     * @param name - name of object
     * @return BloomFilter object
     */
    @Override
    public <V> RBloomFilter<V> getBloomFilter(String name) {
        return determineTargetRouter().getBloomFilter(name);
    }

    /**
     * Returns bloom filter instance by name
     * using provided codec for objects.
     *
     * @param name  - name of object
     * @param codec - codec for values
     * @return BloomFilter object
     */
    @Override
    public <V> RBloomFilter<V> getBloomFilter(String name, Codec codec) {
        return determineTargetRouter().getBloomFilter(name, codec);
    }

    /**
     * Returns bloom filter instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return BloomFilter object
     */
    @Override
    public <V> RBloomFilter<V> getBloomFilter(PlainOptions options) {
        return determineTargetRouter().getBloomFilter(options);
    }

    /**
     * Returns id generator by name.
     *
     * @param name - name of object
     * @return IdGenerator object
     */
    @Override
    public RIdGenerator getIdGenerator(String name) {
        return determineTargetRouter().getIdGenerator(name);
    }

    /**
     * Returns id generator instance with specified <code>options</code>.
     *
     * @param options instance options
     * @return IdGenerator object
     */
    @Override
    public RIdGenerator getIdGenerator(CommonOptions options) {
        return determineTargetRouter().getIdGenerator(options);
    }

    /**
     * Returns interface for Redis Function feature
     *
     * @return function object
     */
    @Override
    public RFunction getFunction() {
        return determineTargetRouter().getFunction();
    }

    /**
     * Returns interface for Redis Function feature using provided codec
     *
     * @param codec - codec for params and result
     * @return function interface
     */
    @Override
    public RFunction getFunction(Codec codec) {
        return determineTargetRouter().getFunction(codec);
    }

    /**
     * Returns interface for Redis Function feature with specified <code>options</code>.
     *
     * @param options instance options
     * @return function object
     */
    @Override
    public RFunction getFunction(OptionalOptions options) {
        return determineTargetRouter().getFunction(options);
    }

    /**
     * Returns script operations object
     *
     * @return Script object
     */
    @Override
    public RScript getScript() {
        return determineTargetRouter().getScript();
    }

    /**
     * Returns script operations object using provided codec.
     *
     * @param codec - codec for params and result
     * @return Script object
     */
    @Override
    public RScript getScript(Codec codec) {
        return determineTargetRouter().getScript(codec);
    }

    /**
     * Returns script operations object with specified <code>options</code>.
     *
     * @param options instance options
     * @return Script object
     */
    @Override
    public RScript getScript(OptionalOptions options) {
        return determineTargetRouter().getScript(options);
    }

    /**
     * Returns ScheduledExecutorService by name
     *
     * @param name - name of object
     * @return ScheduledExecutorService object
     */
    @Override
    public RScheduledExecutorService getExecutorService(String name) {
        return determineTargetRouter().getExecutorService(name);
    }

    /**
     * Returns ScheduledExecutorService by name
     *
     * @param name    - name of object
     * @param options - options for executor
     * @return ScheduledExecutorService object
     */
    @Override
    @Deprecated
    public RScheduledExecutorService getExecutorService(String name, ExecutorOptions options) {
        return determineTargetRouter().getExecutorService(name, options);
    }

    /**
     * Returns ScheduledExecutorService by name
     * using provided codec for task, response and request serialization
     *
     * @param name  - name of object
     * @param codec - codec for task, response and request
     * @return ScheduledExecutorService object
     * @since 2.8.2
     */
    @Override
    public RScheduledExecutorService getExecutorService(String name, Codec codec) {
        return determineTargetRouter().getExecutorService(name, codec);
    }

    /**
     * Returns ScheduledExecutorService by name
     * using provided codec for task, response and request serialization
     *
     * @param name    - name of object
     * @param codec   - codec for task, response and request
     * @param options - options for executor
     * @return ScheduledExecutorService object
     */
    @Override
    @Deprecated
    public RScheduledExecutorService getExecutorService(String name, Codec codec, ExecutorOptions options) {
        return determineTargetRouter().getExecutorService(name, codec, options);
    }

    /**
     * Returns ScheduledExecutorService with defined options
     * <p>
     * Usage examples:
     * <pre>
     * RScheduledExecutorService service = redisson.getExecutorService(
     *                                                  ExecutorOptions.name("test")
     *                                                  .taskRetryInterval(Duration.ofSeconds(60)));
     * </pre>
     *
     * @param options options instance
     * @return ScheduledExecutorService object
     */
    @Override
    public RScheduledExecutorService getExecutorService(org.redisson.api.options.ExecutorOptions options) {
        return determineTargetRouter().getExecutorService(options);
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
     * Creates transaction with <b>READ_COMMITTED</b> isolation level.
     *
     * @param options - transaction configuration
     * @return Transaction object
     */
    @Override
    public RTransaction createTransaction(TransactionOptions options) {
        return determineTargetRouter().createTransaction(options);
    }

    /**
     * Creates batch object which could be executed later
     * with collected group of commands in pipeline mode.
     * <p>
     * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
     *
     * @param options - batch configuration
     * @return Batch object
     */
    @Override
    public RBatch createBatch(BatchOptions options) {
        return determineTargetRouter().createBatch(options);
    }

    /**
     * Creates batch object which could be executed later
     * with collected group of commands in pipeline mode.
     * <p>
     * See <a href="http://redis.io/topics/pipelining">http://redis.io/topics/pipelining</a>
     *
     * @return Batch object
     */
    @Override
    public RBatch createBatch() {
        return determineTargetRouter().createBatch();
    }

    /**
     * Returns interface with methods for Redis keys.
     * Each of Redis/Redisson object associated with own key
     *
     * @return Keys object
     */
    @Override
    public RKeys getKeys() {
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
    public RKeys getKeys(KeysOptions options) {
        return determineTargetRouter().getKeys(options);
    }

    /**
     * Returns RedissonAttachedLiveObjectService which can be used to
     * retrieve live REntity(s)
     *
     * @return LiveObjectService object
     */
    @Override
    public RLiveObjectService getLiveObjectService() {
        return determineTargetRouter().getLiveObjectService();
    }

    /**
     * Returns Live Object Service which is used to store Java objects
     * with specified <code>options</code>.
     *
     * @param options
     * @return LiveObjectService object
     */
    @Override
    public RLiveObjectService getLiveObjectService(LiveObjectOptions options) {
        return determineTargetRouter().getLiveObjectService(options);
    }

    @Override
    public RClientSideCaching getClientSideCaching(ClientSideCachingOptions options) {
        return determineTargetRouter().getClientSideCaching(options);
    }

    /**
     * Returns RxJava Redisson instance
     *
     * @return redisson instance
     */
    @Override
    public RedissonRxClient rxJava() {
        return determineTargetRouter().rxJava();
    }

    /**
     * Returns Reactive Redisson instance
     *
     * @return redisson instance
     */
    @Override
    public RedissonReactiveClient reactive() {
        return determineTargetRouter().reactive();
    }

    /**
     * Shutdown Redisson instance but <b>NOT</b> Redis server
     * <p>
     * This equates to invoke shutdown(0, 2, TimeUnit.SECONDS);
     */
    @Override
    public void shutdown() {
        determineTargetRouter().shutdown();
    }

    /**
     * Shuts down Redisson instance but <b>NOT</b> Redis server
     * <p>
     * Shutdown ensures that no tasks are submitted for <i>'the quiet period'</i>
     * (usually a couple seconds) before it shuts itself down.  If a task is submitted during the quiet period,
     * it is guaranteed to be accepted and the quiet period will start over.
     *
     * @param quietPeriod the quiet period as described in the documentation
     * @param timeout     the maximum amount of time to wait until the executor is {@linkplain #shutdown()}
     *                    regardless if a task was submitted during the quiet period
     * @param unit        the unit of {@code quietPeriod} and {@code timeout}
     */
    @Override
    public void shutdown(long quietPeriod, long timeout, TimeUnit unit) {
        determineTargetRouter().shutdown(quietPeriod, timeout, unit);

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
     * Returns API to manage Redis nodes
     *
     * @param nodes Redis nodes API class
     * @return Redis nodes API object
     * @see RedisNodes#CLUSTER
     * @see RedisNodes#MASTER_SLAVE
     * @see RedisNodes#SENTINEL_MASTER_SLAVE
     * @see RedisNodes#SINGLE
     */
    @Override
    public <T extends BaseRedisNodes> T getRedisNodes(RedisNodes<T> nodes) {
        return determineTargetRouter().getRedisNodes(nodes);
    }

    /**
     * @return
     */
    @Override
    @Deprecated
    public NodesGroup<Node> getNodesGroup() {
        return determineTargetRouter().getNodesGroup();
    }

    /**
     * @return
     */
    @Override
    @Deprecated
    public ClusterNodesGroup getClusterNodesGroup() {
        return determineTargetRouter().getClusterNodesGroup();
    }

    /**
     * Returns {@code true} if this Redisson instance has been shut down.
     *
     * @return {@code true} if this Redisson instance has been shut down overwise <code>false</code>
     */
    @Override
    public boolean isShutdown() {
        return determineTargetRouter().isShutdown();
    }

    /**
     * Returns {@code true} if this Redisson instance was started to be shutdown
     * or was shutdown {@link #isShutdown()} already.
     *
     * @return {@code true} if this Redisson instance was started to be shutdown
     * or was shutdown {@link #isShutdown()} already.
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
