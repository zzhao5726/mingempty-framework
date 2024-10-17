package top.mingempty.datasource.transaction;


import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.transaction.TransactionException;
import org.springframework.util.CollectionUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据库连接工厂
 */
@Slf4j
public class ConnectionFactory {
    /**
     * connection holder
     */
    private static final ThreadLocal<Map<String, Map<String, ConnectionProxy>>> CONNECTION_HOLDER =
            ThreadLocal.withInitial(ConcurrentHashMap::new);
    /**
     * savepoint connection holder
     */
    private static final ThreadLocal<Map<String, List<SavePointHolder>>> SAVEPOINT_CONNECTION_HOLDER =
            ThreadLocal.withInitial(ConcurrentHashMap::new);

    /**
     * put connection
     *
     * @param xid        xid
     * @param ds         ds
     * @param connection connection
     */
    public static void putConnection(String xid, String ds, ConnectionProxy connection) {
        Map<String, Map<String, ConnectionProxy>> concurrentHashMap = CONNECTION_HOLDER.get();
        Map<String, ConnectionProxy> connectionProxyMap = concurrentHashMap.computeIfAbsent(xid, k -> new ConcurrentHashMap<>());
        if (!connectionProxyMap.containsKey(ds)) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                log.error("记录连接异常", e);
            }
            connectionProxyMap.put(ds, connection);
        }
    }

    /**
     * getConnection
     *
     * @param xid 事务ID
     * @param ds  ds
     * @return boolean
     */
    public static ConnectionProxy getConnection(String xid, String ds) {
        Map<String, Map<String, ConnectionProxy>> concurrentHashMap = CONNECTION_HOLDER.get();
        Map<String, ConnectionProxy> connectionProxyMap = concurrentHashMap.get(xid);
        if (CollUtil.isNotEmpty(connectionProxyMap)) {
            return connectionProxyMap.get(ds);
        }
        return null;
    }

    /**
     * 是否有 Savepoint
     *
     * @param xid   xid
     * @param state state
     * @throws Exception Exception
     */
    public static void notify(String xid, Boolean state) throws Exception {
        Exception exception = null;
        Map<String, Map<String, ConnectionProxy>> concurrentHashMap = CONNECTION_HOLDER.get();
        Map<String, List<SavePointHolder>> savePointMap = SAVEPOINT_CONNECTION_HOLDER.get();
        if (CollectionUtils.isEmpty(concurrentHashMap)) {
            return;
        }
        boolean hasSavepoint = hasSavepoint(xid);
        List<SavePointHolder> savePointHolders = savePointMap.get(xid);
        Map<String, ConnectionProxy> connectionProxyMap = concurrentHashMap.get(xid);
        try {
            //如果有 savepoint，则表示嵌套事务。
            if (hasSavepoint) {
                try {
                    if (state) {
                        Iterator<SavePointHolder> iterator = savePointHolders.iterator();
                        while (iterator.hasNext()) {
                            SavePointHolder savePointHolder = iterator.next();
                            if (savePointHolder.releaseSavepoint()) {
                                iterator.remove();
                            }
                        }
                    } else {
                        List<ConnectionProxy> markedConnectionProxy = new ArrayList<>();
                        Iterator<SavePointHolder> iterator = savePointHolders.iterator();
                        while (iterator.hasNext()) {
                            SavePointHolder savePointHolder = iterator.next();
                            ConnectionProxy connectionProxy = savePointHolder.getConnectionProxy();
                            markedConnectionProxy.add(connectionProxy);
                            if (savePointHolder.rollbackSavePoint()) {
                                iterator.remove();
                            }
                        }

                        Iterator<Map.Entry<String, ConnectionProxy>> entryIterator = connectionProxyMap.entrySet().iterator();
                        while (entryIterator.hasNext()) {
                            Map.Entry<String, ConnectionProxy> connectionProxyEntry = entryIterator.next();
                            ConnectionProxy value = connectionProxyEntry.getValue();
                            if (!markedConnectionProxy.contains(value)) {
                                value.rollback();
                                entryIterator.remove();
                            }
                        }
                    }
                } catch (SQLException e) {
                    exception = e;
                }
            } else {
                for (ConnectionProxy connectionProxy : connectionProxyMap.values()) {
                    try {
                        if (connectionProxy != null) {
                            connectionProxy.notify(state);
                        }
                    } catch (SQLException e) {
                        exception = e;
                    }

                }
            }
        } finally {
            if (!hasSavepoint) {
                concurrentHashMap.remove(xid);
                savePointMap.remove(xid);
            }
            if (exception != null) {
                throw exception;
            }
        }
    }

    /**
     *创建一个 savepoint
     *
     * @param xid 事务ID
     * @throws TransactionException TransactionException
     */
    public static void createSavepoint(String xid) throws TransactionException {
        try {
            Map<String, List<SavePointHolder>> savePointMap = SAVEPOINT_CONNECTION_HOLDER.get();
            List<SavePointHolder> savePointHolders = savePointMap.get(xid);
            Map<String, Map<String, ConnectionProxy>> concurrentHashMap = CONNECTION_HOLDER.get();
            Map<String, ConnectionProxy> connectionProxyMap = concurrentHashMap.get(xid);
            if (CollectionUtils.isEmpty(savePointHolders)) {
                savePointHolders = new ArrayList<>();
                for (ConnectionProxy connectionProxy : connectionProxyMap.values()) {
                    SavePointHolder savePointHolder = new SavePointHolder(connectionProxy);
                    savePointHolder.conversionSavePointHolder();
                    savePointHolders.add(savePointHolder);
                }

            } else {
                List<ConnectionProxy> markedConnectionProxy = new ArrayList<>();
                for (SavePointHolder savePointHolder : savePointHolders) {
                    ConnectionProxy connectionProxy = savePointHolder.getConnectionProxy();
                    markedConnectionProxy.add(connectionProxy);
                    savePointHolder.conversionSavePointHolder();
                }
                for (ConnectionProxy connectionProxy : connectionProxyMap.values()) {
                    if (!markedConnectionProxy.contains(connectionProxy)) {
                        SavePointHolder savePointHolder = new SavePointHolder(connectionProxy);
                        savePointHolder.conversionSavePointHolder();
                        savePointHolders.add(savePointHolder);
                    }
                }

            }
            savePointMap.put(xid, savePointHolders);
        } catch (SQLException ex) {
            throw new CannotCreateTransactionException("Could not create JDBC savepoint", ex);
        }

    }

    /**
     * 确定是否存在 Savepoint
     *
     * @param xid 事务ID
     * @return true or false
     */
    public static boolean hasSavepoint(String xid) {
        Map<String, List<SavePointHolder>> savePointMap = SAVEPOINT_CONNECTION_HOLDER.get();
        if (CollectionUtils.isEmpty(savePointMap)) {
            return false;
        }
        List<SavePointHolder> savePointHolders = savePointMap.get(xid);
        return !CollectionUtils.isEmpty(savePointHolders);
    }

}