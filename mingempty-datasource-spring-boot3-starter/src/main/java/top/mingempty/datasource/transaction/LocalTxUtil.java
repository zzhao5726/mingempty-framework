package top.mingempty.datasource.transaction;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * 本地事务工具类
 */
@Slf4j
public final class LocalTxUtil {


    /**
     * 手动开启事务
     *
     * @return 事务ID
     */
    public static String startTransaction() {
        String xid = TransactionContext.getXID();
        if (!StrUtil.isEmpty(xid)) {
            log.debug("dynamic-datasource exist local tx [{}]", xid);
        } else {
            xid = UUID.randomUUID().toString();
            TransactionContext.bind(xid);
            log.debug("dynamic-datasource start local tx [{}]", xid);
        }
        return xid;
    }

    /**
     * 手动提交事务
     *
     * @param xid 事务ID
     */
    public static void commit(String xid) throws Exception {
        boolean hasSavepoint = ConnectionFactory.hasSavepoint(xid);
        try {
            ConnectionFactory.notify(xid, true);
        } finally {
            if (!hasSavepoint) {
                log.debug("dynamic-datasource commit local tx [{}]", TransactionContext.getXID());
                TransactionContext.remove();
            }
        }
    }

    /**
     * 手动回滚事务
     *
     * @param xid 事务ID
     */
    public static void rollback(String xid) throws Exception {
        boolean hasSavepoint = ConnectionFactory.hasSavepoint(xid);
        try {
            ConnectionFactory.notify(xid, false);
        } finally {
            if (!hasSavepoint) {
                log.debug("dynamic-datasource rollback local tx [{}]", TransactionContext.getXID());
                TransactionContext.remove();
            }
        }
    }
}