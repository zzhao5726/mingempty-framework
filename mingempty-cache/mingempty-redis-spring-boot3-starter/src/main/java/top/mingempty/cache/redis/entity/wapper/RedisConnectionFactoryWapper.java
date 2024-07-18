package top.mingempty.cache.redis.entity.wapper;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConnection;
import top.mingempty.cache.redis.aspect.RedisCacheAspect;
import top.mingempty.domain.other.AbstractRouter;

import java.util.Map;

/**
 * RedisConnectionFactory封装
 *
 * @author zzhao
 * @date 2023/3/12 11:02
 */
public class RedisConnectionFactoryWapper extends AbstractRouter<RedisConnectionFactory> implements RedisConnectionFactory {
    public RedisConnectionFactoryWapper(String defaultTargetName, Map<String, RedisConnectionFactory> targetRouter) {
        super(defaultTargetName, targetRouter);
    }

    public RedisConnectionFactoryWapper(String defaultTargetName, Map<String, RedisConnectionFactory> targetRouter, boolean lenientFallback) {
        super(defaultTargetName, targetRouter, lenientFallback);
    }

    /**
     * 检索查找路由方式
     */
    @Override
    protected String determineCurrentLookupKey() {
        return RedisCacheAspect.acquireCacheName();
    }

    /************************RedisConnectionFactory方法封装----------start*********************************************/
    /**
     * Provides a suitable connection for interacting with Redis.
     *
     * @return connection for interacting with Redis.
     * @throws IllegalStateException if the connection factory requires initialization and the factory was not yet
     *                               initialized.
     */
    @Override
    public RedisConnection getConnection() {
        return determineTargetRouter().getConnection();
    }

    /**
     * Provides a suitable connection for interacting with Redis Cluster.
     *
     * @return
     * @throws IllegalStateException if the connection factory requires initialization and the factory was not yet
     *                               initialized.
     * @since 1.7
     */
    @Override
    public RedisClusterConnection getClusterConnection() {
        return determineTargetRouter().getClusterConnection();
    }

    /**
     * Specifies if pipelined results should be converted to the expected data type. If false, results of
     * {@link RedisConnection#closePipeline()} and {RedisConnection#exec()} will be of the type returned by the underlying
     * driver This method is mostly for backwards compatibility with 1.0. It is generally always a good idea to allow
     * results to be converted and deserialized. In fact, this is now the default behavior.
     *
     * @return Whether or not to convert pipeline and tx results
     */
    @Override
    public boolean getConvertPipelineAndTxResults() {
        return determineTargetRouter().getConvertPipelineAndTxResults();
    }

    /**
     * Provides a suitable connection for interacting with Redis Sentinel.
     *
     * @return connection for interacting with Redis Sentinel.
     * @throws IllegalStateException if the connection factory requires initialization and the factory was not yet
     *                               initialized.
     * @since 1.4
     */
    @Override
    public RedisSentinelConnection getSentinelConnection() {
        return determineTargetRouter().getSentinelConnection();
    }

    /**
     * @param ex
     * @return
     */
    @Override
    public DataAccessException translateExceptionIfPossible(RuntimeException ex) {
        return determineTargetRouter().translateExceptionIfPossible(ex);
    }
    /************************RedisConnectionFactory方法封装----------end*********************************************/



}
