package top.mingempty.sequence.api.impl.wrapper;

import lombok.extern.slf4j.Slf4j;
import top.mingempty.domain.other.GlobalConstant;
import top.mingempty.sequence.api.impl.AbstractExpirationStrategySequence;
import top.mingempty.sequence.api.impl.base.DatabaseSequence;
import top.mingempty.sequence.enums.ExpirationStrategyEnum;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Datasource序号生成器包装
 *
 * @author zzhao
 */
@Slf4j
public class DatabaseSequenceWrapper extends AbstractExpirationStrategySequence<DatabaseSequence> {

    private DatabaseSequenceWrapper(DatabaseSequence databaseSequence, ExpirationStrategyEnum expirationStrategy) {
        super(databaseSequence, expirationStrategy);
    }


    public static DatabaseSequenceWrapper gainInstance(String seqName) {
        return gainInstance(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName);
    }


    public static DatabaseSequenceWrapper gainInstance(String instanceName, String seqName) {
        return DatabaseSequenceWrapper.DatabaseSequenceHolder.DATABASE_SEQUENCE_WRAPPER_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0)).get(seqName);
    }

    public static class DatabaseSequenceHolder {

        private static final Map<String, Map<String, DatabaseSequenceWrapper>> DATABASE_SEQUENCE_WRAPPER_MAP = new ConcurrentHashMap<>();

        public static void init(String seqName, DataSource dataSource) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, dataSource);
        }

        public static void init(String seqName, int step, DataSource dataSource) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, dataSource);
        }

        public static void init(String seqName, int step, ExpirationStrategyEnum expirationStrategy, DataSource dataSource) {
            init(GlobalConstant.DEFAULT_INSTANCE_NAME, seqName, step, expirationStrategy, dataSource);
        }

        public static void init(String instanceName, String seqName, DataSource dataSource) {
            init(instanceName, seqName, 50, dataSource);
        }

        public static void init(String instanceName, String seqName, int step, DataSource dataSource) {
            init(instanceName, seqName, step, ExpirationStrategyEnum.NONE, dataSource);
        }

        public static void init(String instanceName, String seqName, int step,
                                ExpirationStrategyEnum expirationStrategy, DataSource dataSource) {
            if (DATABASE_SEQUENCE_WRAPPER_MAP.containsKey(instanceName)) {
                log.error("DatabaseSequenceWrapper has init!!!!!!!");
                return;
            }
            DatabaseSequence.DatabaseSequenceHolder.init(instanceName, seqName, step, dataSource);
            DatabaseSequence databaseSequence = DatabaseSequence.gainInstance(instanceName, seqName);
            DATABASE_SEQUENCE_WRAPPER_MAP.computeIfAbsent(instanceName, k -> new ConcurrentHashMap<>(0))
                    .computeIfAbsent(seqName, k -> new DatabaseSequenceWrapper(databaseSequence, expirationStrategy));
        }
    }
}
