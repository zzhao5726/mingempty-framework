package top.mingempty.datasource.creator.c3p0;

import lombok.Data;

/**
 * C3po配置
 *
 * @author zzhao
 */
@Data
public class C3p0Config {

    private final static Integer INITIAL_POOL_SIZE = 3;
    private final static Integer MAX_POOL_SIZE = 15;
    private final static Integer MIN_POOL_SIZE = 3;
    private final static Integer ACQUIRE_INCREMENT = 3;
    private final static boolean TEST_CONNECTION_ON_CHECKOUT = false;
    private final static boolean TEST_CONNECTION_ON_CHECKIN = false;
    private final static int IDLE_CONNECTION_TEST_PERIOD = 0;
    private final static int MAX_IDLE_TIME = 0;
    private final static int MAX_STATEMENTS = 0;
    private final static int MAX_STATEMENTS_PER_CONNECTION = 0;
    private final static int CHECKOUT_TIMEOUT = 0;
    private final static int MAX_ADMINISTRATIVE_TASK_TIME = 0;
    private final static int NUM_HELPER_THREADS = 3;
    private final static boolean FORCE_IGNORE_UNRESOLVED_TXNS = false;
    private final static int STATEMENT_CACHE_NUM_DEFERRED_CLOSE_THREADS = 0;
    private final static boolean DEBUG_UNRETURNED_CONNECTION_STACK_TRACES = false;
    private final static int UNRETURNED_CONNECTION_TIMEOUT = 0;
    private final static int MAX_CONNECTION_AGE = 0;
    private final static boolean BREAK_AFTER_ACQUIRE_FAILURE = false;
    private final static int ACQUIRE_RETRY_DELAY = 1000;
    private final static int ACQUIRE_RETRY_ATTEMPTS = 30;


    /*=================连接池配置项  start=================*/


    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialPoolSize = INITIAL_POOL_SIZE;
    /**
     * 最大连接池数量
     */
    private Integer maxPoolSize = MAX_POOL_SIZE;

    /**
     * 最小连接池数量
     */
    private Integer minPoolSize = MIN_POOL_SIZE;

    /**
     * 当连接池中的连接耗尽时一次性创建的连接数
     */
    private Integer acquireIncrement = ACQUIRE_INCREMENT;

    /*=================连接池配置项  end=================*/

    /*=================连接测试配置项  start=================*/

    /**
     * 每次从池中取出连接时是否测试该连接
     */
    private Boolean testConnectionOnCheckout = TEST_CONNECTION_ON_CHECKOUT;

    /**
     * 每次将连接放回池中时是否测试该连接
     */
    private Boolean testConnectionOnCheckin = TEST_CONNECTION_ON_CHECKIN;

    /**
     * 用于测试连接是否有效的 SQL 查询语句
     */
    private String preferredTestQuery;

    /**
     * 每多少秒进行一次空闲连接的测试
     */
    private Integer idleConnectionTestPeriod = IDLE_CONNECTION_TEST_PERIOD;

    /**
     * 连接在池中保持空闲状态的最长时间
     */
    private Integer maxIdleTime = MAX_IDLE_TIME;
    /*=================连接测试配置项  end=================*/


    /*=================性能优化配置项  start=================*/

    /**
     * 连接池中缓存的最大可用语句数
     */
    private Integer maxStatements = MAX_STATEMENTS;

    /**
     * 每个连接缓存的最大语句数
     */
    private Integer maxStatementsPerConnection = MAX_STATEMENTS_PER_CONNECTION;

    /**
     * 获取连接时的超时时间(milliseconds)
     */
    private Integer checkoutTimeout = CHECKOUT_TIMEOUT;

    /**
     * 管理任务的最大时间(seconds)
     */
    private Integer maxAdministrativeTaskTime = MAX_ADMINISTRATIVE_TASK_TIME;

    /*=================性能优化配置项  end=================*/


    /*=================其他配置项  start=================*/

    /**
     * 辅助线程的数量，用于异步操作
     */
    private Integer numHelperThreads = NUM_HELPER_THREADS;

    /**
     * 自动测试表的名称，如果没有指定，则 c3p0 将创建一个临时表
     */
    private Integer automaticTestTable;

    /*=================其他配置项  end=================*/


    /*=================Acquire 配置项  start=================*/

    /**
     * 在失败之前从数据库获取新连接的尝试次数
     */
    private Integer acquireRetryAttempts = ACQUIRE_RETRY_ATTEMPTS;

    /**
     * 两次获取尝试之间的时间（以毫秒为单位）
     */
    private Integer acquireRetryDelay = ACQUIRE_RETRY_DELAY;

    /**
     * 如果获取连接失败是否中断
     */
    private Boolean breakAfterAcquireFailure = BREAK_AFTER_ACQUIRE_FAILURE;

    /*=================Acquire 配置项  end=================*/


    /*=================Timeout 配置项  start=================*/

    /**
     * 连接池中连接的最大存活时间（以秒为单位），超过这个时间后，连接将被丢弃
     */
    private Integer maxConnectionAge = MAX_CONNECTION_AGE;

    /**
     * 连接在未返回池中的最大时间（以秒为单位），超过这个时间的连接将被认为是泄漏连接并被强制收回
     */
    private Integer unreturnedConnectionTimeout = UNRETURNED_CONNECTION_TIMEOUT;

    /*=================Timeout 配置项  end=================*/


    /*=================Debug 和 Logging 配置项  start=================*/

    /**
     * 是否在连接泄漏时打印堆栈跟踪信息
     */
    private Boolean debugUnreturnedConnectionStackTraces = DEBUG_UNRETURNED_CONNECTION_STACK_TRACES;

    /*=================Debug 和 Logging 配置项  end=================*/


    /*=================Statement 缓存配置项  start=================*/

    /**
     * 用于 statement 缓存的延迟关闭线程数
     */
    private Integer statementCacheNumDeferredCloseThreads = STATEMENT_CACHE_NUM_DEFERRED_CLOSE_THREADS;

    /*=================Statement 缓存配置项  end=================*/


    /*=================Connection Customization 缓存配置项  start=================*/

    /**
     * 自定义连接的类名，可以用于设置连接的特定属性
     */
    private String connectionCustomizerClassName;

    /**
     * 重写默认的数据库用户名
     */
    private String overrideDefaultUser;

    /**
     * 重写默认的数据库密码
     */
    private String overrideDefaultPassword;

    /*=================Connection Customization 缓存配置项  end=================*/


    /*=================Advanced  配置项  start=================*/

    /**
     * 指定自定义 DataSource 工厂类的位置
     */
    private String factoryClassLocation;


    /**
     * 强制忽略未解决的事务
     */
    private Boolean forceIgnoreUnresolvedTransactions = FORCE_IGNORE_UNRESOLVED_TXNS;

    /*=================Advanced  配置项  end=================*/

}
