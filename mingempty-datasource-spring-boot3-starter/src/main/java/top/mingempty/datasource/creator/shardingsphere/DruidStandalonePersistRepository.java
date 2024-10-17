package top.mingempty.datasource.creator.shardingsphere;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.base.Strings;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.mode.repository.standalone.StandalonePersistRepository;
import org.apache.shardingsphere.mode.repository.standalone.jdbc.JDBCRepository;
import org.apache.shardingsphere.mode.repository.standalone.jdbc.props.JDBCRepositoryProperties;
import org.apache.shardingsphere.mode.repository.standalone.jdbc.props.JDBCRepositoryPropertyKey;
import org.apache.shardingsphere.mode.repository.standalone.jdbc.sql.JDBCRepositorySQL;
import org.apache.shardingsphere.mode.repository.standalone.jdbc.sql.JDBCRepositorySQLLoader;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/**
 * 使用Druid作为shardingsphere内置数据源
 * 参考{@link JDBCRepository}实现
 * 使用时请使用spi机制自行注入该bean
 */
@Slf4j
public class DruidStandalonePersistRepository implements StandalonePersistRepository {


    private static final String SEPARATOR = "/";

    private JDBCRepositorySQL repositorySQL;

    private DruidDataSource dataSource;

    @Override
    @SneakyThrows(SQLException.class)
    public void init(Properties props) {
        JDBCRepositoryProperties jdbcRepositoryProps = new JDBCRepositoryProperties(props);
        repositorySQL = JDBCRepositorySQLLoader.load(jdbcRepositoryProps.getValue(JDBCRepositoryPropertyKey.PROVIDER));
        dataSource = new DruidDataSource();
        dataSource.setDriverClassName(repositorySQL.getDriverClassName());
        dataSource.setUrl(jdbcRepositoryProps.getValue(JDBCRepositoryPropertyKey.JDBC_URL));
        dataSource.setUsername(jdbcRepositoryProps.getValue(JDBCRepositoryPropertyKey.USERNAME));
        dataSource.setPassword(jdbcRepositoryProps.getValue(JDBCRepositoryPropertyKey.PASSWORD));
        dataSource.setTestWhileIdle(false);
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            if (jdbcRepositoryProps.<String>getValue(JDBCRepositoryPropertyKey.JDBC_URL).contains("h2:mem:")) {
                try {
                    statement.execute("TRUNCATE TABLE `repository`");
                } catch (final SQLException ignored) {
                }
            }
            statement.execute(repositorySQL.getCreateTableSQL());
        }
    }

    @Override
    public String getDirectly(String key) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(repositorySQL.getSelectByKeySQL())) {
            preparedStatement.setString(1, key);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("value");
                }
            }
        } catch (final SQLException ex) {
            log.error("Get {} data by key: {} failed", getType(), key, ex);
        }
        return "";
    }

    @Override
    public List<String> getChildrenKeys(final String key) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(repositorySQL.getSelectByParentKeySQL())) {
            preparedStatement.setString(1, key);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                List<String> resultChildren = new LinkedList<>();
                while (resultSet.next()) {
                    String childrenKey = resultSet.getString("key");
                    if (Strings.isNullOrEmpty(childrenKey)) {
                        continue;
                    }
                    int lastIndexOf = childrenKey.lastIndexOf(SEPARATOR);
                    resultChildren.add(childrenKey.substring(lastIndexOf + 1));
                }
                resultChildren.sort(Comparator.reverseOrder());
                return new ArrayList<>(resultChildren);
            }
        } catch (final SQLException ex) {
            log.error("Get children {} data by key: {} failed", getType(), key, ex);
            return Collections.emptyList();
        }
    }

    @Override
    public boolean isExisted(final String key) {
        return !Strings.isNullOrEmpty(getDirectly(key));
    }

    @Override
    public void persist(final String key, final String value) {
        try {
            if (isExisted(key)) {
                update(key, value);
                return;
            }
            String tempPrefix = "";
            String parent = SEPARATOR;
            String[] paths = Arrays.stream(key.split(SEPARATOR)).filter(each -> !Strings.isNullOrEmpty(each)).toArray(String[]::new);
            // Create key level directory recursively.
            for (int i = 0; i < paths.length - 1; i++) {
                String tempKey = tempPrefix + SEPARATOR + paths[i];
                String tempKeyVal = getDirectly(tempKey);
                if (Strings.isNullOrEmpty(tempKeyVal)) {
                    insert(tempKey, "", parent);
                }
                tempPrefix = tempKey;
                parent = tempKey;
            }
            insert(key, value, parent);
        } catch (final SQLException ex) {
            log.error("Persist {} data to key: {} failed", getType(), key, ex);
        }
    }

    private void insert(final String key, final String value, final String parent) throws SQLException {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(repositorySQL.getInsertSQL())) {
            preparedStatement.setString(1, UUID.randomUUID().toString());
            preparedStatement.setString(2, key);
            preparedStatement.setString(3, value);
            preparedStatement.setString(4, parent);
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(final String key, final String value) {
        try (
                Connection connection = dataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(repositorySQL.getUpdateSQL())) {
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, key);
            preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            log.error("Update {} data to key: {} failed", getType(), key, ex);
        }
    }

    @Override
    public void delete(final String key) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(repositorySQL.getDeleteSQL())) {
            preparedStatement.setString(1, key);
            preparedStatement.executeUpdate();
        } catch (final SQLException ex) {
            log.error("Delete {} data by key: {} failed", getType(), key, ex);
        }
    }

    @Override
    public void close() {
        dataSource.close();
    }

    @Override
    public String getType() {
        return "druid";
    }

    @Override
    public boolean isDefault() {
        return true;
    }
}
