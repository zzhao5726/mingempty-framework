package top.mingempty.mybatis.interceptor;

import cn.hutool.core.util.ObjUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import top.mingempty.mybatis.tool.ReplacedTableNameTool;


/**
 * mybatis表名称自动自动拦截替换配置
 *
 * @author zzhao
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MybatisReplacedTableNameInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (ObjUtil.isEmpty(ReplacedTableNameTool.gainReplacedTableName())) {
            //当没有数据时，直接放行
            return invocation.proceed();
        }

        Object[] args = invocation.getArgs();
        //获取MappedStatement对象
        MappedStatement ms = (MappedStatement) args[0];
        //获取传入sql语句的参数对象
        Object parameterObject = args[1];

        BoundSql boundSql = ms.getBoundSql(parameterObject);
        //获取到拥有占位符的sql语句
        String sql = boundSql.getSql();
        if (log.isDebugEnabled()) {
            log.debug("表名替换-拦截前sql[{}]", sql);
        }
        //判断是否需要替换表名
        if (!isReplaceTableName(sql)) {
            return invocation.proceed();
        }
        String[] sqls = {sql};

        ReplacedTableNameTool.gainReplacedTableName()
                .forEach((tableName, replacedTableName) -> sqls[0] = sqls[0].replaceAll(tableName, replacedTableName));

        if (log.isDebugEnabled()) {
            log.debug("表名替换-拦截后sql[{}]", sqls[0]);
        }
        //重新生成一个BoundSql对象
        BoundSql bs = new BoundSql(ms.getConfiguration(), sqls[0], boundSql.getParameterMappings(), parameterObject);
        //重新生成一个MappedStatement对象
        MappedStatement newMs = copyMappedStatement(ms, new BoundSqlSqlSource(bs));
        //赋回给实际执行方法所需的参数中
        args[0] = newMs;
        return invocation.proceed();
    }

    /***
     * 判断是否需要替换表名
     * @param sql
     * @return
     */
    private boolean isReplaceTableName(String sql) {
        return ReplacedTableNameTool.gainReplacedTableName().entrySet()
                .parallelStream()
                .anyMatch(entry -> sql.contains(entry.getKey()));
    }

    /***
     * 复制一个新的MappedStatement
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement copyMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());

        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(String.join(",", ms.getKeyProperties()));
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /***
     * MappedStatement构造器接受的是SqlSource
     * 实现SqlSource接口，将BoundSql封装进去
     */
    public static class BoundSqlSqlSource implements SqlSource {
        private final BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
