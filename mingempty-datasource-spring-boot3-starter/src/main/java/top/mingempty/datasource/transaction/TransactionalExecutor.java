package top.mingempty.datasource.transaction;

import top.mingempty.datasource.aspect.DsTransactionalAspect;

/**
 * 事务执行器
 *
 * @author Hzh
 */
public interface TransactionalExecutor {

    /**
     * 执行
     *
     * @return object
     * @throws Throwable Throwable
     */
    Object execute() throws Throwable;

    /**
     * 获取事务信息
     *
     * @return TransactionalInfo
     */
    DsTransactionalAspect.TransactionalInfo getTransactionInfo();
}