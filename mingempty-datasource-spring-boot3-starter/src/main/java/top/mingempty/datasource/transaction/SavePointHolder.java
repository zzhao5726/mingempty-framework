package top.mingempty.datasource.transaction;


import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;
import java.sql.SQLTransientConnectionException;
import java.sql.Savepoint;
import java.util.LinkedList;
import java.util.List;

/**
 * Savepoint Holder
 */
@Slf4j
public class SavePointHolder {
    /**
     * savepoint name prefix
     */
    private static final String SAVEPOINT_NAME_PREFIX = "DYNAMIC_";
    /**
     * connection proxy
     * -- GETTER --
     * get connection proxy
     *
     * @return
     */
    @Getter
    private final ConnectionProxy connectionProxy;
    /**
     * savepoints
     */
    private final LinkedList<Savepoint> savepoints = new LinkedList<>();

    /**
     * constructor
     *
     * @param connectionProxy connection proxy
     */
    public SavePointHolder(ConnectionProxy connectionProxy) {
        this.connectionProxy = connectionProxy;
    }

    /**
     * conversion savepoint holder
     *
     * @throws SQLException SQLException
     */
    public void conversionSavePointHolder() throws SQLException {
        if (connectionProxy == null) {
            throw new SQLTransientConnectionException();
        }
        int savepointCounter = connectionProxy.getSavepointCounter();
        Savepoint savepoint = connectionProxy.setSavepoint(SAVEPOINT_NAME_PREFIX + savepointCounter);
        connectionProxy.setSavepointCounter(savepointCounter + 1);
        savepoints.addLast(savepoint);
    }

    /**
     * release savepoint
     *
     * @return savepoint index
     * @throws SQLException SQLException
     */
    public boolean releaseSavepoint() throws SQLException {
        Savepoint savepoint = savepoints.pollLast();
        if (savepoint != null) {
            connectionProxy.releaseSavepoint(savepoint);
            String savepointName = savepoint.getSavepointName();
            log.info("dynamic-datasource releaseSavepoint [{}]", savepointName);
            return savepoints.isEmpty();
        }
        return true;
    }

    /**
     * rollback savepoint
     *
     * @return savepoint index
     * @throws SQLException SQLException
     */
    public boolean rollbackSavePoint() throws SQLException {
        Savepoint savepoint = savepoints.pollLast();
        if (savepoint != null) {
            connectionProxy.rollback(savepoint);
            String savepointName = savepoint.getSavepointName();
            log.info("dynamic-datasource rollbackSavePoint [{}]", savepointName);
            return savepoints.isEmpty();
        }
        return true;
    }

    /**
     * get savepoints
     *
     * @return
     */
    public List<Savepoint> getSavePoints() {
        return this.savepoints;
    }
}