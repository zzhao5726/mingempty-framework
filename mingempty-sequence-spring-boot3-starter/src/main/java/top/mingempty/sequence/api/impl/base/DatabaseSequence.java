package top.mingempty.sequence.api.impl.base;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Pair;
import lombok.extern.slf4j.Slf4j;
import top.mingempty.commons.util.IoUtils;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.AbstractCacheSequence;
import top.mingempty.sequence.enums.SeqRealizeEnum;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLSyntaxErrorException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于数据库实现带缓存的序号生成
 *
 * @author zzhao
 */
@Slf4j
public class DatabaseSequence extends AbstractCacheSequence {

    private final DataSource dataSource;

    private DatabaseSequence(String seqName, DataSource dataSource) {
        this(seqName, 50, dataSource);
    }

    private DatabaseSequence(String seqName, int step, DataSource dataSource) {
        super(SeqRealizeEnum.Database, seqName, step);
        this.dataSource = dataSource;
    }

    /**
     * 获取新的最大值和步长
     * <p>
     * key：获取到的最大值
     * value：步长
     */
    @Override
    protected final Pair<Long, Integer> max() {
        try (Connection connection = dataSource.getConnection()) {
            try {
                //开启事务
                connection.setAutoCommit(false);
                //查询当前的值
                try (PreparedStatement preparedStatement
                             = connection.prepareStatement("select max_id+step as max_id,step from t_sequence_generated where biz_tag=?;")) {
                    preparedStatement.setString(1, key());
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        if (resultSet.next()) {
                            //存在则更新
                            try (PreparedStatement preparedStatementByUpdate = connection.prepareStatement("update t_sequence_generated set max_id=max_id+step where biz_tag=?;")) {
                                preparedStatementByUpdate.setString(1, key());
                                preparedStatementByUpdate.executeUpdate();
                            }
                            long maxId = resultSet.getLong("max_id");
                            int step = resultSet.getInt("step");
                            return Pair.of(maxId, step);

                        } else {
                            //不存在则插入
                            try (PreparedStatement preparedStatementByInsert = connection.prepareStatement("insert into t_sequence_generated(biz_tag, max_id, step) value (?, ?, ?);")) {
                                preparedStatementByInsert.setString(1, key());
                                preparedStatementByInsert.setLong(2, step());
                                preparedStatementByInsert.setInt(3, step());
                                preparedStatementByInsert.execute();
                            }
                            return Pair.of((long) step(), step());
                        }
                    }
                } finally {
                    //提交事务
                    connection.commit();
                }
            } catch (Exception e) {
                //回滚事务
                connection.rollback();
                throw e;
            }
        } catch (SQLSyntaxErrorException e) {
            try {
                String sql = IoUtils.gainString(Objects.requireNonNull(this.getClass().getResourceAsStream("/db.sql")));
                log.error("序号[{}]获取最大值异常，此异常可能原因为未创建表结构。表结构如下所示:\n{}", key(), sql, e);
            } catch (IOException ex) {
                log.error("序号[{}]获取最大值异常", key(), e);
            }
        } catch (Exception e) {
            log.error("序号[{}]获取最大值异常", key(), e);
        }
        //说明出险了异常
        return Pair.of(-1L, -1);
    }

    /**
     * 序号实现机制
     */
    @Override
    public SeqRealizeEnum seqRealize() {
        return SeqRealizeEnum.Database;
    }

    public static DatabaseSequence gainInstance(String seqName) {
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName);
    }


    public static DatabaseSequence gainInstance(String instanceName, String seqName) {
        return DatabaseSequenceHolder.DATABASE_SEQUENCE_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0)).get(seqName);
    }

    public static class DatabaseSequenceHolder {

        private static final Map<String, Map<String, DatabaseSequence>> DATABASE_SEQUENCE_MAP = new ConcurrentHashMap<>();

        public static void init(String seqName, DataSource dataSource) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, dataSource);
        }

        public static void init(String seqName, int step, DataSource dataSource) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, dataSource);
        }

        public static void init(String instanceName, String seqName, DataSource dataSource) {
            init(instanceName, seqName, 50, dataSource);
        }

        public static void init(String instanceName, String seqName, int step, DataSource dataSource) {
            if (DATABASE_SEQUENCE_MAP.containsKey(instanceName)
                    && DATABASE_SEQUENCE_MAP.get(instanceName).containsKey(seqName)) {
                log.error("DatabaseSequence has init!!!!!!!");
                return;
            }
            Assert.notNull(dataSource, "DataSource is null");
            DATABASE_SEQUENCE_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0))
                    .computeIfAbsent(seqName, k -> new DatabaseSequence(seqName, step, dataSource));
        }
    }

}
