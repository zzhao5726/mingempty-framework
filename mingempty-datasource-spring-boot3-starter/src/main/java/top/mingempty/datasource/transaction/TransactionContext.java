package top.mingempty.datasource.transaction;

import cn.hutool.core.util.StrUtil;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.transaction.support.TransactionSynchronization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author funkye zp
 */
public class TransactionContext {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
    private static final ThreadLocal<Set<TransactionSynchronization>> SYNCHRONIZATION_HOLDER =
            ThreadLocal.withInitial(LinkedHashSet::new);

    /**
     * Gets xid.
     *
     * @return 事务ID
     */
    public static String getXID() {
        String xid = CONTEXT_HOLDER.get();
        if (!StrUtil.isEmpty(xid)) {
            return xid;
        }
        return null;
    }

    /**
     * Unbind string.
     *
     * @param xid 事务ID
     * @return the string
     */
    public static String unbind(String xid) {
        CONTEXT_HOLDER.remove();
        return xid;
    }

    /**
     * bind xid.
     *
     * @param xid 事务ID
     * @return the string
     */
    public static String bind(String xid) {
        CONTEXT_HOLDER.set(xid);
        return xid;
    }

    /**
     * remove
     */
    public static void remove() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * Register synchronization.
     *
     * @param synchronization 事务同步信息
     */
    public static void registerSynchronization(TransactionSynchronization synchronization) {
        if (Objects.isNull(synchronization)) {
            throw new IllegalArgumentException("TransactionSynchronization must not be null");
        }
        if (StrUtil.isEmpty(TransactionContext.getXID())) {
            throw new IllegalStateException("Transaction is not active");
        }
        Set<TransactionSynchronization> synchs = SYNCHRONIZATION_HOLDER.get();
        synchs.add(synchronization);
    }

    /**
     * Get synchronization list.
     *
     * @return List<TransactionSynchronization> synchronizations
     */
    public static List<TransactionSynchronization> getSynchronizations() {
        Set<TransactionSynchronization> synchs = SYNCHRONIZATION_HOLDER.get();
        //to avoid ConcurrentModificationExceptions.
        if (synchs.isEmpty()) {
            return Collections.emptyList();
        } else {
            // Sort lazily here, not in registerSynchronization.
            List<TransactionSynchronization> sortedSynchs = new ArrayList<>(synchs);
            AnnotationAwareOrderComparator.sort(sortedSynchs);
            return Collections.unmodifiableList(sortedSynchs);
        }
    }

    /**
     * Remove synchronizations.
     */
    public static void removeSynchronizations() {
        SYNCHRONIZATION_HOLDER.remove();
    }

}